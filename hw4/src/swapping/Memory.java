package swapping;
import java.util.ArrayList;
import java.util.Random;

public abstract class Memory {
    private int time = 0;
    private final ArrayList<String> mem = new ArrayList<>();

    // create memory with 100 free pages
    public Memory() {
        for (int i = 0; i < 100; i++) {
        	mem.add(".");
        }
    }

    //get the starting index of where to allocate memory for the provided process.
    public abstract int getNextIndex(ArrayList<String> memory, SimulatedProcess proccess);

    public boolean allocateMemory(SimulatedProcess process) {
        int startingIndex = getNextIndex(mem, process);
        if (startingIndex != -1) {
            for (int i = startingIndex; i < startingIndex + process.getSize(); i++) {
            	mem.set(i, process.getName());
            }
        	Random rand = new Random();
        	double duration = rand.nextInt(5) + 1;
        	System.out.print("Enter timestamp: ");
        	System.out.printf("%.2f", (duration + time) / 60);
        	System.out.println(" seconds");
            time += duration;
            System.out.print("[Enter] Memory map for Process " + process.getName() + ": ");
            printMemoryMap();
            return true;
        }
        return false;
    }

   
    public void deallocateMemory(SimulatedProcess process) {
        for (int i = 0; i < mem.size(); i++) {
            if (mem.get(i).equals(process.getName())) {
            	mem.set(i, ".");
            }
        }
        System.out.print("[Exit] Memory map for Process " + process.getName() + ": ");
        printMemoryMap();
    }

    public void printMemoryMap() {
    	mem.stream().forEach(System.out::print);
        System.out.println();
    }

    public void reset() {
        for (int i = 0; i < mem.size(); i++) {
        	mem.set(i, ".");
        }
    }
}
