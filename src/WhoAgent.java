// Kyle Geib - Program 3 - CSS434 Fall 2012 - Dr Fukuda - November 15th 2012
// WhoAgent.java


import java.io.*;
import java.util.Vector;
import UWAgent.*;

public class WhoAgent extends UWAgent implements Serializable {

	private String[] my_args;
	private int node_num;
	private int repetitions;
	private int cycle = 1;
	private Vector<String> results;

	/**
	 * Constructor copies args to private memory
	 * @param args Console arguments from main
	 */
	public WhoAgent( String[] args) {	
		
	 	// Copy the arguments into private memory
		my_args = new String[args.length];
		for (int x = 0; x < args.length; ++x)
			my_args[x] = args[x];

		repetitions = Integer.parseInt(args[0]);
	}

	/**
	 * UWAgent init()
	 */
	public void init() {
		if (cycle <= repetitions) {
			System.out.println("Loop #" + cycle + ":");
			node_num = 1;
			hop(my_args[node_num + 1], "get", null);
		}
	}
	
	/**
	 * Modified code taken from provided WhoServer.java
	 */
	public void get() {
		// New node, new number
		node_num++;
		
		// StringBuffer for easy & efficient string building
		StringBuffer sb = new StringBuffer();
		
		// Report this node's name
		sb.append("Node #" + node_num + " \"" + my_args[node_num + 1] + "\"");
		
		// Copied from WhoServer.java
		String line;
		try {
		    Runtime runtime = Runtime.getRuntime( );
		    Process process = runtime.exec( "who" );
		    InputStream input = process.getInputStream();
		    BufferedReader bufferedInput
			= new BufferedReader( new InputStreamReader( input ) );
		    while ( ( line = bufferedInput.readLine( ) ) != null ) {
				System.out.println( line );
				sb.append(line + "\n");		// Append to StringBuffer
		    }
		} catch ( IOException e ) {
		    e.printStackTrace( );
		}
		
		// Write this nodes's results to the vector...
		results.add(sb.toString());
		
		// Check if we continue onto another machine
		if (node_num + 1 < my_args.length)
			hop(my_args[node_num + 1], "get", null);
		else // Reached end of host arguments
			hop(my_args[1], "printResults", null);
	}

	/**
	 * Prints out the results vector in the home node.
	 */
	public void printResults() {
		for (int z = 0; z < results.size(); ++z)
			System.out.println(results.elementAt(z));
		
		results.clear();	// Empty out the Vector
		
		cycle++;
		init();				// Go back to the start!
	}
	
	/**
	 * Main function for the origin (injecting) host.
	 * @param args
	 */
	public static void main( String args[]) {
		if (args.length < 1) {
			System.err.println(
					"Usage: WhoAgent repetitions homeip ip1 ip2 ip3...");
			System.exit(-1);
		}
		System.out.println("INJECTED");
		new WhoAgent(args);
	}
}
