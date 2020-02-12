import company.TextModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CustomerTests {

    private TextModel model;

    @Before
    public void CreateModel() {
        model = new TextModel();
    }

    @Test
    public void StringInput() throws IOException {
        String commands =
                "*input" + System.lineSeparator() +
                "some line" + System.lineSeparator() +
                "*end" + System.lineSeparator() +
                "*enter" + System.lineSeparator() +
                "*display" + System.lineSeparator() +
                "*output" + System.lineSeparator() +
                "some line" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "<P>|</P>";
        InterpretCommands(commands);
    }

    @Test
    public void FileInput() throws IOException {
        String contents = new String ( Files.readAllBytes( Paths.get("src/test/resources/notepadfileInput.txt") ) );
        InterpretCommands(contents);
    }

    private void InterpretCommands(String commands) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(commands));

        String line = reader.readLine();
        while ( line != null) {
            if (line.equals("*enter"))
                model.Enter();
            if (line.equals("*display"))
                System.out.print(String.format("display\n%s\n", model.TestText()));
            if (line.equals("*output"))
                CompareOutput(reader);
            if (line.equals("*input"))
                SetInput(reader);
            line = reader.readLine();
        }
    }



    private void SetInput(BufferedReader reader) throws IOException {
        String[] input = ArrayToEnd(reader);
        model.setLines(input);
    }

    private String[] ArrayToEnd(BufferedReader reader) throws IOException {
        ArrayList result = new ArrayList();
        String line = reader.readLine();
        while (line != null && !line.equals("*end")) {
            result.add(line.trim());
            line = reader.readLine();
        }
        String[] answer = new String[result.size()];
        result.toArray(answer);
        return answer;
    }

    private void CompareOutput(BufferedReader reader) throws IOException {
        String expected = ExpectedOutput(reader);
        Assert.assertEquals(expected, model.TestText());
    }

    private void CompareOutput(BufferedReader reader, String message) throws IOException {
        String expected = ExpectedOutput(reader);
        Assert.assertEquals(message, expected, model.TestText());
    }

    private String ExpectedOutput(BufferedReader reader) throws IOException {
        return ReadToEnd(reader);
    }

    private String ReadToEnd(BufferedReader reader) throws IOException {
        String result = "";
        String line = reader.readLine();
        while (line != null && !line.equals("*end")) {
            result += line;
            result += System.lineSeparator();
            line = reader.readLine();
        }
        return result;
    }
}
