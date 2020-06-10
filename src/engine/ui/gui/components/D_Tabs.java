package engine.ui.gui.components;

import engine.ui.gui.animation.D_GuiTransition;
import engine.ui.gui.manager.constraints.D_LayoutConstraint;
import engine.ui.gui.manager.events.D_GuiMousePressEvent;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;

import java.util.HashMap;

/**
 * This class lets the user switch between a group of components by
 * clicking on a tab with a given title.
 *
 * @author Abdul Kareem
 *
 */
public class D_Tabs extends D_Container{

    private static final float QUAD_HEIGHT = 30;

    private D_GuiQuad currentQuad;
    private D_Panel currentTab;
    private HashMap<String, D_Panel> tabs;
    private Color color = new Color(0.1f,0.1f,0.1f,0.8f);

    private D_GuiTransition fadeIn = new D_GuiTransition("fadeIn",0.6f,0.3f,0.8f, (gui, aFloat) -> gui.style.setAlpha(aFloat));

    public D_Tabs(float x, float y, float width, float height) {
        this();
        this.style.setBounds(x,y,width,height);
    }

    public D_Tabs() {
        this.style.setAlpha(0);
        this.style.setBorderWidth(1);
    }

    public void add(String tabName, D_Panel panel) {
        if(tabs == null) tabs = new HashMap<>();
        if(tabs.containsKey(tabName)) return;

        tabs.put(tabName, panel);
        panel.setVisible(false);
        panel.getStyle().setBorderWidth(0);
        add(panel);

        if(panel.style.getWidth() > this.style.getWidth())
            this.style.setWidth(panel.style.getWidth(), false);
        else
            panel.style.setWidth(this.style.getWidth(), false);

        if(panel.style.getHeight() > this.style.getHeight() - QUAD_HEIGHT)
            this.style.setHeight(panel.style.getHeight() + QUAD_HEIGHT, false);
        else
            panel.style.setHeight(this.style.getHeight() - QUAD_HEIGHT, false);


        D_GuiQuad quad = new D_GuiQuad(this.style.getWidth() / tabs.size(), QUAD_HEIGHT,tabName);
        quad.getTextBox().setTextColor(Color.WHITE);
        quad.getTextBox().getTextColor().setBrightness(0.5f);
        quad.style.setPosition(style.getX(), style.getY(), false);
        quad.style.setBgColor(color);
        quad.style.getBgColor().setBrightness(0.5f);

        quad.addEventListener(D_GuiMousePressEvent.class, e -> {
            String name = quad.getText();

            currentQuad.style.getBgColor().setBrightness(0.5f);
            currentQuad.getTextBox().getTextColor().setBrightness(0.5f);
            currentTab = tabs.get(name);
            currentQuad = quad;
            currentTab.setVisible(true);
            currentQuad.getTextBox().getTextColor().setBrightness(1f);
            currentQuad.style.getBgColor().setBrightness(1);

            style.notifyObservers();
        });
        addQuad(quad);

        if(currentTab == null) {
            currentQuad = quad;
            currentTab = panel;
            currentTab.setVisible(true);
            currentQuad.style.getBgColor().setBrightness(1);
            currentQuad.getTextBox().getTextColor().setBrightness(1f);
        }
        style.notifyObservers();
    }

    public D_Panel getCurrentTab() { return currentTab; }

    @Override
    public void add(D_Gui gui, D_LayoutConstraint constraint) {
        if(gui instanceof D_Panel){
            super.add(gui,null);
            return;
        }
        if(currentTab != null)
            currentTab.add(gui,constraint);
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onStateChange(Observable o) {
        if(getQuads() != null) {
            float x = style.getX();
            for(D_GuiQuad quad : getQuads()) {
                quad.style.setBounds(x,style.getY(),style.getWidth() / tabs.size(),QUAD_HEIGHT);
                x += style.getWidth() / tabs.size();
            }
        }

        if(getChildList() != null) {
            for(D_Gui child : getChildList()) {
                if(child == currentTab) {
                    child.style.setPosition(style.getX(), style.getY() - QUAD_HEIGHT, false);

                    if(child.style.getHeight() + QUAD_HEIGHT> this.style.getHeight())
                        this.style.setHeight(child.style.getHeight() + QUAD_HEIGHT, false);
                    else
                        child.style.setHeight(this.style.getHeight() - QUAD_HEIGHT, false);

                    if(child.style.getWidth() > this.style.getWidth())
                        this.style.setWidth(child.style.getWidth(), false);
                    else
                        child.style.setWidth(this.style.getWidth(), false);
                }
                else
                    child.setVisible(false);
                child.style.notifyObservers();
            }
        }
    }
}
