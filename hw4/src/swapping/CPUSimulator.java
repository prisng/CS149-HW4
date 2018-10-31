package swapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;

/**
 * Simulates a CPU system for workload generation.
 */
public class CPUSimulator {

	private final Memory memory;	// will use a specified memory algorithm
	private int currentTime = 0;
	private final LinkedList<SimulatedProcess> processes = new LinkedList<>();			// jobs structured as linked list
	private final ArrayList<SimulatedProcess> runningProcesses = new ArrayList<>();		// keep track of running processes
	private final Random rand = new Random(0);							// for random process generation
	private static final int[] PROCESS_SIZE = {5, 11, 17, 31};			// processes sizes: 5, 11, 17, or 31 MB
	private static final int[] PROCESS_DURATION = {1, 2, 3, 4, 5};		// processes durations: 1, 2, 3, 4, or 5 seconds
	public static final int SECONDS_TO_RUN = 60;
	private final List<Integer> stats = new ArrayList<>();

	// creates a CPU simulator with a specified memory algorithm (best fit, next fit, or first fit)
	public CPUSimulator(Memory algorithm) {
		this.memory = algorithm;
	}

	// resets the CPU simulator
	public void reset() {
		processes.clear();
		runningProcesses.clear();
		currentTime = 0;
		memory.reset();
		SimulatedProcess.nextID = 0;
	}

	// generates new processes to add to scheduler
	public void generateProcesses() {
		List<Integer> usedSizes = new ArrayList<>();
		List<Integer> usedDuration = new ArrayList<>();
		int size_idx = rand.nextInt(PROCESS_SIZE.length), duration_idx = rand.nextInt(PROCESS_DURATION.length);

		for (int i = 0; i < 160; i++) { // Evenly distribute processes durations and sizes
			while (usedDuration.contains(duration_idx)) { // Loop until unused index found
				duration_idx = rand.nextInt(PROCESS_DURATION.length);
			}

			usedDuration.add(duration_idx); // Add to used index
			if (usedDuration.size() == PROCESS_DURATION.length) {
				usedDuration.clear(); // Clear used list if list contains all possible indexes
			}

			while (usedSizes.contains(size_idx)) { // Loop until unused index found
				size_idx = rand.nextInt(PROCESS_SIZE.length);
			}

			usedSizes.add(size_idx); // Add to used index
			if (usedSizes.size() == PROCESS_SIZE.length) {
				usedSizes.clear(); // Clear used list if list contains all possible indexes
			}
			processes.add(new SimulatedProcess(PROCESS_SIZE[size_idx], PROCESS_DURATION[duration_idx]));
		}
	}

	// starts the CPU simulator
	public void start() {
		int processesSwappedIn = 0; // keeps track number of processes swapped in
		// 1 loop = 1 second
		while (currentTime <= SECONDS_TO_RUN) {
			Iterator<SimulatedProcess> iter = runningProcesses.iterator();
			// execute all processes
			while (iter.hasNext()) {
				SimulatedProcess p = iter.next();
				p.executing(); 
				// if the process is finished, remove it from the running process list
				if (p.finished()) {
					memory.deallocateMemory(p); 
				}
			}
			
			// add first process to process list
			SimulatedProcess process = processes.peek();
			if (memory.allocateMemory(process)) { 
				System.out.println("Process added: " + process);
				runningProcesses.add(processes.poll());
				processesSwappedIn++;
			}

			currentTime += 1;
		}
		stats.add(processesSwappedIn);
	}
	
	// print statistics of average amount of processes swapped
	public String printStats() {
		OptionalDouble avgSwap = stats.stream().mapToDouble(a -> a).average();
		return "Average processes swapped in: " + (avgSwap.isPresent() ? avgSwap.getAsDouble() : 0);
	}

}
