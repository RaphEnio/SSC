package pathOramHw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Name: TODO
 * NetID: TODO
 */

public class ORAMWithReadPathEviction implements ORAMInterface{

	/** variable to store the number of blocks*/
	private int num_blocks;
	/** variable to store the server storage*/
	private UntrustedStorageInterface storage;
	/** variable to store the positon map */
	private int[] positionMap;
	/** local stash variable */
	private ArrayList<Block> stash;
	/** variable to store the random number generator */
	private RandomForORAMHW rand_gen;

	/**
	 * Constructs a new ORAM and initializes needed variables.
	 * @param storage Server storage
	 * @param rand_gen random number generator
	 * @param bucket_size size of the buckets in the tree
	 * @param num_blocks the number of blocks in the tree
	 */
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		this.num_blocks = num_blocks;
		this.storage = storage;
		this.storage.setCapacity(getNumBuckets());
		this.rand_gen = (RandomForORAMHW) rand_gen;
		// Set bound size for the random int generator
		rand_gen.setBound(getNumLeaves());
		// create and fill position map of size L
		positionMap = new int[getNumLevels()];
		for (int i = 0; i < positionMap.length; i++){
			positionMap[i] = rand_gen.getRandomLeaf();
		}
		// initialize the stash and leave it empty
		this.stash = new ArrayList<>();

	}


	@Override
	public byte[] access(Operation op, int blockIndex, byte[] newdata) {
		byte[] data = null;
		/* Remap block at blockIndex.*/
		// get leaf where the data is stores
		int x =  positionMap[blockIndex];//Arrays.binarySearch(positionMap, blockIndex);
		// generate new position
		int new_x = rand_gen.getRandomLeaf();
		/* Read path for leaf */
		// get path
		List<Integer> path = new ArrayList<>();
		for(int l = getNumLevels() - 1; l >= 0; l--) {
			path.add(P(x, l));
		}
		// if the block is not in the tree is must be already in the stash
		if (path == null) {

		}
		return data;
	}


	@Override
	public int P(int leaf, int level) {
		//go through all levels
		for(int i = getNumLevels() - 1; i >= 0; i--) {
			// find the indices of the blocks
		}
		return 0;
	}

	/**
	 * Returns the Block of the given index.
	 * @param blockIndex index of the Block to find
	 */
	public Block findBlockInStash(int blockIndex){
		Block result = null;
			for (int i = 0; i < getStashSize(); i++) {
				if (stash.get(i).index == blockIndex) {
					result = stash.get(i);
					break;
				}
			}
		return result;
	}

	@Override
	public int[] getPositionMap() {
		return positionMap;
	}


	@Override
	public ArrayList<Block> getStash() {
		return stash;
	}


	@Override
	public int getStashSize() {
		return stash.size();
	}

	@Override
	public int getNumLeaves() {
		// according to lecture notes
		return (int) Math.pow(2, getNumLevels());
	}


	@Override
	public int getNumLevels() {
		// according to lecture notes
		return (int) Math.ceil((Math.log(getNumBlocks()) / Math.log(2)));
	}


	@Override
	public int getNumBlocks() {
		return num_blocks;
	}


	@Override
	public int getNumBuckets() {
		// according to lecture notes
		return (int) Math.pow(2, getNumLevels() + 1) - 1;
	}


	
}
