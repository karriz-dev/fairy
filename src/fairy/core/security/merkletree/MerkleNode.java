package fairy.core.security.merkletree;

import fairy.core.utils.Convert;

public class MerkleNode {
	private MerkleNode left = null;
	private MerkleNode right = null; 
	private byte[] data = null;
	
	public MerkleNode(byte[] data)
	{
		this.data = data;
	}
	
	public MerkleNode(byte[] data, MerkleNode left)
	{
		this.data = data;
	}
	
	public MerkleNode(byte[] data, MerkleNode left, MerkleNode right)
	{
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
	
	@Override
	public String toString()
	{
		return "[Left: " + left + ", Right: " + right + ", Data: " + Convert.bytesToHex(data)+"]";
	}
}
