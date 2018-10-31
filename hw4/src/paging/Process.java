package paging;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

public class Process {

    private final Memory mem;
    private final Random r = new Random();
    private int lastPageIndex = -1;
    private final List<Integer> stats = new ArrayList<>();
    private final int name;
    private int pageRefs;
    
    public Process(Memory accessTo, int name) {
        mem = accessTo;
        this.name = name;
    }

    public int name() {
    	return this.name;
    }

    private int getPageToRequest() {
		// random page index
        if (lastPageIndex == -1) {
            lastPageIndex = r.nextInt(mem.getPagesOnDisk());
            return lastPageIndex;
        }
        int p = r.nextInt(10);
        // locality of reference: 70% probability
        if (p < 7) {
            lastPageIndex = (lastPageIndex + (r.nextInt(3) - 1)) % 10;
        } else {
            lastPageIndex = (lastPageIndex + (r.nextInt(7) + 2)) % 10;
        }
        return Math.max(0, lastPageIndex);
    }

    public void reset() {
        pageRefs = 0;
        lastPageIndex = -1;
        mem.reset();
    }    
    
    // simulates 100 page references
    public void run() {
        while (pageRefs < 100) {
            pageRefs++;
            mem.requestPage(getPageToRequest(), pageRefs);
        }
        stats.add(mem.getPageHits());
        System.out.println("Page hit ratio: " + mem.getPageHits() / 100.0);
    }
   
    public String printAverageHitRatio() {
        OptionalDouble avgSwap = stats.stream().mapToDouble(a -> a).average();
        return "Average hit ratio: " + (avgSwap.isPresent() ? avgSwap.getAsDouble() / 100.0 : 0) + "\r\n";
    }
    
}