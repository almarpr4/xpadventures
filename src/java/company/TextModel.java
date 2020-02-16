package company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TextModel {
    private List<String> lines;
    private int selectionStart;

    public TextModel() {
        lines = new ArrayList<String>();
    }

    private int LineContainingCursor() {
        int length = 0;
        int lineNr = 0;
        int cr = System.lineSeparator().length();
        for (String s : lines) {
            if (length <= selectionStart
                    && selectionStart < length + s.length() + cr)
                break;
            length += s.length() + cr;
            lineNr++;
        }
        return lineNr;
    }

    public void Enter() {
        InsertParagraphTag();
    }

    public void InsertParagraphTag() {
        //
        // On Enter, we change the TextModel lines to insert, after the line
        // containing the cursor, a blank line, and a line with <P></P>. We set
        // the new cursor location to be between the P tags: <P>|</P>.
        //
        // handle empty array special case (yucch)
        if (lines.size() == 0) {
            lines.add("<P></P>");
            selectionStart = 3;
            return;
        }

        int index = LineContainingCursor() + 1;
        List<String> newParagraph = NewParagraph();
        lines.addAll(index, newParagraph);

        // set cursor location
        selectionStart = NewSelectionStart(LineContainingCursor() + 2);
    }

    private int NewSelectionStart(int cursorLine) {
        return sumLineLengths(cursorLine) + "<p>".length();
    }

    private int sumLineLengths(int cursorLine) {
        int length = 0;
        for (int i = 0; i < cursorLine; i++)
            length += lines.get(i).length() + System.lineSeparator().length();
        return length;
    }

    public List<String> NewParagraph() {
        List<String> temp = new ArrayList();
        temp.add("");
        temp.add("<P></P>");
        return temp;
    }

    public String[] getLines() {
        String[] strings = new String[lines.size()];
        return lines.toArray(strings);
    }

    public void setLines(String[] lines) {
        this.lines = new ArrayList<String>(Arrays.asList(lines));
    }

    public void setLines(List<String> lines) {
        this.lines = new ArrayList<String>(lines);
    }

    public String TestText() {

        StringBuilder b = new StringBuilder();
        for (String s : lines) {
            b.append(s);
            b.append(System.lineSeparator());
        }
        b.insert(selectionStart, "|");
        return b.toString();

    }

    public int getSelectionStart() {
        return selectionStart;
    }

    public void setSelectionStart(int selectionStart) {
        this.selectionStart = selectionStart;
    }


    public void changeToH2() {
        List<String> linesList = lines;
        String oldLine = linesList.get(LineContainingCursor());
        var r = Pattern.compile("<(?<prefix>.*)>(?<body>.*)</(?<suffix>.*)>");
        var m = r.matcher(oldLine);
        boolean b = m.find();
        String newLine = "<H2>" + m.group("body") + "</H2>";
        linesList.set(LineContainingCursor(), newLine);
        lines = linesList;
    }

    public void altS() {
        InsertSectionTags();
    }

    public void InsertSectionTags() {
        if ( lines.size() == 0 ) {
            lines.add( "<sect1><title></title>" );
            lines.add( "</sect1>");
            selectionStart = 14;
            return;
        }
        lines.addAll(LineContainingCursor()+1, NewSection());
        selectionStart = NewSectionSelectionStart(LineContainingCursor() + 1);
    }

    public List<String> NewSection() {
        List<String> temp = new ArrayList<String>();
        temp.add("<sect1><title></title>");
        temp.add("</sect1>");
        return temp;
    }

    private int NewSectionSelectionStart(int cursorLine) {
        return sumLineLengths(cursorLine) + "<sect1><title>".length();
    }
}

