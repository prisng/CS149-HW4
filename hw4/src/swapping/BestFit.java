package swapping;

import java.util.ArrayList;

public class BestFit extends Memory {

    @Override
    public int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess) {
        ArrayList<Fitment> possibleIndices = new ArrayList<Fitment>();
        Fitment f = null;
       
        // free space in memory
        int freeCount = 0;
        int start = -1;
        
        for (int j = 0; j < memory.size(); j++) {
        	// if the space is free, store the starting index
            if (memory.get(j).equals(".")) {
                if (start == -1) {
                    start = j;	
                }
                // increment free block counter
                freeCount++;
                if (f != null)
                    f.freeSpaceAfter++;
            } else {
            	// restart starting index and block counter
                f = null;
                freeCount = 0;
                start = -1;
            }
            if (freeCount == proccess.getSize()) {
                f = new Fitment(start, freeCount);
                possibleIndices.add(f);
            }
        }
        possibleIndices.sort((f1, f2) -> Integer.compare(f1.freeSpaceAfter, f2.freeSpaceAfter));
        
        return possibleIndices.isEmpty() ? -1 : possibleIndices.get(0).start;
    }

    
    class Fitment {

        int start = 0;
        int freeSpaceAfter = 0;

        public Fitment(int start, int freeSpaceAfter) {
            this.start = start;
            this.freeSpaceAfter = freeSpaceAfter;
        }

    }
}