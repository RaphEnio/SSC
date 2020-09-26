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
	private int[] positionMap;
	private int num_of_blocks;
	private int treeHeight;
	private ArrayList<Block> Stash;

	private UntrustedStorageInterface storage;
	private RandForORAMInterface randOram;
	private int bucket_size;

	
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO complete the constructor
		this.storage = storage;
		this.num_of_blocks = num_blocks;
		this.randOram = rand_gen;
		this.bucket_size = bucket_size;

		this.Stash = new ArrayList<Block>();
		this.positionMap = new int[num_blocks];

		this.treeHeight = (int) (Math.ceil (Math.log(num_of_blocks) / Math.log(2)) );
		this.randOram.setBound(getNumLeaves());

		for (int i=0; i<positionMap.length; i++){
			positionMap[i] = randOram.getRandomLeaf();
		}

		this.storage.setCapacity(getNumBuckets());

		Bucket temp_bucket = new Bucket();
		for (int i=0; i<getNumBuckets(); i++){
			this.storage.WriteBucket(i, temp_bucket);
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
		return 0;
	}


	@Override
	public int[] getPositionMap() {
		// TODO Must complete this method for submission
		return null;
	}


	@Override
	public ArrayList<Block> getStash() {
		// TODO Must complete this method for submission
		return null;
	}


	@Override
	public int getStashSize() {
		// TODO Must complete this method for submission
		return 0;
	}

	@Override
	public int getNumLeaves() {
		// TODO Must complete this method for submission
		return 0;
	}


	@Override
	public int getNumLevels() {
		// TODO Must complete this method for submission
		return 0;
	}


	@Override
	public int getNumBlocks() {
		// TODO Must complete this method for submission
		return 0;
	}


	@Override
	public int getNumBuckets() {
		// TODO Must complete this method for submission
		return 0;
	}


	
}
