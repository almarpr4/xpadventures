import company.InputCommand;
import company.TextModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @Test
    public void FileWithACursorInTheMiddleInput() throws IOException {
        String contents = new String ( Files.readAllBytes( Paths.get("src/test/resources/cursorinthemiddle.txt") ) );
        InterpretCommands(contents);
    }

    @Test
    public void ParagraphAfterParagraph() throws IOException {
        String contents = new String ( Files.readAllBytes( Paths.get("src/test/resources/paragraphafterparagraph.txt") ) );
        InterpretCommands(contents);
    }

    @Test
    public void FileWithACursorAtTheEndInput() throws IOException {
        String contents = new String ( Files.readAllBytes( Paths.get("src/test/resources/cursorattheend.txt") ) );
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
        InputCommand input = new InputCommand(reader);
        model.setLines(input.cleanLines());
        model.setSelectionStart(input.selectionStart());
    }



    private void CompareOutput(BufferedReader reader) throws IOException {
        String expected = ExpectedOutput(reader);
        Assert.assertEquals(expected, model.TestText());
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
