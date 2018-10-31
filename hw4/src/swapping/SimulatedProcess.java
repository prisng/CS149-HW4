package swapping;

public class SimulatedProcess {

    private final int parentId;
    public static int nextID = 0;
    private final char name;
    private final int size;
    private int duration;
    private boolean finished;
    private static final String NAME_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // creates a process with specified size/duration
    public SimulatedProcess(int aSize, int aDuration) {
        parentId = nextID++;
        this.name = NAME_CHARS.charAt(parentId % NAME_CHARS.length());
        this.size = aSize;
        this.duration = aDuration;
    }
    
    public String getName() {
        return String.valueOf(name);
    }

    public int getSize() {
        return size;
    }

    public boolean finished() {
        return finished;
    }
    
    public void executing() {
        duration -= 1;
        if (duration <= 0) {
            finished = true;
        }
    }
   

    @Override
    public String toString() {
        String result = "Name=" + name + "/Size=" + size + "/Duration=" + duration;
        return result;
    }

}
