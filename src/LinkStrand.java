
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
	
	public LinkStrand() {
		this("");
	}
	
	public LinkStrand(String s) {
		initialize(s);
	}
	@Override
	public long size() {
		return mySize;
	}
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
	@Override
	public IDnaStrand getInstance(String source) {
		return new LinkStrand(source);
	}
	@Override
	public IDnaStrand append(String dna) {
		myLast.next = new Node(dna);
		myLast = myLast.next;
		mySize += dna.length();
		myAppends++;
		return this;
	}
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
		//make sure that this reverses the node info too
		LinkStrand backwards = new LinkStrand(copy.toString());
		return backwards;
	}
	@Override
	public int getAppendCount() {
		return myAppends;
	}
	@Override
	public char charAt(int index) {
		while (myIndex != index && myCurrent != null) {
			myIndex++;
			myLocalIndex++;
			if (index > mySize) {
				return ' ';
			}
			if (index < myIndex) {
				myIndex = 0;
				myLocalIndex = 0;
				myCurrent = myFirst;
			}
			if (myLocalIndex >= myCurrent.info.length()) {
				myLocalIndex = 0;
//				if (myCurrent.next == null) return ' ';
				myCurrent.info = myCurrent.next.info;
			}
		}
		return myCurrent.info.charAt(myLocalIndex);
	}
	
//	public int charAt(int index) {
//		int count = 0;
//				int dex = 0;
//				Node list = myFirst;
//				while (count != index) {
//					count++;
//					dex++;
//					if (dex >= list.info.length()) {
//						dex = 0;
//						list = list.next;
//					}
//				}
//		           return list.info.charAt(dex);
//		        }

	
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