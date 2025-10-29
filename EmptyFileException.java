import java.io.IOException;

/* this class is meant to flag the exception in case 
 * a wrong file name is provided or its empty */

public class EmptyFileException extends IOException {
    public EmptyFileException(String fileName) {
        super(fileName + " was empty");
    }
}
