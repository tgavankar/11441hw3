import java.util.HashMap;
import java.util.Map;


public class SparseTermList {
	Map<Integer, Double> list;
	
	public SparseTermList() {
		list = new HashMap<Integer, Double>();
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
}
