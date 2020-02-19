package company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TextModel {
    private static final List<String> newParagraph = List.of("<P></P>");
    public static final String sectionSkip = "<sect1><title>";
    public static final String paragraphSkip = "<P>";
    private List<String> lines;
    private int selectionStart;
    private static List<String> newSection = List.of("<sect1><title></title>", "</sect1>");

    private static List<String> newPre = List.of( "<pre></pre>");
    private static String preSkip = "<pre>";

    public TextModel() {
        lines = new ArrayList<String>();
    }

    public void Enter() {
        InsertParagraphTag();
    }

    public void altS() {
        InsertSectionTags();
    }

    public void InsertSectionTags() {
        insertTags(newSection, sectionSkip);
    }
    public void InsertParagraphTag() {
        insertTags(newParagraph, paragraphSkip);
    }
    private void insertTags(List<String> newSection, String tags) {
        int cursorLine = LineContainingCursor();
        lines.addAll(cursorLine + 1, newSection);
        selectionStart = NewSelectionStart(cursorLine + 1, tags);
    }

    private int NewSelectionStart(int cursorLine, String tags) {
        return firstPositionOfLine(cursorLine) + tags.length(); }


    private int LineContainingCursor() {
        if (lines.size() == 0)
            return -1;

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

    private int firstPositionOfLine(int cursorLine) {
        int length = 0;
        for (int i = 0; i < cursorLine; i++)
            length += lines.get(i).length() + System.lineSeparator().length();
        return length;
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

    public String TestText() {
        StringBuilder b = new StringBuilder();
        for (String s : lines) {
            b.append(s);
            b.append(System.lineSeparator());
        }
        b.insert(selectionStart, "|");
        return b.toString();

    }


    public void InsertPreTag() {
        insertTags(newPre, preSkip);
    }

    public void AltP() {
        InsertPreTag();
    }

    public void InsertReturn() {
        String front = FrontOfCursorLine();
        String back = BackOfCursorLine();
        lines.set(LineContainingCursor(),front);
        lines.add(LineContainingCursor()+1, back);
        selectionStart += System.lineSeparator().length();
    }


    public String FrontOfCursorLine() {
        String line = lines.get(LineContainingCursor());
        int position = positionOfCursorInLine();
        return line.substring(0, position);
    }

    public int positionOfCursorInLine() {
        return selectionStart - firstPositionOfLine(LineContainingCursor());
    }

    public String BackOfCursorLine() {
        String line = lines.get(LineContainingCursor());
        int position = positionOfCursorInLine();
        return line.substring( position );
    }

}

