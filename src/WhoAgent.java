// Kyle Geib - Program 3 - CSS434 Fall 2012 - Dr Fukuda - November 15th 2012
// WhoAgent.java


import java.io.*;
import java.util.Vector;
import UWAgent.*;

@SuppressWarnings("serial")
public class WhoAgent extends UWAgent implements Serializable {
	private String[] my_args;
	private int node_num;
	private int repetitions;
	private int cycle = 1;
	private long startTime;
	private Vector<String> results;

	/**
	 * Constructor copies args to private memory
	 * @param args Console arguments from main
	 */
	public WhoAgent( String[] args) {	
		startTime = System.currentTimeMillis();
		
		System.out.println("INJECTED");
		
		for (int z = 0; z < args.length; ++z)
			System.out.println("[" + z + "] = \"" + args[z] + "\"");
		
	 	// Copy the arguments into private memory
		my_args = new String[args.length];
		for (int x = 0; x < args.length; ++x)
			my_args[x] = args[x];

		
		results = new Vector<String>();
		repetitions = Integer.parseInt(args[0]);
	}

	/**
	 * UWAgent init()
	 */
	public void init() {
		
		// Repeating...
		if (cycle <= repetitions) {
			System.out.println("Loop #" + cycle + ":");
			node_num = 1;
			
			if (node_num + 1 < my_args.length) 
				hop(my_args[node_num + 1], "get", null);

				
		} else {
			System.out.println("Execution time: " + 
					(System.currentTimeMillis() - startTime) + "ms");
		}
	}
	
	/**
	 * Modified code taken from provided WhoServer.java
	 */
	public void get() {
		// New node, new number
		node_num++;
		
		// StringBuffer for easy & efficient string building
		StringBuffer sb = new StringBuffer("");
		String header = my_args[node_num] + ":";
		
		// Report this node's name
		sb.append(header + "\n");
		System.out.println("\n" + header);
		
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
			hop(my_args[1], "printResults", null);	// Go to origin host
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
}
