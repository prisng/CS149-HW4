package paging;

import java.util.ArrayList;
import java.util.List;

public class Disk {

    private final List<Page> pages = new ArrayList<>();

    // creates 10 pages on this disk
    public Disk() {
        for (int i = 0; i < 10; i++) {
        	pages.add(new Page(i));	
        }
    }

    // gets page of index p
    public Page getPage(int p) {
        return p < pages.size() ? pages.get(p) : null;
    }

    // gets number of pages on disk
    public int getPageSizeOnDisk() {
        return pages.size();
    }
}
