package swapping;

import java.util.ArrayList;

public class NextFit extends Memory {

    private int lastIndex;

    @Override
    // finds index for where to allocate memory
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        int start = -1; 
        int freeCount = 0;
        boolean wrapAround = true; 
        
        for (int i = lastIndex; i < memory.size(); i++) { 
            // checks if memory is free
			if (memory.get(i).equals(".")) { 
                if (start == -1)
                    start = i; 
                freeCount++;
            } else {
			// if not free then reset start and freeCount
                freeCount = 0; 
                start = -1; 
            }	// if number of free spaces is enough for process then return this start index
            if (freeCount == proccess.getSize()) { 
                lastIndex = i; 
                return start;
            }   
            if (wrapAround && i == memory.size() - 1) {
                i = 0; 
                freeCount = 0;
                start = -1;
                wrapAround = false;
            }
        }
        return -1;
    }
}