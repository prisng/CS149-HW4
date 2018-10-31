package paging;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;


public abstract class Memory {

    private final int freePages = 4;
    private final Disk disk;
    private Queue<Page> workloadQueue;
    private int time = 0;
    private final List<Page> pageFrames = new ArrayList<>();
    private int pageHits;

    // create memory with disk access and workload queue
    public Memory(Disk d) {
        disk = d;
        workloadQueue = new LinkedList<Page>();
    }

    // requests a page from memory or disk based on number of references made
    public Page requestPage(int page, int referencesMade) {
    	System.out.print("Page reference " + referencesMade + ": ");
        Optional<Page> optPage = pageFrames.stream().filter(p -> p.getPageNumber() == page).findFirst();
        pageFrames.stream().forEach(System.out::print);
        System.out.println();
        // generate workload as sorted queue based on arrival time
        if (optPage.isPresent()) {
        	workloadQueue.add(optPage.get());
        	Random rand = new Random();
        	double duration = rand.nextInt(5) + 1;
        	System.out.print("Arrival time: ");
        	System.out.printf("%.2f", (duration + time) / 60);
        	System.out.println(" seconds");
            time += duration;
            System.out.println("Page " + page + " hit");
            pageHits++;
            return optPage.get();
        }
        
        System.out.println("Page " + page + " must be paged into memory");
        if (pageFrames.size() == freePages) {
            int evictedIndex = getPageIndexToRemove();
            Page pageRemoved = pageFrames.remove(evictedIndex);
            System.out.println("Page " + pageRemoved.getPageNumber() + " has been evicted");
        }
        
        Page rPage = disk.getPage(page);
        pageFrames.add(rPage);
        return rPage;
    }

   
    public abstract int getPageIndexToRemove();

    // allows subclasses to access memory page frames
    protected List<Page> getPageFrames() {
        return pageFrames;
    }

    
    // gets number of pages on the disk
    public int getPagesOnDisk() {
        return disk.getPageSizeOnDisk();
    }

    // number of page hits during current run 
    public int getPageHits() {
        return pageHits;
    }

  
    // resets the memory state of page
    public void reset() {
        pageHits = 0;
        pageFrames.clear();
    }
}
