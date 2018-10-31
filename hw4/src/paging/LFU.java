package paging;
import java.util.ArrayList;
import java.util.List;

public class LFU extends Memory {
	
    private final List<Page> references = new ArrayList<>();	// keep track of page access

    public LFU(Disk d) {
        super(d);
    }

    @Override
    public int getPageIndexToRemove() {
        references.sort((p1, p2) -> Integer.compare(p1.getReferenceCount(), p2.getReferenceCount()));

        Page p = references.remove(0);
        p.setReferenceCount(0);

        return getPageFrames().indexOf(p);
    }
    
    // resets reference counter
    @Override
    public void reset() {
        super.reset();
        references.stream().forEach((p) ->
        {
            p.setReferenceCount(0);
        });
        references.clear();
    }

    // returns the page being requested
    @Override
    public Page requestPage(int page, int numReferences) {
        Page p = super.requestPage(page, numReferences);
        if (!references.contains(p)) {
            references.add(p);	
        }
        p.setReferenceCount(p.getReferenceCount() + 1);

        return p;
    }
    
}
