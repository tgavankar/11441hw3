import java.util.HashMap;
import java.util.Map;


public class SparseTermList {
	Map<Integer, Double> list;
	public int length;
	
	public SparseTermList(int size) {
		list = new HashMap<Integer, Double>();
		length = size;
	}
	
	public double get(int i) {
		if(list.containsKey(i)) {
			return list.get(i);
		}
		return 0.0;
	}
	
	public void put(int i, double s) {
		list.put(i, s);
	}
	
	public SparseTermList deepCopy() {
		SparseTermList out = new SparseTermList(length);
		for(int i=0; i<length; i++) {
			if(get(i) != 0.0) {
				out.put(i, get(i));
			}
		}
		return out;
	}
}
