import engine.ui.IO.Window;
import engine.ui.gui.components.D_Panel;
import engine.ui.gui.components.D_TextArea;
import engine.ui.gui.manager.layouts.GridLayout;
import engine.ui.gui.renderer.D_GuiRenderer;
import engine.ui.gui.text.D_TextMaster;

import java.io.*;

import static org.lwjgl.opengl.GL11.*;

class TextTest {

    private TextTest() {}

    public static void main(String[] args) {
        Window window = new Window(640,480,"test",false);
        window.create();

        D_Panel panel = new D_Panel("tst", new GridLayout(1,1));
        D_TextArea textArea = new D_TextArea("",200,200);

        //FileEditor editor = new FileEditor(new File("src/engine/ui/gui/renderer/shader/dTextShader.glsl"),1280,720);

        panel.add(textArea);

        window.add(panel);

        while(!window.isExitRequested()) {
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            D_GuiRenderer.render();
            D_TextMaster.render();
            window.update();
        }
        window.destroy();
    }


}

class FileEditor extends D_TextArea{

    private File file;
    private BufferedWriter writer;
    private BufferedReader reader;

    public FileEditor(File file, float width, float height) {
        super("", width, height);
        this.file = file;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void init() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        openFile("r");
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        reader.close();
        this.setText(stringBuilder.toString());
    }

    private void openFile(String rw) throws IOException {
        if(rw.equals("r")) {
            if(reader == null ) reader = new BufferedReader(new FileReader(file));
        } else {
            if(writer == null) writer = new BufferedWriter(new FileWriter(file));
        }
    }

    private void flush() throws IOException {
        writer.flush();
    }
}