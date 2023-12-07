package software;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCounterLetterFileTxtCommand implements Command{
    @Override
    public Output execute(Input input) {
        try {
            File file = new File(input.get("splat"));
            return outputCounter(file);
        } catch (Exception e){
            return outputException();
        }
    }

    private Output outputException() {
        return new Output() {
            @Override
            public int responseCode() {
                return 404;
            }

            @Override
            public String result(){
                return "File Exception";
            }
        };
    }

    private Output outputCounter(File file) {
        return new Output() {
            @Override
            public int responseCode() {
                return 200;
            }

            @Override
            public String result(){
                try {
                    return wordCounter(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private String wordCounter(File file) throws IOException {
        return wordCounter(new FileReader(file)).toString();
    }

    private Map<String, Integer> wordCounter(FileReader reader) throws IOException {
        return wordCounter(new BufferedReader(reader));
    }

    private Map<String, Integer> wordCounter(BufferedReader reader) throws IOException {
        Map<String, Integer> result = new HashMap<>();
        String line = reader.readLine();
        while (line != null){
            String[] splitString = line.split(" ");
            for (String string : splitString) {
                String upperLetter = string.substring(0, 1).toUpperCase();
                result.put(upperLetter, result.getOrDefault(upperLetter,0)+1);
            }
            line = reader.readLine();
        }
        return result;
    }
}
