import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* this is our main WordCounter class that will 
 * be doing all the work for processing text
 * and files while also using exceptions in 
 * order to tell the user what might have went 
 * wrong like an empty file or a invalid stop word 
 * javac -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" Lab10_Tester.java
 * java -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" org.junit.runner.JUnitCore Lab10_Tester 
 */

public class WordCounter {

    public static void main(String[] args) {
    }

    public static String processText(StringBuffer input, String stopWord)
            throws InvalidStopwordException, TooSmallText {
        String result = "";

        if (stopWord == null) {                 // incase that we didnt put a stopword
            result = noStopWord(input);
        } else {
            result = noStopWord(input);         // checks first if the text is too small, if it is throws a new exception
            result = stopWord(input, stopWord); // in the case that we do have a stop word
        }

        return result;
    }

    public static StringBuffer processFile(String fileName) throws EmptyFileException, TooSmallText {

        StringBuffer output = new StringBuffer();
        
        // want to modify this code so that if the provided fileName is invalid it would prompt us to enter a correct one
        // also want to raise exceptions when the file is empty or the text in the file is too short 
        
        try {
            
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(fileName)));
            
            String line = reader.readLine();
            
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
            
            reader.close();
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static String userInput() {

        System.out.println("enter: ");

        Scanner userInput = new Scanner(System.in);

        String Input = userInput.nextLine();

        userInput.close();

        return Input;
    }

    public static String stopWord(StringBuffer input, String stopWord) throws InvalidStopwordException {

        int wordCount = 0;

        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");

        Matcher regexMatcher = regex.matcher(input);      

        boolean found = false;                          // gets set to true if the stop word is found if not we keep it false

        while (regexMatcher.find()) {
            String curWord = regexMatcher.group();
            
            if (curWord.equals(stopWord)) {             // breaks as soon as we find the stop word
                found = true;
                wordCount++;
                break;
            }
            
            wordCount++;
        }

        if (!found) {                   // in the case that we dont find the stop word we raise InvalidStopWord exception
            throw new InvalidStopwordException(stopWord);
        }

        String result = "" + wordCount;

        return result;
    }

    public static String noStopWord(StringBuffer input) throws TooSmallText {

        int wordCount = 0;

        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");

        Matcher regexMatcher = regex.matcher(input);

        while (regexMatcher.find()) {           // counts all words without a stop word
            wordCount++;
        }

        if (wordCount < 5) {                    // if the sentence is too short we raise TooSmallText exception
            throw new TooSmallText(wordCount);
        }

        String result = "" + wordCount;

        return result;
    }
}