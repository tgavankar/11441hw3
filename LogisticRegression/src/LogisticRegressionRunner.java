import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class LogisticRegressionRunner {
	public static void main(String[] args) {
		System.out.println("Start");
		long startParse = System.currentTimeMillis();
		
		DataParser dp = new DataParser("data.txt");
		List<AbstractEntry> data = dp.parseTrain();
		
		long endParse = System.currentTimeMillis();
		System.out.println("Parse: " + (endParse - startParse));		
		
		List<LogisticRegression> lrl = new ArrayList<LogisticRegression>();
		
		for(int i=1; i<=17; i++) {
			System.out.println("Training: " + i);
			LogisticRegression lr = new LogisticRegression(i, 0.01, Double.parseDouble(dp.config.get("c")));
		
			//lr.train(data);
			lr = loadFromDisk("w" + i);
		
			lrl.add(lr);
			
			//flushToDisk(lr, "w" + i);
		}
		
		long endRun = System.currentTimeMillis();
		

		System.out.println("Train: " + (endRun - endParse));
		
		/*SparseTermList w = null;;
        try
        {
           FileInputStream fileIn =
                         new FileInputStream("w.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           w = (SparseTermList) in.readObject();
           in.close();
           fileIn.close();
       } 
       catch(Exception i) {
           i.printStackTrace();
       }
	
        if(w != null) {
        	lr.setW(w);
        }*/
		
		
		
		
		// Classify
		int count = 0;
		for(int i=0; i<data.size(); i++) {
			AbstractEntry entry = data.get(i);
			double max = Double.MIN_VALUE;
			int maxLabel = -1;
			for(int j=0; j<lrl.size(); j++) {
				double pred = lrl.get(j).classify(entry.getList());
				if(pred > max) {
					max = pred;
					maxLabel = j+1;
				}
			}
			
			entry.setPredLabel(maxLabel);
			
			if(maxLabel == entry.getActualLabel()) {
				count++;
			}
		}
		
		System.out.println("Accuracy: " + (double) (1.0*count) / data.size());
		return;
	}
	
	private static void flushToDisk(LogisticRegression e, String name) {
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(name + ".ser");
	         ObjectOutputStream out =
	                            new ObjectOutputStream(fileOut);
	         out.writeObject(e);
	         out.close();
	          fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	private static LogisticRegression loadFromDisk(String name) {
		LogisticRegression lr = null;  
		try
	        {
	           FileInputStream fileIn =
	                         new FileInputStream(name + ".ser");
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
