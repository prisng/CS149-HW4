package paging;
import java.util.LinkedList;

public class LRU extends Memory {

	// linked list to represent cache
    private final LinkedList<Page> cache = new LinkedList<>();

    public LRU(Disk d) {
        super(d);
    }

    @Override
    public int getPageIndexToRemove() {
        return getPageFrames().indexOf(cache.poll());
    }
   
    // clear cache list
    @Override
    public void reset() {
        super.reset();
        cache.clear();
    }

    @Override
    public Page requestPage(int page, int refsMade) {
        Page p = super.requestPage(page, refsMade);
        // if page is in the cache, move page to end of list
        if (cache.contains(p)) {
            cache.remove(p);	
        }
        // add page to end of cache list (most recent)
        cache.addLast(p);
        return p;
    }

}
