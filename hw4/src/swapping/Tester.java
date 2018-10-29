package swapping;

/**
 * Tester class for the output.
 *
 */
public class Tester {

    public static void main(String[] args) {
        String output = "";
        
        Memory[] fitAlgos = {new FirstFitMemory(), new NextFitMemory(), new BestFitMemory()};
        // to print the algorithm names at the end of the output
        String[] fitAlgoNames = {"First Fit", "Next Fit", "Best Fit"};
        
        // loop through first fit, next fit, & best fit
        for (int i = 0; i < fitAlgos.length; i++) {
            System.out.println(fitAlgoNames[i]);
            
            CPUSimulator sys = new CPUSimulator(fitAlgos[i]);
            
            // running the simulator 5 times
            for (int j = 0; j < 5; j++) {
                sys.generateProcesses();
                sys.start();
                sys.reset();
                System.out.println();
            }
 
            output += fitAlgoNames[i] + (" ") + sys.printStats() + "\r\n";
            System.out.println();
        }
        
        System.out.println(output);
    }

}