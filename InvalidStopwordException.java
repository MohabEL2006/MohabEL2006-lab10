/* this class is meant to flag the exception incase 
 * the user provides an invalid stop word */

public class InvalidStopwordException extends Exception {
    public InvalidStopwordException(String stopWord) {
        super("InvalidStopwordException: Couldn't find stopword: " + stopWord);
    }
}