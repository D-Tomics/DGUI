import org.dtomics.DGUI.IO.DGUI;
import org.dtomics.DGUI.IO.Window;
import org.dtomics.DGUI.gui.components.D_Label;
import org.dtomics.DGUI.gui.components.D_Panel;
import org.dtomics.DGUI.gui.components.D_Tabs;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class TransitionTest {
    public static D_Tabs tabs;
    public static void main(String[] args) {
        DGUI.init();

        Window window = new Window(640,480, "test",false);
        window.create();


        D_Panel panel = new D_Panel();
        panel.add(new D_Label("TransitionTest0"));
        panel.add(new D_Label("TransitionTest1"));
        window.add(panel);

        window.show();
        GL11.glClearColor(0, 0, 0, 1);
        while (!window.isExitRequested()) {
            glClear(GL_COLOR_BUFFER_BIT);
            //window.getRenderer().getDTextRenderer().render(D_TextMaster.getTextMap(window));

            DGUI.update(true);
        }

        DGUI.terminate();
    }
}
