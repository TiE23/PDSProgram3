import java.io.*;
import java.util.Arrays;
import java.net.InetAddress;
import UWAgent.*;

public class TestAgent extends UWAgent implements Serializable {
    private String[] mHosts;
    private int      mTotalIterations;
    
    private String   mHomeHost;
    private int      mCurrentIteration;
    
    public TestAgent(String[] args) {
	if (args.length < 2) usage();
	
	mTotalIterations = Integer.parseInt(args[0]);
	mHosts = Arrays.copyOfRange(args, 1, args.length);
    }

    private static void usage() {
	System.err.println( 
          "Usage: <inject> TestAgent <iterations> <ip1> [ip2] [ip3].."
			    );
	System.exit(-1);
    }

    public void init() {
	mCurrentIteration = 1;

	try {
	    mHomeHost = InetAddress.getLocalHost().getHostName();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(-1);
	}

      hop(mHosts[0], "hopHost");
    }

    public void hopHost() {
	hop(mHomeHost, "hopHome");
    }

    public void hopHome() {
	System.out.println("# loop " + mCurrentIteration + ":");

	if (++mCurrentIteration <= mTotalIterations) {
	    hop(mHosts[0], "hopHost");
	}
    }
}

 
 
