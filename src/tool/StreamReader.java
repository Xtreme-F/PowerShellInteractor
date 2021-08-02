package tool;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class StreamReader extends Thread {

    private AtomicBoolean running = new AtomicBoolean(false);
    private InputStream in;
    private OutputStream out;
    private ArrayList<String> outputlines;

    public StreamReader(InputStream in, OutputStream out, ArrayList<String> outputstore) {
    	outputlines = outputstore;
        this.in = in;
        this.out = out;
        running.set(true);
    }

    @Override
    public void run() {
    	System.out.println("Run");
        Scanner scanner = new Scanner(in);
        PrintWriter writer = new PrintWriter(out, true);
        while (running.get()) {
            if (scanner.hasNextLine()) {
            	String line = scanner.nextLine();
            	outputlines.add(line);
                writer.println(line);
                writer.flush();
            }
        }
        System.out.println("Close");
        scanner.close();
    }

    public void shutdown() {
        running.set(false);
    }
    
    public ArrayList<String> retrieveOutput(){
    	return outputlines;
    }
    
    public void flushOutput() {
    	outputlines = new ArrayList<String>();
    }

}