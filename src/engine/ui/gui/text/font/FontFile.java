package engine.ui.gui.text.font;

import java.io.*;
import java.util.HashMap;

/**
 * This file loads .fnt file that holds character data and stores these data in a list of <code>FontChar</code>
 *
 * @author Abdul Kareem
 */
public class FontFile {

    private static final int PAD_TOP = 0, PAD_RIGHT = 1, PAD_BOTTOM = 2, PAD_LEFT = 3;

    private float desiredPadding;
    private File fontFile;
    private BufferedReader reader;
    private HashMap<Integer, FontChar> characterMap;

    private int[] padding;

    private int padWidth;
    private int padHeight;
    private float imageWidth;
    private float spaceWidth;
    private float lineHeight;

    public FontFile(String fontFile, float desiredPadding) {
        this.fontFile = new File(fontFile);
        this.desiredPadding = desiredPadding;
        openFile(fontFile);
        processFile();
        closeFile();
    }

    public FontChar getFontChar(int id) {
        return characterMap.get(id);
    }


    public float getLineHeight() {
        return lineHeight;
    }

    public float getSpaceWidth() {
        return spaceWidth;
    }

    private void processFile() {
        try {
            loadPadding();
            loadSpaces();
            loadCharacters(imageWidth);
        } catch (IOException e) {
            System.err.println("Error processing file "+this.fontFile.getName());
            e.printStackTrace();
        }
    }

    private void loadPadding() throws IOException {
        processLine();
        String[] padStrings = getValue("padding").split(",");
        for(int i = 0; i < 4; i++)
            padding[i] = Integer.parseInt(padStrings[i]);
        padWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
        padHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
    }

    private void loadSpaces() throws IOException {
        processLine();
        lineHeight = getInt("lineHeight");
        imageWidth = getInt("scaleW");
    }

    private void loadCharacters(float scaleX) throws IOException {
        String line = "";
        while((line = processLine()) != null) {
            if(line.startsWith("char ")) {
                FontChar fontChar = loadCharacter(scaleX);
                characterMap.put(fontChar.getId(), fontChar);
            }
        }
    }


    private FontChar loadCharacter(float scaleX) {
        //32 = space ascii
        if (getInt("id") == 32)  spaceWidth = getInt("xadvance") - padWidth;

        float xTex = (getInt("x") + padding[PAD_LEFT] - desiredPadding) / scaleX;
        float yTex = (getInt("y") + padding[PAD_TOP] - desiredPadding) / scaleX;

        return new FontChar(
                getInt("id"),
                xTex,
                yTex,
                xTex + (getInt("width") - (padWidth - 2 * desiredPadding)) / scaleX,
                yTex + (getInt("height") - (padHeight - 2 * desiredPadding)) / scaleX,
                getInt("xoffset") + padding[PAD_LEFT] - desiredPadding,
                getInt("yoffset") + padding[PAD_TOP] - desiredPadding,
                getInt("xadvance") - padWidth,
                getInt("width") - (padWidth - 2 * desiredPadding),
                getInt("height") - (padHeight - 2 * desiredPadding)
        );
    }

    private void openFile(String path) {
        try {
            variables = new HashMap<>();
            reader = new BufferedReader(
                    fontFile.exists() ?
                            new FileReader(fontFile) :
                            new InputStreamReader(this.getClass().getResourceAsStream(path))
            );
            characterMap = new HashMap<>();
            padding = new int[4];
        } catch (FileNotFoundException e) {
            System.err.println("couldn't read "+fontFile.getName());
            e.printStackTrace();
        }
    }

    private void closeFile() {
        try {
            padding = null;
            variables = null;
            reader.close();
        } catch (IOException e) {
            System.err.print("couldn't close "+fontFile.getName()+" properly");
            e.printStackTrace();
        }
    }

    private HashMap<String, String> variables;
    private String processLine() throws IOException {
        variables.clear();
        String line = reader.readLine();
        if(line == null) return null;

        String[] types = line.split( " ");
        for(String type : types) {
            String[] values = type.split("=");
            if(values.length == 2)
                variables.put(values[0],values[1]);
        }
        return line;
    }

    private String getValue(String name) {
        return variables.get(name);
    }

    private int getInt(String name) {
        return Integer.parseInt(getValue(name));
    }

}
