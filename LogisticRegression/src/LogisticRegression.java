import java.io.Serializable;
import java.util.List;


public class LogisticRegression implements Serializable {
	private int label;
	private SparseTermList W;
	private double learnRate;
	private double C;
	
	public LogisticRegression(int label, double learnRate, double C) {
		this.label = label;
		this.W = new SparseTermList(14601);
		this.learnRate = learnRate;
		this.C = C;
	}
	
	public void train(List<AbstractEntry> data) {
		double dist = Double.MAX_VALUE;
		do {
			SparseTermList prevW = W.deepCopy();
			for(int i=0; i<data.size(); i++) {
				AbstractEntry entry = data.get(i);
				SparseTermList X = entry.getList();
				double y = (entry.getActualLabel() == this.label) ? 1 : 0;
				double hx = sigmoid(W, X);
				
				for(int j=0; j<W.length; j++) {
					W.put(j, W.get(j) + this.learnRate * (y - hx * X.get(j) - C * W.get(j)));
				}
			}
			dist = dist(prevW, W);
			System.out.println(dist);
		} while(dist > 0.01);
	}
	
	private double sigmoid(SparseTermList W, SparseTermList X) {
		return 1.0 / (1.0 + Math.exp(-1.0 * dotp(W, X)));
	}
	
	private double dotp(SparseTermList W, SparseTermList X) {
		double out = 0;
		for(int i=0; i<W.length; i++) {
			if(X.get(i) == 0) {
				continue;
			}
			out += W.get(i) * X.get(i);
		}
		return out;
	}
	
	private double dist(SparseTermList W1, SparseTermList W2) {
		double out = 0.0;
		for(int i=0; i<W1.length; i++) {
			out += Math.pow(W1.get(i) - W2.get(i), 2);
		}
		return Math.sqrt(out);
	}
	
	public SparseTermList getW() {
		return W;
	}
	
	public void setW(SparseTermList w) {
		this.W = w;
	}
	
	public double classify(SparseTermList X) {
		return sigmoid(this.W, X);
	}
	
	/*@Override
	public String toString() {
		return this.W.toString();
	}*/
}
