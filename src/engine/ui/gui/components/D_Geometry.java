package engine.ui.gui.components;

import engine.ui.gui.text.D_TextBox;
import engine.ui.utils.observers.Observable;

public class D_Geometry extends D_Component {

    private D_TextBox textBox;
    D_Geometry() {
    }

    public void setText(String text) {
        if(textBox == null)
            textBox = new D_TextBox(text,60,style.getWidth(),style.getHeight(), true);
        else
            textBox.setText(text);
    }

    public D_TextBox getText() {
        return textBox;
    }

    @Override
    protected void onUpdate() { }

    @Override
    public void onStateChange(Observable o) {
        if(textBox != null)
            textBox.setPosition(this.style.getX(),this.style.getY());
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(this.textBox != null)
            this.textBox.setVisible(visible);
    }

}
