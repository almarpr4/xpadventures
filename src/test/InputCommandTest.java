import company.InputCommand;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class InputCommandTest {


    @Test
    public void EmptyCommand() {
        var command = new InputCommand(new BufferedReader(new StringReader("")));
        assertEquals(0, command.cleanLines().size());
    }

    @Test
    public void OneLineCommand() {
        String oneLineString = "one line" + System.lineSeparator()
                + "*end";

        BufferedReader reader = new BufferedReader(new StringReader(oneLineString));
        var command = new InputCommand(reader);
        assertEquals(1, command.cleanLines().size());
    }


    @Test
    public void OneDirtyLine() {
        var command = new InputCommand(new BufferedReader(new StringReader("a|b\n*end")));

        assertEquals("ab", command.cleanLines().get(0));
        assertEquals(1,command.selectionStart());
    }

    @Test
    public void OneLineCommandWithCursorAtTheEnd() {
        String oneLineString =
                "one line" + System.lineSeparator()
                +"*end";
        var command = new InputCommand(new BufferedReader(new StringReader(oneLineString)));

        assertEquals(1, command.cleanLines().size());
        assertEquals(8,command.selectionStart());
    }

}
