package engine.ui.gui.text.meshCreator.backup;

import engine.ui.gui.text.font.FontChar;

import java.util.ArrayList;
import java.util.List;

public class BackUpWord {

    private float fontSize;
    private float width;
    private List<FontChar> characters = new ArrayList<>();

    protected BackUpWord(float fontSize) {
        this.fontSize = fontSize;
    }

    protected void addCharacter(FontChar character) {
        characters.add(character);
        this.width += character != null ? character.getXAdvance() * fontSize : 0;
    }

    protected FontChar removeCharacter(int index) {
        FontChar character = characters.get(index);
        characters.remove(index);
        this.width -= character != null ? character.getXAdvance() * fontSize  : 0;
        return character;
    }

    protected  float getWidth() {
        return width;
    }

    protected  float getFontSize() { return fontSize; }

    protected int getLength() { return characters.size(); }

    protected boolean isEmpty() {
        return characters.isEmpty();
    }

    protected List<FontChar> getCharacters() { return characters; }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for(FontChar c : characters)
            str.append(c.toString());
        return str.toString();
    }
}
