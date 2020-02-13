package company;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputCommand {
    private List<String> lines;

    public InputCommand(BufferedReader reader) {
            readLines(reader);
    }

    private void readLines(BufferedReader reader) {
        try {
            String line = reader.readLine();
            lines = new ArrayList<>();
            while (line != null && !line.equals("*end")) {
                lines.add(line.trim());
                line = reader.readLine();
            }
        } catch (IOException e) {

        }
    }

    private String cleanTheLine(String dirty) {
        return dirty.replace("|", "");
    }


    public List<String> cleanLines() {
        var cleanLines = new ArrayList<String>();
        for ( String line : lines) {
            cleanLines.add(cleanTheLine(line));
        }
        return cleanLines;
    }



    public int selectionStart() {
        int charactersSoFar = 0;
        for (String line : lines) {
            int index = line.indexOf("|");
            if (index != -1)
                return charactersSoFar + index;
            else
                charactersSoFar += line.length() + System.lineSeparator().length();
        }
        return charactersSoFar - System.lineSeparator().length();
    }
}
