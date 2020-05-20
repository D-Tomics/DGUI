package engine.ui.gui.manager.events;

import engine.ui.gui.components.D_Gui;

/**This event is fired on the focused gui when pressed Mouse buttons are released
 *
 * @author Abdul Kareem
 */
public class D_GuiMouseReleaseEvent extends D_GuiEvent{

    private int button;
    private int mods;

    /**
     * Will be called when a key is released.
     *
     * @param gui      the gui that has received the event
     * @param button   the Mouse Button that was released
     * @param mods     bitfield describing which modifiers keys were held down
     */
    public D_GuiMouseReleaseEvent(D_Gui gui, int button, int mods) {
        super(gui);
        this.button = button;
        this.mods = mods;
    }

    public int getButton() {
        return button;
    }
    public int getMods() { return mods; }

}
