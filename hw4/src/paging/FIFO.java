package paging;

public class FIFO extends Memory {
	
	// ctor
    public FIFO(Disk disk) {
        super(disk);
    }

    @Override
    public int getPageIndexToRemove() {
    		return 0;
    }
}