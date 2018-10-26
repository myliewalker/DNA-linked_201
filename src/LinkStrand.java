import java.lang.IndexOutOfBoundsException;

/**
 * Efficient implementation of IDnaStrand. This implementation 
 * uses LinkStrands to represent genomic/DNA data.
 * 
 * @author Mylie Walker
 * @date October 2018, created
 */

public class LinkStrand implements IDnaStrand {
	
	private Node myFirst;
	private Node myLast;
	private long mySize;
	private int myAppends;
	private int myIndex;
	private int myLocalIndex;
	private Node myCurrent;
	
	private class Node {
		String info;
		Node next;	
		public Node (String s) {
			info = s;
			next = null;
		}
	}

	/**
	 * Create an empty strand of dna
	 * @param s the source of cgat data for this strand
	*/
	public LinkStrand() {
		this("");
	}

	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * @param s the source of cgat data for this strand
	 */
	public LinkStrand(String s) {
		initialize(s);
	}

	/**
	 * @return the number of base pairs in this strand
	 */
	@Override
	public long size() {
		return mySize;
	}

	/**
	 * Initialiize the strand of DNA. Initialize the first node with a new node that 
	 * links to the beginning of the strand and last node, which represents the end 
	 * of the strand, with the first node. Initialize size to the length of the strand, 
	 * initialize the index, current index, and number of appends to zero. Initialize the 
	 * current node as the first node. No error checking is performed.
	 * @param source is the source of this enzyme
	 */
	@Override
	public void initialize(String source) {
		myFirst = new Node(source);
		myLast = myFirst;
		mySize = source.length();
		myAppends = 0;
		myIndex = 0;
		myLocalIndex = 0;
		myCurrent = myFirst;
	}

	/**
	 * @return a new strand based on source
	 */
	@Override
	public IDnaStrand getInstance(String source) {
		return new LinkStrand(source);
	}

	/**
	 * Append a strand of dna data to the end of this strand. No error checking is
	 * done. 
	 * @param dna is the String appended to this strand
	 */
	@Override
	public IDnaStrand append(String dna) {
		myLast.next = new Node(dna);
		myLast = myLast.next;
		mySize += dna.length();
		myAppends++;
		return this;
	}

	/**
	 * @return the dna strand with all the base pairs in backwards/reverse order
	 */
	@Override
	public IDnaStrand reverse() {
		StringBuilder copy = new StringBuilder();
		Node temp = myFirst;
		copy.append(temp.info);
		while (temp.next != null) {
			temp = temp.next;
			copy.append(temp.info);
		}
		copy.reverse();
		LinkStrand backwards = new LinkStrand(copy.toString());
		return backwards;
	}

	/**
	 * @return the number of times this has been appended
	 */
	@Override
	public int getAppendCount() {
		return myAppends;
	}	
	
	/**
	 * Check that index is greater than zero and less than mySize. Use myIndex 
	 * to keep track of the index used the last time charAt was called and begin
	 * searching from there if index is greater than myIndex. Use myLocalIndex to 
	 * keep track of position within the current node, myCurrent. This makes charAt
	 * O(1) if index is one greater than the last index used.
	 * @param index is the index of the character that gets returned
	 * @return the char at the position index
	 */
	@Override
	public char charAt(int index) {
		if (index < 0 || index >= mySize) throw new IndexOutOfBoundsException();
		if (myIndex > index) {
			myIndex = 0;
			myLocalIndex = 0;
			myCurrent = myFirst;
		}
		while (myIndex != index) {
			myLocalIndex++;
			myIndex++;
			if (myLocalIndex >= myCurrent.info.length()) {
				myLocalIndex = 0;
				myCurrent = myCurrent.next;
			}
		}
		return myCurrent.info.charAt(myLocalIndex);
	}

	/**
	 * @return the string representation of a LinkStrand object
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		Node n = myFirst;
		str.append(n.info);
		while (n.next != null) {
			n = n.next;
			str.append(n.info);
		}
		return str.toString();
	}
}