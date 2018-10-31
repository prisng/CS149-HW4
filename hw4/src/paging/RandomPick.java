package paging;

import java.util.Random;

// random paging algorithm
public class RandomPick extends Memory {

	// random number generator
    private final Random random = new Random();

    public RandomPick(Disk accessTo) {
        super(accessTo);
    }

    // get a random index to remove
    @Override
    public int getPageIndexToRemove() {
		// size = number of page frames
        return random.nextInt(getPageFrames().size() - 1);
    }
}