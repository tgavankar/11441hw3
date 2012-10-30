import java.util.List;


public class LogisticRegression {
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
				double hx = sigmoid(W, X);
				
				for(int j=0; j<W.length; j++) {
					W.put(j, W.get(j) + this.learnRate * (entry.getActualLabel() - hx * X.get(j) - C * W.get(j)));
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
}
