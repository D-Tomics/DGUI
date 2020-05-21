package engine.ui.gui.text.meshCreator;

import engine.ui.gui.text.font.FontChar;

import java.util.ArrayList;
import java.util.List;

/**This class holds the characters that are present in a single line.
 *
 * @author Abdul Kareem
 */
public class Line {

    private float fontSize;
    private float curWidth;
    private float maxWidth;

    private StringBuilder text;
    private List<Float> widths;
    private List<FontChar> charList;

    Line(float maxWidth, float fontSize) {
        this.maxWidth = maxWidth;
        this.fontSize = fontSize;
        this.charList = new ArrayList<>();
        this.widths = new ArrayList<>();
        this.text = new StringBuilder();
    }

    boolean addCharToLine(FontChar fontChar, boolean wrapText, float hps) {
        if (fontChar.getId() == '\n') {
            text.append('\n');
            return false;
        }
        if(wrapText && (curWidth + fontChar.getXAdvance() * hps) * fontSize / 2.0f > maxWidth) return false;

        text.append((char)fontChar.getId());
        curWidth += fontChar.getXAdvance() * hps;
        charList.add(fontChar);
        widths.add(curWidth);
        return true;
    }

    public boolean contains(String s) {
        return text.toString().contains(s);
    }

    public int length() {
        return charList.size();
    }

    public float getWidth(int index) {
        return widths.size() != 0 && index >= 0 && index < widths.size() ? widths.get(index) * fontSize / 2.0f : 0;
    }

    public float getMaxWidth() {
        return curWidth;
    }

    public List<FontChar> getCharacters() {
        return charList;
    }

    public String toString() {
        return text.toString();
    }

}
