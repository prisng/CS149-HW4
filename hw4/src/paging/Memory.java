package paging;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;


public abstract class Memory {

    private final Disk disk;
    private final int maxPages = 4;
    private final List<Page> pageFrames = new ArrayList<>();
    private Queue<Page> workloadQueue;
    private int pageHits;
    private int time = 0;

    //Assigns disk access to memory
    public Memory(Disk d) {
        disk = d;
        workloadQueue = new LinkedList<Page>();
    }

    
    // Gets a requested page from memory, or from disk. And if memory cannot take it, it removes a page. 
    // Arguments needed are the page being requested and the amount of references made
  
    public Page requestPage(int page, int referencesMade) {
        Optional<Page> optPage = pageFrames.stream().filter(p -> p.getPageNumber() == page).findFirst();
        System.out.print("Page reference " + referencesMade + ": ");
        pageFrames.stream().forEach(System.out::print);
        System.out.println();
        // generate workload as sorted queue based on arrival time
        if (optPage.isPresent()) {
        	workloadQueue.add(optPage.get());
        	Random gen = new Random();
        	double duration = gen.nextInt(5) + 1;
        	System.out.print("Arrival time: ");
        	System.out.printf("%.2f", (duration + time) / 60);
        	System.out.println(" seconds");
            time += duration;
            System.out.println("Page " + page + " hit");
            pageHits++;
            return optPage.get();
        }
        
        System.out.println("Page " + page + " must be paged into memory");
        if (pageFrames.size() == maxPages) {
            int evictedIndex = getPageIndexToRemove();
            Page pageRemoved = pageFrames.remove(evictedIndex);
            System.out.println("Page " + pageRemoved.getPageNumber() + " has been evicted");
        }
        
        Page rPage = disk.getPage(page);
        pageFrames.add(rPage);
        return rPage;
    }

   
    public abstract int getPageIndexToRemove();

    //this is to allow subclasses to access memory page frames
    protected List<Page> getPageFrames() {
        return pageFrames;
    }

    
    //get the number of pages on disk
    public int getPagesOnDisk() {
        return disk.getPageSizeOnDisk();
    }

    //the number of page hits during the current run 
    public int getPageHits() {
        return pageHits;
    }

  
    //reset the memory state of the page
    public void reset() {
        pageHits = 0;
        pageFrames.clear();
    }
}
