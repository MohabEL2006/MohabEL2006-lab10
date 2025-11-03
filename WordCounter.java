import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
 * This is our main WordCounter class that will 
 * be doing all the work for processing text
 * and files while also using exceptions in 
 * order to tell the user what might have went 
 * wrong like an empty file or an invalid stop word 
 * javac -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" Lab10_Tester.java
 * java -classpath ".;junit-platform-console-standalone-1.7.0-M1.jar" org.junit.runner.JUnitCore Lab10_Tester 
 */
public class WordCounter {

    public static void main(String[] args) {
        Scanner userScanner = new Scanner(System.in);

        System.out.println("Option 1 for processing files. Option 2 for processing texts. Pick.");

        String choice = userScanner.nextLine();

        while (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Invalid option, choose again");
            choice = userScanner.nextLine();
        }

        String stopWord = null;
        if (args.length > 1) {
            stopWord = args[1];
        }

        if (choice.equals("1")) {
            String filePath = null;
            if (args.length > 0) {
                filePath = args[0];
            }

            if (filePath == null || filePath.isEmpty()) {
                System.out.println("enter path");
                filePath = userScanner.nextLine();
            }

            StringBuffer fileContent;

            try {
                fileContent = WordCounter.processFile(filePath);
            } catch (Exception e) {
                System.out.println(e.toString());
                fileContent = new StringBuffer();
            }

            try {
                String result = processText(fileContent, stopWord);
                System.out.println("Found " + result + " words.");
            } catch (Exception e) {
                // FIX: use toString() to include exception type
                System.out.println(e.toString());
            }

        } else {
            System.out.println("Enter text");

            StringBuffer userText = new StringBuffer(userScanner.nextLine());

            try {
                String result = processText(userText, stopWord);
                System.out.println("Found " + result + " words.");
            } catch (Exception e) {
                // FIX: use toString() to include exception type
                System.out.println(e.toString());
            }
        }

        userScanner.close();
    }

    public static String processText(StringBuffer input, String stopWord)
            throws InvalidStopwordException, TooSmallText {

        String result = "";

        if (stopWord == null) {
            // in case that we didn't put a stopword
            result = noStopWord(input);
        } else {
            result = noStopWord(input); // checks first if the text is too small, if it is throws a new exception
            result = stopWord(input, stopWord); // in the case that we do have a stop word
        }

        return result;
    }

    public static StringBuffer processFile(String pathString) throws EmptyFileException {
        Scanner input = new Scanner(System.in);
        File file = new File(pathString);

        while (!file.exists() || !file.isFile()) {
            System.out.println("Did not find file, retry");
            if (input.hasNextLine()) {
                pathString = input.nextLine();
                file = new File(pathString);
            } else {
                break;
            }
        }

        StringBuffer stringBuffer = new StringBuffer();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                stringBuffer.append(reader.nextLine()).append(" ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("didnt find");
        }

        if (stringBuffer.length() > 0) {
            stringBuffer.setLength(stringBuffer.length() - 1);
        }

        if (stringBuffer.length() == 0) {
            throw new EmptyFileException(pathString);
        }

        return stringBuffer;
    }

    public static String stopWord(StringBuffer input, String stopWord) throws InvalidStopwordException {
        int wordCount = 0;

        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");
        Matcher regexMatcher = regex.matcher(input);

        boolean found = false; // gets set to true if the stop word is found; otherwise false

        while (regexMatcher.find()) {
            String curWord = regexMatcher.group();

            if (curWord.equals(stopWord)) { // breaks as soon as we find the stop word
                found = true;
                wordCount++;
                break;
            }

            wordCount++;
        }

        if (!found) { // in the case that we don't find the stop word we raise InvalidStopword
                      // exception
            throw new InvalidStopwordException(stopWord);
        }

        String result = "" + wordCount;

        return result;
    }

    public static String noStopWord(StringBuffer input) throws TooSmallText {
        int wordCount = 0;

        Pattern regex = Pattern.compile("\\b[a-zA-Z0-9']+\\b");
        Matcher regexMatcher = regex.matcher(input);

        while (regexMatcher.find()) { // counts all words without a stop word
            wordCount++;
        }

        if (wordCount < 5) { // if the sentence is too short we raise TooSmallText exception
            throw new TooSmallText(wordCount);
        }

        String result = "" + wordCount;

        return result;
    }

    public static int userInput() {
        System.out.println("enter: ");
        Scanner userInput = new Scanner(System.in);
        int Input = userInput.nextInt();
        userInput.close();
        return Input;
    }
}
