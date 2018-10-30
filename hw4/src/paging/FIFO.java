package paging;

// FIFO Paging class. FIFO (First In First Out) works according to the First Come, First Served principle

public class FIFO extends Memory
{
	
    public FIFO(Disk disk)
    {
        super(disk);
    }

    @Override
    public int getPageIndexToRemove()
    {
    	return 0;
    }
}