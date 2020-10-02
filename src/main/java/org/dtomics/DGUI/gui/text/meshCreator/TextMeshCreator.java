package org.dtomics.DGUI.gui.text.meshCreator;

import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.font.FontChar;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates the mesh for the text that is specified.
 *
 * @author Abdul Kareem
 */
public final class TextMeshCreator {

    public static final float LINE_HEIGHT = 1;

    private static List<Float> vertexData;
    public static TextMeshData createTextMesh(Window window, D_TextBox text) {
        if(vertexData == null)
            vertexData = new ArrayList<>();

        float vps = LINE_HEIGHT / text.getFont().getFontFile().getLineHeight();
        float hps = vps / window.getAspectRatio();

        //here vps is a constant for a font type. while hps is dependant on windows dimensions
        //if we just used font size of the text for widths and heights calculation, when window resize happens
        //the text becomes stretched or compressed. To avoid this we have to counter the change in window aspect ratio in hps.
        // So we need to change the font size relation to window size change(aspect ratio change).
        // To achieve this we need to multiply font size with aspect ratio of window , we get font size in relation to window,
        // but keeps the text not affected by stretching.
        // Since vps is a constant we don't need to change the font size for height calculations.
        // hence consider where we calculate width of the text
        //          (a + b + c + ...) * hps * fontSize / 2 ---(1)
        // here a,b,c is the char widths of each characters in the text
        //          hps = vps / wAr ----{2} , wAr is window aspect ratio
        //          (2) in (1) =>
        //                      (a + b + c + .....) * (vps / wAr) * fontSize / 2 ----(3)
        //from above equation its clear that when ever wAr changes width stretches or compresses
        // to counter that we take,
        //           fontSize = fontSize * wAr
        //            (3)=> (a + b + c + ....) * vps * fontSize / 2
        // so in effect the width or height of the text remains unchanged by window resize.
        // Only down side is this calculation needs to be done when ever the window resize occurs. Otherwise the text will start
        // stretching
        float relativeFontSize = text.getFontSize() * window.getAspectRatio();

        List<Line> lines = createStructure(text, hps, vps, relativeFontSize);

        TextAlignment align = text.getTextAlignment();

        float lineSpace = text.getLineSpacing() * vps;
        float maxWidth = 0;
        float maxHeight = (lines.size() - 1) * lineSpace;
        for(Line line : lines)
            maxHeight += line.getMaxHeight();

        float cursorX = 0;
        float cursorY = align.cursorY(2 * text.getBoxHeight() / text.getFontSize(), maxHeight,  text.getFont().getFontFile().getDesiredPadding() * vps);

        for(Line line : lines) {
            // here box Width is divided by fontSize because
            // it is in the shader that we multiply fontSize with vertices
            // that we created here
            //box size in normalized screen space form is 2 * boxSize / WindowWidth;
            cursorX = align.cursorX(2 * text.getBoxWidth() / relativeFontSize, line.getMaxWidth());

            if(line.getMaxWidth() > maxWidth)
                maxWidth = line.getMaxWidth();
            for(FontChar character : line.getCharacters()) {
                addCharacterDataToVertexData(vertexData, character, cursorX, cursorY, hps, vps);
                cursorX += character.getXAdvance() * hps;
            }
            cursorY -= line.getMaxHeight() + lineSpace;
        }

        try {
            return new TextMeshData(
                    listToArray(vertexData),
                    vertexData.size() / 4,
                    lines,
                    maxWidth * relativeFontSize / 2.0f, // maxWidth is in screen space of (0,1)=> needs conversion
                    // (fontSize / Window.getWidth()) * Window.getWidth() / 2
                    maxHeight * text.getFontSize() / 2.0f
                    // (fontSize / Window.getHeight()) * Window.getHeight() / 2
            );
        } finally {
            vertexData.clear();
        }
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

    private static List<Line> createStructure(D_TextBox textBox, float hps, float vps, float relativeFontSize) {
        List<Line> lines = textBox.getLines() == null ? new ArrayList<>() : textBox.getLines();
        lines.clear();
        char[] characterArray = textBox.getText().toCharArray();

        Line currentLine = new Line(textBox.getBoxWidth(), relativeFontSize);
        lines.add(currentLine);
        for(char character : characterArray) {
            FontChar fontCharacter = textBox.getFont().getFontFile().getFontChar(character);
            boolean added = currentLine.addCharToLine(fontCharacter, textBox.isWrapped(), hps,vps);
            if(!added) {
                currentLine = new Line(textBox.getBoxWidth(), relativeFontSize);
                currentLine.addCharToLine(fontCharacter,textBox.isWrapped(),hps,vps);
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
