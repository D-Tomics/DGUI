package engine.ui.gui.text;

import engine.ui.IO.Window;

public class D_Text extends D_TextBox{

    public D_Text(float x, float y, float fontSize, String text) {
        super(text,fontSize, Window.INSTANCE.getWidth(),Window.INSTANCE.getHeight());
        super.setPosition(x,y);
    }

    public D_Text(float fontSize, String text) {
        super(text,fontSize,Window.INSTANCE.getWidth(),Window.INSTANCE.getHeight());
    }

}
