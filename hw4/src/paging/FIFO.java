package paging;

public class FIFO extends Memory {
	
    public FIFO(Disk disk) {
        super(disk);
    }

    @Override
    public int getPageIndexToRemove() {
    	return 0;
    }
}