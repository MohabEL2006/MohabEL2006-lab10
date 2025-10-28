import java.io.IOException;
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
        compile();
    }

    public static String processText(StringBuffer input, String stopWord)
            throws InvalidStopwordException, TooSmallText {
        String result = "";
        if (stopWord == null) {
            result = noStopWord(input);
        } else {
            result = stopWord(input, stopWord);
        }
        return result;
    }

    public static StringBuffer processFile(String fileName) throws EmptyFileException {
        return null;
    }

    public static String noStopWord(StringBuffer input) { // helper function
        int wordCount = -1;
        for (int i = 0; i < input.length(); i++) { // so far counts total words without the stop word
            if (input.charAt(i) == ' ') {
                wordCount++;
            }
        }
        String result = "" + wordCount;
        return result;
    }

    public static String stopWord(StringBuffer input, String stopWord) { // helper function
        int wordCount = -1;
        Pattern regex = Pattern.compile(stopWord);
        Matcher regexMatcher = regex.matcher(input);
        if (regexMatcher.find()) {
            System.out.println(regexMatcher.group());
        }
        String result = "" + wordCount;
        return result;
    }

    public static String userInput() {
        System.out.println("enter: ");
        Scanner userInput = new Scanner(System.in);
        String Input = userInput.nextLine();
        userInput.close();
        return Input;
    }

    public static void compile() {
        try {
            Process process = Runtime.getRuntime()
                    .exec("javac -classpath \".;junit-platform-console-standalone-1.7.0-M1.jar\" Lab10_Tester.java");
            process.waitFor();
            System.out.println("Compiled code!");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}