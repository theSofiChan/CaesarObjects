import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Menu {
    private static final ArrayList<Character> ALPHABET = new ArrayList<>();

    public static void main(String[] args) {
        Collections.addAll(ALPHABET, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', ',', '.', ' ', '-',
                '\"', '\n', '\t', '–', ':', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '‘');
        menu();
    }

    /**
     * The user should input an integer key to be used in methods cypher() and decipher()
     * @return Returns integer key
     */
    public static int keyInput() {
        System.out.println("You should input an integer in the range of -20 to 20");
        try {
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                int key = scanner.nextInt();
                if (key > 20 || key < -20) {
                    keyInput();
                }
                return key;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("You should input an integer in the range of -20 to 20, restarting the program");
            keyInput();
        }
        return 0;
    }

    /**
     * Main menu where user chooses operations with files
     */
    public static void menu() {
        System.out.println("Choose an action");
        System.out.println("1 to cypher");
        System.out.println("2 to decipher");
        System.out.println("3 to decipher with brute force");
        System.out.println("4 to decipher with statistical analysis");
        try {
            Scanner scanner = new Scanner(System.in);
            switch (scanner.nextInt()) {
                case 1 -> {
                    System.out.println("Path to file to cypher: ");
                    String sourceCypher = scanner.next();
                    System.out.println("Path to the target file: ");
                    String targetCypher = scanner.next();
                    int key=keyInput();
                    File fileRead=new File(sourceCypher);
                    String fileData=fileRead.read();
                    Text textObject=new Text(fileData);
                    try{
                        String fileCyphered = textObject.cypher(key,ALPHABET);
                        Text cypheredText = new Text(fileCyphered);
                        cypheredText.write(targetCypher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Path to file to decipher: ");
                    String sourceDecypher = scanner.next();
                    System.out.println("Path to the target file: ");
                    String targetDecypher = scanner.next();
                    int key=keyInput();
                    File fileRead=new File(sourceDecypher);
                    String fileData=fileRead.read();
                    Text textObject=new Text(fileData);
                    try{
                        String fileDeciphered = textObject.decipher(key,ALPHABET);
                        Text decipheredText = new Text(fileDeciphered);
                        decipheredText.write(targetDecypher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                case 3 -> {
                    System.out.println("Path to file to decipher: ");
                    String sourceBruteForce = scanner.next();
                    System.out.println("Path to the target file: ");
                    String targetBruteForce = scanner.next();
                    File fileRead=new File(sourceBruteForce);
                    String fileData=fileRead.read();
                    Text textObject=new Text(fileData);
                    try{
                        String fileDecipheredBF = textObject.bruteForce(ALPHABET);
                        Text decipheredTextBF = new Text(fileDecipheredBF);
                        decipheredTextBF.write(targetBruteForce);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 4 -> {
                    System.out.println("Path to file to the source text: ");
                    String sourceTextStats = scanner.next();
                    System.out.println("Path to file to decipher: ");
                    String DecipherStats = scanner.next();
                    System.out.println("Path to the target file: ");
                    String targetStats = scanner.next();
                    File fileReadSource=new File(sourceTextStats);
                    String SourceData=fileReadSource.read();
                    File fileRead=new File(DecipherStats);
                    String fileData=fileRead.read();
                    Text textObject=new Text(fileData);
                    try{
                        String fileDecipheredStats = textObject.statistics(SourceData,ALPHABET);
                        Text decipheredTextStats = new Text(fileDecipheredStats);
                        decipheredTextStats.write(targetStats);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                default -> {
                    System.out.println("Something went wrong");
                    menu();
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong");
            menu();
        }
    }
}
