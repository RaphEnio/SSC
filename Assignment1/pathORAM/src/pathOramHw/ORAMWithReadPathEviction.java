package pathOramHw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Name: Arnab Chattopadhyay
 * StudentID: s2364484
 */

public class ORAMWithReadPathEviction implements ORAMInterface{
	
	/**
	 * TODO add necessary variables 
	 */
	private int[] positionMap; 									//variable to store the position map
	private int num_of_blocks; 									//variable to store the total number of blocks
	private int treeHeight; 									// variable to store the tree height
	private ArrayList<Block> Stash; 							//variable to store the client stash

	private UntrustedStorageInterface storage; 					// variable to store the Server storage
	private RandForORAMInterface randOram; 						//RNG and RandomLeaf as in Uniform distribution from pseudocode
	private int bucket_size;									//variable to store the bucket
	// access counter
	private int accessCount = 1;

	private Map<Integer,Integer> log = new HashMap<>();   		// Map to store the stash size and the accesses
	
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO complete the constructor
		this.storage = storage;									//initializing all the passed variables into the constructor
		this.num_of_blocks = num_blocks;
		this.randOram = rand_gen;
		this.bucket_size = bucket_size;

		log.put(-1, 0);											// Initialize logfile

		this.Stash = new ArrayList<Block>();					//Initialize the Block Array for the client Stash
		this.positionMap = new int[num_blocks];					//Initialize the position map with the total number of blocks

		this.treeHeight = (int) (Math.ceil (Math.log(num_of_blocks) / Math.log(2)) );			// Calculate the Tree height based on the formula
		this.randOram.setBound(getNumLeaves());					//Bound set for RNG to the number of leaves in the tree

		for (int i=0; i<positionMap.length; i++){
			positionMap[i] = randOram.getRandomLeaf();			//Fill position map with random leaves as is given in the pseudo-code
		}

		this.storage.setCapacity(getNumBuckets());				//Storage Capacity set to the number of buckets, before any read/write operation to the server

		Bucket temp_bucket = new Bucket();
		for (int i=0; i<getNumBuckets(); i++){
			this.storage.WriteBucket(i, temp_bucket);			//initial state, fill all the buckets in the tree
		}

	}


	@Override
	public byte[] access(Operation op, int blockIndex, byte[] newdata) {

		// TODO Must complete this method for submission
		int x = -1;
		try{
			x = positionMap[blockIndex]; 								//get the current position of the block (Leaf) and store it in x
			positionMap[blockIndex] = randOram.getRandomLeaf();
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Out of bounds at positionMap");
		}
					//Get a random leaf to fill the new position
		int levelX = findLevel(x);										// find level where x is stored


		for (int i=0; i<=levelX; i++){
			Bucket temp_bucket = storage.ReadBucket( P(x,i) );			//iteratively read Bucket from storage with the index defined by P, till treeHeight
			int counter = temp_bucket.returnRealSize();					//store the position of a real block and read all the blocks before it

			for (int j=0; j<counter; j++){
				Stash.add(temp_bucket.getBlocks().get(j)); 				// add the respective buckets to the Stash
			}
		}
		byte [] data = null;
		try{
			for (int i=0; i<Stash.size(); i++){
				if (Stash.get(i).index == blockIndex){
					data = Stash.get(i).data; 								//Store the data from the old block that needs to be written/accessed
				}
			}
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Out of bounds at stash");
		}


		if (op == Operation.WRITE){
			ArrayList<Block> temp_Stash;
			temp_Stash = Stash; 										// store the curent stash at client in a temporary variable

			for (int i=0; i<temp_Stash.size(); i++){
				if (temp_Stash.get(i).index == blockIndex){
					temp_Stash.remove(i); 								//remove the previously stored data from the client Stash
				}
			}

			Block new_block = new Block(blockIndex, newdata);			//create a new block with the new data
			temp_Stash.add(new_block); 									// add it to temp_stash
			Stash = temp_Stash; 										//update it to the current client stash
		}


		ArrayList<Block> new_Stash;
		for (int i=levelX; i>=0; i--){
			new_Stash = new ArrayList<Block>();

			for (int j=0; j<Stash.size(); j++){
				Block temp_block = Stash.get(j);
				if (P(x,i) == P(positionMap[temp_block.index], i)){
					new_Stash.add(temp_block);
				}
			}

			int k = Math.min(new_Stash.size(), bucket_size);

			new_Stash.removeAll(new_Stash.subList(k, new_Stash.size()));
			Stash.removeAll(new_Stash);
			Bucket new_bucket = new Bucket();

			for(int l=0; l<new_Stash.size(); l++){
				new_bucket.addBlock(new_Stash.get(l));
			}
			storage.WriteBucket(P(x, i), new_bucket);


		}
		if (accessCount > 3000000) {
			writeLog(getStashSize());// write to the logfile
		}

		accessCount++;
		return data; 													// return the currently stored data in the storage to be read or written
	}


	@Override
	public int P(int leaf, int level) {
		int leafLevel = findLevel(leaf);									// calculate level of the leaf in the storage
		int idx = (int) Math.pow(2, treeHeight) - 2 - (leaf - (int) Math.pow(2,leafLevel)) - 1; //get index of the leaf
		List<Integer> path = new ArrayList<>();
		path.add(idx); 													// add start point (or end point of the path)
		int currentLeaf = idx;
		int parent = 0;
		while (currentLeaf > 0) {										//find the index of parent leaf
			if (currentLeaf % 2 == 0) {
				parent = (currentLeaf - 2) / 2;
			} else {
				parent = (currentLeaf - 1) / 2;
			}
			path.add(parent);
			currentLeaf = parent;
		}
		int result = path.size() - level - 1;
		if (result < 0) {
			return -1; 												// this can be caused by new randomly sampled positionmap
		}
		return path.get(result);
		/** TODO Must complete this method for submission
		int a = (int) Math.pow(2, treeHeight-level);
		int b = 2 *(leaf/a) + 1;
		return a*b - 1; */
	}


	@Override
	public int[] getPositionMap() {
		// TODO Must complete this method for submission
		return positionMap;
	}


	@Override
	public ArrayList<Block> getStash() {
		// TODO Must complete this method for submission
		return Stash;
	}


	@Override
	public int getStashSize() {
		// TODO Must complete this method for submission
		return Stash.size();
	}

	@Override
	public int getNumLeaves() {
		// TODO Must complete this method for submission
		return (int) Math.pow(2, treeHeight) - 1;
	}


	@Override
	public int getNumLevels() {
		// TODO Must complete this method for submission
		return treeHeight;
	}


	@Override
	public int getNumBlocks() {
		// TODO Must complete this method for submission
		return num_of_blocks;
	}


	@Override
	public int getNumBuckets() {
		// TODO Must complete this method for submission
		return (int) Math.pow(2, this.treeHeight+1) - 1;
	}

	public void writeLog(int stashSize){
		log.put(-1,log.get(-1) + 1);						// update the general access counter
		if(log.containsKey(stashSize)){						// check if the key (stash size) already exists
			log.put(stashSize, log.get(stashSize) + 1);		// update the access counter of the stash size
		} else{
			log.put(stashSize, 1);							// insert new stash size if needed
		}
	}
	public void saveLog(){
		File file = new File("simulation1.txt");			// create new file
		BufferedWriter writer = null;
		try{
			writer = new BufferedWriter(new FileWriter(file));
			for(Map.Entry<Integer, Integer> entry : log.entrySet()){
				writer.write(entry.getKey() + "," + entry.getValue());
				writer.newLine();
			}
			writer.flush();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				writer.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public int findLevel(int leaf){
		int currentLevel = getNumLevels() - 1; 							// start at highest level
		int comBuckets = (int) Math.pow(2, currentLevel); 				// start with buckets in last level
		while(leaf > comBuckets - 1) {										// iteratively go through the tree to find the level where x is stored
			currentLevel -= 1;
			comBuckets += (int) Math.pow(2, currentLevel);
		}
		return currentLevel;
	}
}
