import java.util.List;


public class LogisticRegressionRunner {
	public static void main(String[] args) {
		DataParser dp = new DataParser("data.txt");
		List<AbstractEntry> data = dp.parseTrain();
		
		LogisticRegression lr = new LogisticRegression(1, 0.01, Double.parseDouble(dp.config.get("c")));
		lr.train(data);
	}
}
