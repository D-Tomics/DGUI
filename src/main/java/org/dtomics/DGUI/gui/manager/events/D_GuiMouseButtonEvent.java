package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.lwjgl.glfw.GLFW;

/**This event is fired on the focused gui when pressed Mouse buttons are pressed, released, or repeated
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseButtonEvent extends D_GuiEvent {

    private int button;
    private int action;
    private int mods;

    /**
     * Will be called when a key is pressed, repeated or released.
     *
     * @param gui      the gui that has received the event
     * @param button   the Mouse Button that was pressed or released or repeated
     * @param action   the button action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
     * @param mods     bitfield describing which modifiers keys were held down
     */
    public D_GuiMouseButtonEvent(D_Gui gui, int button, int action, int mods) {
        super(gui);
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }

    public int getAction() {
        return action;
    }

    public int getMods() {
        return mods;
    }

}
