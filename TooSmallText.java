/* this class is meant to raise exceptions 
 * in case the provided is too small to pass */

public class TooSmallText extends Exception {
    public TooSmallText(int count) {
        super("Only found " + count + " words.");
    }
}
