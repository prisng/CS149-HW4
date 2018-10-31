package paging;

public class Page {

    private final int pageNumber;
    private int reference;

    // create a page with a page number
    public Page(int pageNumber) {
    	this.pageNumber = pageNumber;
    }

    // returns current page number
    public int getPageNumber() {
        return this.pageNumber;
    }

    // returns current page's reference count
    public int getReferenceCount() {
        return reference;
    }

    // sets current page's reference to input
    public void setReferenceCount(int ref) {
        reference = ref;
    }

    @Override
    public String toString() {
        return this.pageNumber + " ";
    }
}
