package org.dtomics.DGUI.gui.manager.events;

import org.dtomics.DGUI.gui.components.D_Gui;
import org.lwjgl.glfw.GLFW;

/**This event is fired on the focused gui when keyboard keys are pressed or released or repeated
 *
 * @author Abdul Kareem
 */
public class D_GuiKeyEvent extends D_GuiEvent {

    private int key;
    private int scanCode;
    private int mods;
    private int action;

    /**
     * Will be called when a key is pressed, repeated or released.
     *
     * @param gui      the gui that has received the event
     * @param key      the keyboard key that was pressed or released
     * @param scanCode the system-specific scancode of the key
     * @param mods      bitfield describing which modifiers keys were held down
     * @param action   the key action. One of:<br><table><tr><td>{@link GLFW#GLFW_PRESS PRESS}</td><td>{@link GLFW#GLFW_RELEASE RELEASE}</td><td>{@link GLFW#GLFW_REPEAT REPEAT}</td></tr></table>
     */
    public D_GuiKeyEvent(D_Gui gui, int key, int scanCode, int mods, int action) {
        super(gui);
        this.key = key;
        this.scanCode = scanCode;
        this.mods = mods;
        this.action = action;
    }

    public boolean isAction(int...actions) { for(int action : actions) if(action == this.action) return true; return false; }
    public boolean isKey(int...keys) { for(int key : keys) if(this.key == key) return true; return false; }

    public int getKey() {
        return key;
    }

    public int getScanCode() {
        return scanCode;
    }

    public int getMods() {
        return mods;
    }

    public int getAction() {
        return action;
    }
}
