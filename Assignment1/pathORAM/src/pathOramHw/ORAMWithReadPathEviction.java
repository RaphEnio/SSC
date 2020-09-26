package pathOramHw;

import java.util.ArrayList;

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
	private int bucket_size; 									//variable to store the bucket size

	
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO complete the constructor
		this.storage = storage;									//initializing all the passed variables into the constructor
		this.num_of_blocks = num_blocks;
		this.randOram = rand_gen;
		this.bucket_size = bucket_size;

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
		return null;
	}


	@Override
	public int P(int leaf, int level) {
		// TODO Must complete this method for submission
		int a = (int) Math.pow(2, treeHeight-level);
		int b = 2 *(leaf/a) + 1;
		return a*b - 1;
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
		return (int) Math.pow(2, treeHeight);
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


	
}
