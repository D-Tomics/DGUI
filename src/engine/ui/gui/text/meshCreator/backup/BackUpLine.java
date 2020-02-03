package engine.ui.gui.text.meshCreator.backup;

import java.util.ArrayList;
import java.util.List;

public class BackUpLine {

    private float length;
    private float currentWidth;
    private float fontSize;
    private float spaceWidth;
    private List<BackUpWord> words = new ArrayList<>();

    protected BackUpLine(float length, float fontSize, float spaceWidth) {
        this.length = length;
        this.fontSize = fontSize;
        this.spaceWidth = spaceWidth * fontSize;
    }

    protected boolean attemptToAddWord(BackUpWord word) {
        if(word.isEmpty()) return true;
        if(currentWidth + word.getWidth() <= length ) {
            words.add(word);
            currentWidth += word.getWidth() + spaceWidth;
            return true;
        }
        return false;
    }

    protected List<BackUpWord> getWords() {
        return words;
    }

    protected float getMaxLength() {
        return length;
    }

    protected float getLineLength() {
        return currentWidth / fontSize;
    }

}

/*
        FontChar[] chars = convert(text);
        List<Line> lines = createStructure(text, chars);
        List<Float> data = new ArrayList<>();
        float cursorX = 0f;
        float cursorY = 0;

        float width = text.getBoxWidth();
        float maxWidth = 0;
        float maxHeight = LINE_HEIGHT * lines.size();

        if(text.isCentered())
            cursorY = maxHeight/2.0f;

        for (Line line : lines) {
            if(line.getLineLength() > width)
                width = line.getLineLength();
            if(line.getLineLength() > maxWidth)
                maxWidth = line.getLineLength();

            if (text.isCentered())
                cursorX = -line.getLineLength() / 2.0f;

            for (Word word : line.getWords()) {
                for (FontChar c : word.getCharacters()) {
                    if (c == null) {
                        cursorX += text.getFont().getFontFile().getSpaceWidth();
                        continue;
                    }
                    float x = cursorX + c.getXOffset();
                    float y = cursorY - c.getYOffset();
                    float xMax = x + c.getSizeX() ;
                    float yMax = y - c.getSizeY() ;

                    addVertexData(data, x, y, xMax, yMax, c.getXTexCoord(), c.getYTexCoord(), c.getxMaxTextureCoord(), c.getyMaxTextureCoord());
                    cursorX += c.getXAdvance() ;
                }
            }
            cursorY -=LINE_HEIGHT;
            cursorX = 0;
        }

        maxWidth *= text.getFontSize() * Window.INSTANCE.getWidth() / 2.0f;
        maxHeight *= text.getFontSize() * Window.INSTANCE.getHeight() / 2.0f;
        return new TextMeshData(listToArray(data), data.size() / 4, lines.size(), maxWidth ,maxHeight);*/


    /*private static List<Line> lines = new ArrayList<>();
    private static List<Line> createStructure(D_TextBox text, char[] characters) {

        lines.clear();
        float space = text.getFont().getFontFile().getSpaceWidth();
        Line currentLine = new Line(text.getBoxWidth(), text.getFontSize(), space);
        Word currentWord = new Word(text.getFontSize());
        lines.add(currentLine);

        for(char character : characters) {
            FontChar c = text.getFont().getFontFile().getFontChar(character);
            if(c == null) {
                currentLine = addWordToLine(lines, currentLine, currentWord, text.getBoxWidth(), text.getBoxHeight(), space, false);
                currentWord = new Word(text.getFontSize());
            } else if(c.getId() == '\n') {
                currentLine = addWordToLine(lines, currentLine, currentWord, text.getBoxWidth(), text.getBoxHeight(), space, true);
                currentWord = new Word(text.getFontSize());
                continue;
            }
            currentWord.addCharacter(c);

            if(currentWord.getWidth() >= currentLine.getMaxLength()) {
                FontChar removedChar = currentWord.removeCharacter(currentWord.getLength() - 1);
                currentLine.attemptToAddWord(currentWord);
                currentLine = new Line(text.getBoxWidth(), text.getFontSize(), space);
                currentWord = new Word(text.getFontSize());
                currentWord.addCharacter(removedChar);
                lines.add(currentLine);
            }
        }
        currentLine.attemptToAddWord(currentWord);
        return lines;
    }

    private static Line addWordToLine(List<Line> lines, Line currentLine, Word currentWord, float width, float height, float spaceWidth, boolean newLine) {
        boolean added = currentLine.attemptToAddWord(currentWord);
        if((!added || newLine)) {
            currentLine = new Line(width,currentWord.getFontSize(),spaceWidth);
            if(!added) currentLine.attemptToAddWord(currentWord);
            lines.add(currentLine);
        }
        return currentLine;
    }

    private static FontChar[] convert(D_TextBox text) {
        char[] chars = text.getText().toCharArray();
        Font font = text.getFont();
        FontChar[] charArray = new FontChar[chars.length];
        for(int i = 0; i < charArray.length; i++)
            charArray[i] = font.getFontFile().getFontChar(chars[i]);
        return charArray;
    }*/
