import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Text {

    private String data;

    public Text(String data) {
        this.data = data;
    }

    /**
     * Method to write data to chosen file path
     * @param target String that contains a path to the target file to write the data
     */
    public void write(String target){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(target))){
            writer.write(this.data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that cyphers data using the Caesar's cypher
     * @param key The integer key(-20~20)
     * @param ALPHABET Alphabet to process text with
     * @return Returns cyphered text
     */
    public String cypher(int key, ArrayList<Character> ALPHABET) throws IOException {
        char[] charArray = this.data.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char letter = charArray[i];
            int indexInAlphabet = ALPHABET.indexOf(letter);
            char cypheredLetter = letter;
            if (indexInAlphabet != -1) {
                cypheredLetter = ALPHABET.get(((ALPHABET.size() + indexInAlphabet + key) % ALPHABET.size()));
            }
            stringBuilder.append(cypheredLetter);
        }
        return stringBuilder.toString();
    }


    /**
     * Method that deciphers data using the Caesar's cypher
     * @param key The integer key(-20~20)
     * @param ALPHABET Alphabet to process text with
     * @return Returns deciphered text
     */
    public String decipher(int key, ArrayList<Character> ALPHABET) throws IOException {
        char[] charArray = this.data.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char letter = charArray[i];
            int indexInAlphabet = ALPHABET.indexOf(letter);
            char cypheredLetter = letter;
            if (indexInAlphabet != -1) {
                cypheredLetter = ALPHABET.get(Math.floorMod((indexInAlphabet - key), ALPHABET.size()));
            }
            stringBuilder.append(cypheredLetter);
        }
        return stringBuilder.toString();
    }


    /**
     * Method that deciphers data using Caesar's cypher by means of brute force
     * @param ALPHABET Alphabet to process text with
     * @return Returns text deciphered by means of brute force
     */
    public String bruteForce(ArrayList<Character> ALPHABET) throws IOException {
        char[] charArray = this.data.toCharArray();
        int key = -20;
        while (key <= charArray.length) {
            StringBuilder innerStringBuilder = new StringBuilder();
            for (int i = 0; i < charArray.length; i++) {
                char letter = charArray[i];
                int indexInAlphabet = ALPHABET.indexOf(letter);
                int newindex = Math.floorMod((indexInAlphabet - key), ALPHABET.size());
                char cypheredLetter = letter;
                if (indexInAlphabet != -1) {
                    cypheredLetter = ALPHABET.get(newindex);
                }
                innerStringBuilder.append(cypheredLetter);
            }
            Pattern stringPattern = Pattern.compile("(,\\s).+(\\.\\s)");
            Matcher m = stringPattern.matcher(innerStringBuilder.toString());
            if (m.find() && innerStringBuilder.toString().contains("after")) {
                return innerStringBuilder.toString();
            }
            key++;
        }
        return null;
    }

    /**
     * Method that deciphers data using Caesar's cypher by means of statistical analysis
     * @param sourceText String that contains data from a file to analyse
     * @param ALPHABET Alphabet to process text with
     * @return Returns text deciphered by means of comparison with similar texts of the same author
     */
    public String statistics(String sourceText, ArrayList<Character> ALPHABET) throws IOException {
        char[] allLettersSource = sourceText.toCharArray();
        Stream<Character> charStreamSource = new String(allLettersSource).chars().mapToObj(i -> (char) i);
        Map<Character, Long> charsSourceText = charStreamSource
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        char[] allLettersText = this.data.toCharArray();
        Stream<Character> charStreamText = new String(allLettersText).chars().mapToObj(i -> (char) i);
        Map<Character, Long> charsText = charStreamText
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        long maxValueInCharsSourceText = (Collections.max(charsSourceText.values()));
        Character maxKeySource = 'a';
        for (Map.Entry<Character, Long> entry : charsSourceText.entrySet()) {
            if (entry.getValue() == maxValueInCharsSourceText) {
                maxKeySource = entry.getKey();
            }
        }
        long maxValueInCharsText = (Collections.max(charsText.values()));
        Character maxKeyText = 'a';
        for (Map.Entry<Character, Long> entry : charsText.entrySet()) {
            if (entry.getValue() == maxValueInCharsText) {
                maxKeyText = entry.getKey();
            }
        }
        int indexInAlphabetSourceMax = ALPHABET.indexOf(maxKeySource);
        int indexInAlphabetTextMax = ALPHABET.indexOf(maxKeyText);
        int key = (indexInAlphabetTextMax - indexInAlphabetSourceMax);
        char[] charArray = this.data.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char letter = charArray[i];
            int indexInAlphabet = ALPHABET.indexOf(letter);
            char cypheredLetter = letter;
            if (indexInAlphabet != -1) {
                cypheredLetter = ALPHABET.get(Math.floorMod((indexInAlphabet - key), ALPHABET.size()));
            }
            stringBuilder.append(cypheredLetter);
        }
        return stringBuilder.toString();
    }

}
