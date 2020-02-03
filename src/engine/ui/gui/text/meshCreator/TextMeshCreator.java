package engine.ui.gui.text.meshCreator;

import engine.ui.IO.Window;
import engine.ui.gui.text.D_TextBox;
import engine.ui.gui.text.font.FontChar;

import java.util.ArrayList;
import java.util.List;

public final class TextMeshCreator {

    public static final float LINE_HEIGHT = 1;

    static TextMeshData createTextMesh(D_TextBox text) {
        var vertexData = new ArrayList<Float>();

        float vps = LINE_HEIGHT / text.getFont().getFontFile().getLineHeight();
        float hps = vps / Window.INSTANCE.getAspectRatio();


        float maxWidth = 0;


        var lines = createStructure(text,hps);


        float cursorX;
        float cursorY = 0;

        for(Line line : lines) {

            // here box Width is divided by fontSize because
            // it is in the shader that we multiply fontSize with vertices
            // that we created here
            //box size in normalized screen space form is 2 * boxSize / WindowWidth;
            cursorX =
                    text.isCentered() ?
                            text.getBoxWidth() / text.getFontSize() - line.getMaxWidth() * 0.5f
                            :
                            0;
            if(line.getMaxWidth() > maxWidth)
                maxWidth = line.getMaxWidth();
            for(FontChar character : line.getCharacters()) {
                addCharacterDataToVertexData(vertexData, character, cursorX, cursorY, hps, vps);
                cursorX += character.getXAdvance() * hps;
            }
            cursorY -= LINE_HEIGHT;
        }

        return new TextMeshData(
                listToArray(vertexData),
                vertexData.size() / 4,
                lines,
                maxWidth * text.getFontSize() / 2.0f, // maxWidth is in screen space of (0,1)=> needs conversion
                // (text.getFontSize() / Window.getWidth()) * Window.getWidth() / 2
                (lines.size() * LINE_HEIGHT) * text.getFontSize() / 2.0f
                // (text.getFontSize() / Window.getHeight()) * Window.getHeight() / 2
        );
    }

    private static void addCharacterDataToVertexData(List<Float> vertexData, FontChar character, float cursorX, float cursorY, float hps, float vps) {
        float x = cursorX + character.getXOffset() * hps;
        float y = cursorY - character.getYOffset() * vps;
        float xMax = x + character.getSizeX() * hps;
        float yMax = y - character.getSizeY() * vps;

        addCharDataToList(
                vertexData, x, y, xMax, yMax,
                character.getXTexCoord(), character.getYTexCoord(),
                character.getXMaxTexCoord(), character.getYMaxTexCoord()
        );
    }

    private static List<Line> createStructure(D_TextBox textBox, float hps) {
        List<Line> lines = textBox.getLines() == null ? new ArrayList<>() : textBox.getLines();
        lines.clear();
        char[] characterArray = textBox.getText().toCharArray();

        Line currentLine = new Line(textBox.getBoxWidth(), textBox.getFontSize());
        lines.add(currentLine);
        for(char character : characterArray) {
            FontChar fontCharacter = textBox.getFont().getFontFile().getFontChar(character);
            boolean added = currentLine.addCharToLine(fontCharacter, textBox.isWrapped(), hps);
            if(!added) {
                currentLine = new Line(textBox.getBoxWidth(), textBox.getFontSize());
                currentLine.addCharToLine(fontCharacter,textBox.isWrapped(),hps);
                lines.add(currentLine);
            }
        }

        return lines;
    }

    private static float[] listToArray(List<Float> list) {
        float[] f = new float[list.size()];
        for (int i = 0; i < f.length; i++)
            f[i] = list.get(i);
        return f;
    }

    private static void addCharDataToList(List<Float> data, float x, float y, float xMax, float yMax, float xTexCoord, float yTexCoord, float xMaxTexCoord, float yMaxTexCoord) {
        //vertex                                texCoords
        addVertexToList(data,    x,    y,       xTexCoord,     yTexCoord);
        addVertexToList(data,    x, yMax,       xTexCoord,  yMaxTexCoord);
        addVertexToList(data, xMax, yMax,    xMaxTexCoord,  yMaxTexCoord);
        addVertexToList(data, xMax, yMax,    xMaxTexCoord,  yMaxTexCoord);
        addVertexToList(data, xMax,    y,    xMaxTexCoord,     yTexCoord);
        addVertexToList(data,    x,    y,       xTexCoord,     yTexCoord);
    }

    private static void addVertexToList(List<Float> list, float x, float y , float xTex, float yTex) {
        list.add(x);
        list.add(y);
        list.add(xTex);
        list.add(yTex);
    }

}
