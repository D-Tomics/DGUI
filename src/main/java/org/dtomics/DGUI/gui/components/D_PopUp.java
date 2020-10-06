package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Window;

/**
 * This component represents a dialog box that informs user of something.
 * This component appears on top of all the other component in a window.
 *
 * @author Abdul Kareem
 */
public class D_PopUp extends D_Container {

    private final Window window;
    private boolean shown;

    public D_PopUp(Window window, D_Container contents, float width, float height) {
        if (contents != null) {
            contents.style.setSize(width, height);
            contents.style.setMargin(0);
            style.setSize(width, height);
            style.setAlpha(0);
            this.add(contents);
        }
        if (window == null) throw new IllegalArgumentException("null window");
        this.setVisible(false);
        this.window = window;
    }

    public void show() {
        if (shown) return;
        shown = true;
        window.add(this);
        this.setLevel(window.getTopLayer());
        this.requestFocus(true);
        this.setVisible(true);
    }

    public void hide() {
        if (!shown) return;
        shown = false;
        window.addTask(() -> window.remove(this));
        this.requestLooseFocus(true);
        this.setVisible(false);
    }

    @Override
    protected void onUpdate() {

    }
}
