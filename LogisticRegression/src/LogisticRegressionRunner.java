import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LogisticRegressionRunner {
	public static void main(String[] args) {
		// Load config
		DataParser dp = new DataParser("data.txt");
		
		// Read training data
		List<AbstractEntry> data = dp.parseTrain();		
		
		List<LogisticRegression> lrl = new ArrayList<LogisticRegression>();
		
		// Train each binary classifier
		for(int i=1; i<=17; i++) {
			LogisticRegression lr = new LogisticRegression(i, 0.01, Double.parseDouble(dp.config.get("c")), 0.0001);
		
			// Comment/uncomment the appropriate training method desired
			lr.train(data);
			//lr = loadFromDisk("w" + i);
		
			lrl.add(lr);
			
			// Uncomment this if you want to save the learned classifiers to disk
			//flushToDisk(lr, "w" + i);
		}
		
		// Training data is no longer required, replace it with parsed test data
		data = dp.parseTest();
		
		// Classify test data
		for(int i=0; i<data.size(); i++) {
			AbstractEntry entry = data.get(i);
			
			// We want to find the maximum sigmoid value and its label
			double max = Double.NEGATIVE_INFINITY;
			int maxLabel = -1;
			
			for(int j=0; j<lrl.size(); j++) {
				double pred = lrl.get(j).classify(entry.getList());
				
				if(pred > max) {
					max = pred;
					maxLabel = j+1;
				}
			}
			
			entry.setPredLabel(maxLabel);
			
			// Output predicted label
			System.out.println(entry.getPredLabel());			
		}
	}
	
	/**
	 * Writes the LogisticRegression object to file of given name
	 * @param e LogisticRegression object
	 * @param name name of output file (without filetype)
	 */
	private static void flushToDisk(LogisticRegression e, String name) {
		try {
			FileOutputStream fileOut = new FileOutputStream(name + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(e);
			out.close();
			fileOut.close();
		} 
		catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	/**
	 * Reads the LogisticRegression object from disk from given file
	 * @param name filename without extension
	 * @return LogisticRegression object from disk, or null if Exception
	 */
	private static LogisticRegression loadFromDisk(String name) {
		LogisticRegression lr = null;  
		try {
	        FileInputStream fileIn = new FileInputStream(name + ".ser");
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        lr = (LogisticRegression) in.readObject();
	        in.close();
	        fileIn.close();
	    } 
	    catch(Exception i) {
	        i.printStackTrace();
	    }
		return lr;
	}
}
