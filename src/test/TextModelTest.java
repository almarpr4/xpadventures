import company.TextModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TextModelTest {

    private TextModel model;

    @Before
    public void CreateModel() {
        model = new TextModel();
    }

    @Test
    public void TestNoLines() {
        model.setLines(new String[0]);
        assertEquals(0, model.getLines().size());
    }

    @Test
    public void TestNoProcessing() {
        model.setLines(new String[] { "hi", "there", "chet"});
        assertEquals(3, model.getLines().size());
    }

    @Test
    public void TestOneEnter() {
        model.setLines(new String[] {"hello world" });
        model.setSelectionStart(5);
        model.InsertParagraphTag();
        assertEquals(3, model.getLines().size());
        assertEquals(16, model.getSelectionStart());
    }

    @Test
    public void TestEmptyText() {
        model.setLines(new String[0]);
        model.InsertParagraphTag();
        assertEquals(1, model.getLines().size());
        assertEquals(3, model.getSelectionStart());
    }


    @Test
    public void InsertWithCursorAtLineStart () {
        model.setLines(new String[] { "<P>one</P>", "", "<P>two</P>"});
        model.setSelectionStart(14);
        model.InsertParagraphTag();
        assertEquals("<P>two</P>", model.getLines().get(2));
    }

    @Test
    public void EmptyModel() {
        model.Enter();
        assertEquals("<P>|</P>\n", model.TestText());
    }
/*
    @Test
    public void TestLineContainingCursorDirectly() {
        // todo?
    }*/

    @Test
    public void ControlTwo() {
        model.setLines(new String[] {"<P>The Heading</P>" });
        model.changeToH2();
        assertEquals("<H2>The Heading</H2>",  model.getLines().get(0));
    }

    @Test
    public void ControlS() {
        model.setLines(new ArrayList<String>());
        model.altS();
        assertEquals("<sect1><title></title>",  model.getLines().get(0));
        assertEquals("</sect1>", model.getLines().get(1));
    }


    @Test
    public void AltSWithText() {
        model.setLines(new String[] {"<P></P>"});
        model.setSelectionStart(7);
        model.altS();
        assertEquals("<sect1><title></title>",  model.getLines().get(1));
        assertEquals("</sect1>", model.getLines().get(2));
        assertEquals(22, model.getSelectionStart());
    }
}
