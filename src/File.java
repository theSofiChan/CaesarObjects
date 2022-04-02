import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    private String filePath;

    public File(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * This method is used to generate a string that contains all data from the file to be cyphered or deciphered later
     * @return Returns a string that contains all symbols from the text including \n
     */
    public String read(){
        StringBuilder text = new StringBuilder();
        try (FileReader in = new FileReader(this.filePath);
             BufferedReader reader = new BufferedReader(in)) {
            while (reader.ready()) {
                String line = reader.readLine();
                text.append(line);
                text.append('\n');
            }
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
