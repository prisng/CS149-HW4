package paging;

import java.io.IOException;
import java.io.PrintStream;

public class Tester {

    public static void main(String[] args) {

		try {
			PrintStream pagingTextFile = new PrintStream("paging.txt");
			System.setOut(pagingTextFile);
	        String output = ""; 
	        
	        Disk disk = new Disk();
	        Memory[] algorithms = {new FIFO(disk), new LRU(disk), new LFU(disk), new MFU(disk), new RandomPick(disk)};
	        String[] algorithmNames = {"FIFO", "LRU", "LFU", "MFU", "Random Pick"};
	        
	        for (int i = 0; i < algorithms.length; i++) {
	            Process process = new Process(algorithms[i], i + 1);
	            System.out.println(algorithmNames[i]);
	            System.out.println("----------------------------- Currently running process: " + process.name() + " -----------------------------");
	            for (int j = 0; j < 5; j++) {
	                System.out.println("\r\nRun " + (j + 1) + " ");
	                process.run();
	                process.reset();
	            }
	            output += algorithmNames[i] + (" ") + process.printAverageHitRatio(); 
	            System.out.println();
	        }
	        System.out.println(output);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    }
}
