package pathOramHw;

import java.util.ArrayList;

/*
 * Name: TODO
 * NetID: TODO
 */

public class ORAMWithReadPathEviction implements ORAMInterface{
	
	/**
	 * TODO add necessary variables 
	 */
	private int num_blocks;
	
	public ORAMWithReadPathEviction(UntrustedStorageInterface storage, RandForORAMInterface rand_gen, int bucket_size, int num_blocks){
		// TODO complete the constructor
		this.num_blocks = num_blocks;
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
		return this.num_blocks;
	}


	@Override
	public int getNumBuckets() {
		// according to lecture notes
		return (int) Math.pow(2, getNumLevels() + 1) - 1;
	}


	
}
