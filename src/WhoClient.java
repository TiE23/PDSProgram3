// Kyle Geib - Program 3 - CSS434 Fall 2012 - Dr Fukuda - November 15th 2012
// WhoClient.java

import java.rmi.*;
import java.util.Vector;

public class WhoClient {
	
	/**
	 * Constructor for WhoClient class. Performs all basic tasks
	 * @throws Exception
	 * @param args The port and server IPs are relayed in this parameter
	 */
	@SuppressWarnings("unchecked")
	public WhoClient(String args[]) throws Exception {
		
		long startTime = System.currentTimeMillis();
		int repetitions = Integer.parseInt(args[1]);
		Vector<String> returnValue = null;
		
		// Loop through the repetitions
		for (int n = 0; n < repetitions; ++n) {
		System.out.println("Loop #" + n + ":");
		
		// Loop through the server(s)
		for (int x = 2; x < args.length; ++x) {
			ServerInterface server = (ServerInterface)
					Naming.lookup("rmi://" + args[x] + ":" + 
									args[0] +"/whoserver");
			
			try {
				returnValue = server.get();		// Call and receive the return
			} catch (Exception e) {}
			
			// Print out the returnValue
			System.out.println(args[x] + ":");
			for (int z = 0; z < returnValue.size(); ++z) {
				System.out.println(returnValue.elementAt(z));
			}
			returnValue.clear();	// Empty out the Vector
		}
		}
		
		System.out.println("Execution time: " + 
					(System.currentTimeMillis() - startTime) + "ms");
	}
	
	
	/**
	 * Main function starts the program
	 * @param args Console arguments
	 */
	public static void main(String args[]) {
		if (args.length < 3) {
			System.err.println(
					"Usage: java WhoClient port# repetitions ip1 ip2 ip3...");
			System.exit(-1);
		}
		try {
			new WhoClient(args);	// Instantiate WhoClient object
		} catch (Exception e) {}
	}

}
