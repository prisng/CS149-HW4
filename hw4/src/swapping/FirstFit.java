package swapping;

import java.util.ArrayList; 

public class FirstFit extends Memory {

    @Override
    // overrides get index for first fit
    // a simpler algorithm finds the first available slot
    public int getNextIndex(ArrayList<String> mem, SimulatedProcess proccess) {
        int start = -1; 
        int freeCount = 0;
        for (int j = 0; j < mem.size(); j++) {
        	// check for free space
            if (mem.get(j).equals(".")) {
                if (start == -1) {
                    start = j;	
                }
                // increment free block counter
                freeCount++;
            } else { 	// reset free block counter and start index
                freeCount = 0;
                start = -1; 
            }
            if (freeCount == proccess.getSize()) {
                return start;	
            }
        }
        return -1;
    }
}