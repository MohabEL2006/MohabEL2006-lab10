/* this class is meant to flag the exception in case 
 * a wrong file name is provided or its empty */

public class EmptyFileException extends Exception {
    public EmptyFileException(String fileName) {
        super("" + fileName + " was empty");
    }
}
