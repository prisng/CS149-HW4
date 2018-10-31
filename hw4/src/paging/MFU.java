package paging;

import java.util.ArrayList;
import java.util.List;

public class MFU extends Memory {
	
    private final List<Page> refCounter = new ArrayList<>();

    public MFU(Disk d) {
        super(d);
    }

  
    @Override
    public int getPageIndexToRemove() {
        // sort by highest ref count
        refCounter.sort((p1, p2) -> Integer.compare(p2.getReferenceCount(), p1.getReferenceCount()));
        Page p = refCounter.remove(0);
        p.setReferenceCount(0);
        return getPageFrames().indexOf(p);
    }

    @Override
    public void reset() {
        super.reset();
        refCounter.stream().forEach((p) -> {
            p.setReferenceCount(0);
        });
        refCounter.clear();
    }
    
    @Override
    public Page requestPage(int page, int refsMade) {
        Page p = super.requestPage(page, refsMade);
        if (!refCounter.contains(p)) {
            refCounter.add(p);	
        }
        p.setReferenceCount(p.getReferenceCount() + 1);
        return p;
    }

}
