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
        
        if (stopWord == null) { // incase that we didnt put a stopword
            result = noStopWord(input);
        } else {
            result = noStopWord(input);         // checks if text is too small first
            result = stopWord(input, stopWord); // in the case that we do have a stop word
        }
        
        return result;
    }

    public static StringBuffer processFile(String fileName) throws EmptyFileException {
        return null;
    }

    public static String userInput() {
        
        System.out.println("enter: ");
        
        Scanner userInput = new Scanner(System.in);
        
        String Input = userInput.nextLine();
        
        userInput.close();
        
        return Input;
    }

    public static String stopWord(StringBuffer input, String stopWord) throws InvalidStopwordException {
        
        int wordCount = 1;                                              // even if the first word is the stop word the count would still be 1
        
        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");
        
        Matcher regexMatcher = regex.matcher(input);
        
        boolean found = false;

        while (regexMatcher.find()) {
            String curWord = regexMatcher.group();
            if (curWord.equalsIgnoreCase(stopWord)) {
                found = true;
                break;
            }
            wordCount++;
        }
        
        if(!found) {
            throw new InvalidStopwordException(stopWord);
        }
        
        String result = "" + wordCount;
        
        return result;
    }

    public static String noStopWord(StringBuffer input) throws TooSmallText {
        
        int wordCount = 0;
        
        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");
        
        Matcher regexMatcher = regex.matcher(input);

        while (regexMatcher.find()) {
            wordCount++;
        }
        
        
        if (wordCount < 5 ) {
            throw new TooSmallText(wordCount);
        }
        
        String result = "" + wordCount;
        
        return result;
    }
}