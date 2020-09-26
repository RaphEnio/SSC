package pathOramHw;

import java.util.ArrayList;

import javax.management.RuntimeErrorException;

/*
 * Name: Arnab Chattopadhyay
 * StudentID: s2364484
 */

public class Bucket{
	private static boolean is_init = false;
	private static int max_size_Z = -1;
	private ArrayList<Block> blocks_in_bucket; 					// data structure to add/remove block from bucket in server, i.e a byte array of blocks
	protected int length; 										//counter to keep track of real blocks in the Bucket


	
	//TODO Add necessary variables
	
	Bucket(){
		if(is_init == false)
		{
			throw new RuntimeException("Please set bucket size before creating a bucket");
		}
		//TODO Must complete this method for submission

		blocks_in_bucket = new ArrayList<Block>(max_size_Z); 	//initialize new byte array with 32 byte block size
		for (int i=0; i<max_size_Z; i++){
			blocks_in_bucket.add(new Block()); 					//initialization, keep adding blocks till max_size of bucket - dummy blocks
		}
		length = 0; 											//since no real blocks added
	}
	
	// Copy constructor
	Bucket(Bucket other)
	{
		if(other == null)
		{
			throw new RuntimeException("the other bucket is not malloced.");
		}
		//TODO Must complete this method for submission
																//if malloced, refer .other and create new blocks again in bucket, test

		blocks_in_bucket = new ArrayList<Block>(max_size_Z);
		for (int i=0; i<other.getBlocks().size(); i++){
			blocks_in_bucket.add(new Block(other.getBlocks().get(i)));
		}
		length = other.length;

	}
	
	//Implement and add your own methods.
	Block getBlockByKey(int key){
		// TODO Must complete this method for submission
																//match the key and return the concerned block else return null

		for (int i =0; i<blocks_in_bucket.size(); i++){
			if (blocks_in_bucket.get(i).index == key){
				return blocks_in_bucket.get(i);
			}
		}

		return null;
	}
	
	void addBlock(Block new_blk){
		// TODO Must complete this method for submission
		blocks_in_bucket.set(length, new_blk); 					// add block at current real size with new_blk
		length++; 												//increment real size
	}
	
	boolean removeBlock(Block rm_blk)
	{
		// TODO Must complete this method for submission

		for (int i=0; i<blocks_in_bucket.size(); i++){
			if (blocks_in_bucket.get(i).index == rm_blk.index){ //check if the block exists
				blocks_in_bucket.remove(i); 					//remove the block
				blocks_in_bucket.add(new Block()); 				// add a new dummy block to provide blinding
				length--; 										//reduce the real size of the bucket
				return true;
			}
		}

		return false;
	}
	
	
	ArrayList<Block> getBlocks(){
		// TODO Must complete this method for submission
		return blocks_in_bucket; 								//return the current blocks in a bucket
	}
	
	int returnRealSize(){
		// TODO Must complete this method for submission
																//length variable initialized earlier keeps a track of the added or removed blocks, hence this is the real size of the Bucket
		return length;
	}

	static void resetState()
	{
		is_init = false;
	}

	static void setMaxSize(int maximumSize)
	{
		if(is_init == true)
		{
			throw new RuntimeException("Max Bucket Size was already set");
		}
		max_size_Z = maximumSize;
		is_init = true;
	}

}