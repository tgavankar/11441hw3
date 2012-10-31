import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent data vector, allows for easy implementation changes.
 */
public class SparseTermList implements Serializable {
	private static final long serialVersionUID = 8347221082845553086L;
	double[] list;
	int length;
	
	public SparseTermList(int size) {
		list = new double[size];
		length = size;
	}
	
	public double get(int i) {
		return list[i];
	}
	
	public void put(int i, double s) {
		list[i] = s;
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
	
	@Override
	public String toString() {
		return Arrays.toString(list);
	}
}
