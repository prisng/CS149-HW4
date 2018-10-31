package paging;

public class Page {

    private final int pageNumber;
    private int reference;

    // create a page with a page number
    public Page(int pageNumber) {
    	this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public int getReferenceCount() {
        return reference;
    }

    public void setReferenceCount(int ref) {
        reference = ref;
    }

    @Override
    public String toString() {
        return this.pageNumber + " ";
    }
}
