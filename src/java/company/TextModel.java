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
    private int NewSelectionStart(int cursorLine, String tags) {
        return sumLineLengths(cursorLine) + tags.length(); }

    public void InsertSectionTags() {
        if ( lines.size() == 0 ) {
            lines.add( "<sect1><title></title>" );
            lines.add( "</sect1>");
            selectionStart = 14;
            return;
        }
        lines.addAll(LineContainingCursor()+1, NewSection());
        selectionStart = NewSelectionStart(LineContainingCursor() + 1, "<sect1><title>");
    }

    public void InsertParagraphTag() {
        if (lines.size() == 0) {
            lines.add("<P></P>");
            selectionStart = 3;
            return;
        }

        lines.addAll(LineContainingCursor() + 1, NewParagraph());
        selectionStart = NewSelectionStart(LineContainingCursor() + 1, "<p>");
    }

    private int sumLineLengths(int cursorLine) {
        int length = 0;
        for (int i = 0; i < cursorLine; i++)
            length += lines.get(i).length() + System.lineSeparator().length();
        return length;
    }

    public List<String> NewParagraph() {
        List<String> temp = new ArrayList();
        temp.add("<P></P>");
        return temp;
    }

    public List<String> getLines() {
        return lines;
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



    public List<String> NewSection() {
        List<String> temp = new ArrayList<String>();
        temp.add("<sect1><title></title>");
        temp.add("</sect1>");
        return temp;
    }
}

