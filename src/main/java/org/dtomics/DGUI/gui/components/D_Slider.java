package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignCenter;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.D_TextAlignRight;
import org.dtomics.DGUI.gui.manager.events.D_GuiKeyEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiMouseDragEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiResizeEvent;
import org.dtomics.DGUI.gui.manager.events.D_GuiValueChangeEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.text.meshCreator.TextAlignment;
import org.dtomics.DGUI.utils.D_Event;
import org.dtomics.DGUI.utils.Maths;
import org.dtomics.DGUI.utils.colors.Color;
import org.dtomics.DGUI.utils.observers.Observable;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

/**This class lets the user graphically select a value by sliding
 * a bar within a bounded interval.
 *
 * @author Abdul Kareem
 */
public class D_Slider extends D_Component{

    private static final float SLIDER_WIDTH = 125;
    private static final float SLIDER_HEIGHT = 30.0F;

    private float value = 0;
    private float minValue;
    private float maxValue;
    private float increment = 0.1f;
    private float xRelativeToValue = 0;

    private final D_TextBox valueText;
    private final D_GuiQuad bar;

    public D_Slider() {
        this(0,1);
    }

    public D_Slider(float value) {
        this(0,1,value);
    }

    public D_Slider(float minValue, float maxValue) {
        this.bar = new D_GuiQuad();
        this.bar.setHoverable(false);
        this.addQuad(bar);
        this.minValue = minValue;
        this.maxValue = maxValue;

        style.setBounds(0,0,SLIDER_WIDTH,SLIDER_HEIGHT,false);

        bar.style.setBounds(style.getX(),style.getY(),0,SLIDER_HEIGHT);
        bar.style.setBgColor(0x667788);
        bar.setText(minValue+"", Color.BLACK);
        this.valueText = bar.getTextBox();
        this.valueText.setFontSize(70);
        this.valueText.setTextAlignment(TextAlignment.RIGHT);
        this.valueText.setBoxSize(SLIDER_WIDTH,SLIDER_HEIGHT);

        this.addEventListener(D_GuiResizeEvent.class, this::onSizeChange);
        this.addEventListener(D_GuiKeyEvent.class, this::onKeyPress);
        this.addEventListener(D_GuiMouseDragEvent.class, this::onMousePress);

        this.addConstraint(new D_TextAlignCenter(this.valueText, new D_TextAlignRight(valueText, 5)));
    }

    public D_Slider(float minValue, float maxValue, float value) {
        this(minValue,maxValue);
        setValue(value);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onStateChange(Observable o) {
        bar.style.setX(style.getX(),false);
        bar.style.setY(style.getY(),false);
        bar.style.setWidth(xRelativeToValue);
    }

    public float getValue() {
        return value;
    }
    public float getMinValue() {
        return minValue;
    }
    public float getMaxValue() { return maxValue; }

    public void setMinValue(float minValue) {
        if(this.value < minValue)
            this.value = minValue;
        this.minValue = minValue;
    }
    public void setMaxValue(float maxValue) {
        if(this.value > maxValue)
            this.value = maxValue;
        this.maxValue = maxValue;
    }

    public void setIncrement(float value) {
        this.increment = value;
    }

    public void setValue(float value) {
        if(value <= minValue)
            value = minValue;
        else if(value > maxValue) value = maxValue;
        float prevVal = this.value;
        this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
        this.value = value;
        updateBarWidth();
        this.valueText.setText(String.format("%s", this.value));
        this.style.notifyObservers();
    }

    private void onSizeChange(D_Event<D_Gui> event) {
        D_GuiResizeEvent e = (D_GuiResizeEvent) event;
        this.bar.style.setHeight(this.style.getHeight());

        float fontSize = valueText.getFontSize();
        float fontToHeightRatio = fontSize / e.getPreviousHeight();

        this.valueText.setBoxSize(this.style.getWidth(), this.style.getHeight());
        this.valueText.setFontSize(fontToHeightRatio * e.getCurrentHeight());
        updateBarWidth();

        this.style.notifyObservers();
    }

    private void onKeyPress(D_Event<D_Gui> event) {
        D_GuiKeyEvent e = (D_GuiKeyEvent) event;
        if(e.getAction() == GLFW_PRESS) {
            float incrementValue = increment;
            if(e.getKey() == GLFW_KEY_LEFT_SHIFT)
                incrementValue = 0.01f;
            else if(e.getKey() == GLFW_KEY_LEFT_CONTROL)
                incrementValue = 1f;

            switch (e.getKey()) {
                case GLFW_KEY_LEFT: setValue(value - incrementValue); break;
                case GLFW_KEY_RIGHT: setValue(value + incrementValue); break;
                case GLFW_KEY_UP: setValue(maxValue); break;
                case GLFW_KEY_DOWN: setValue(minValue); break;
            }
        }
    }

    private void onMousePress(D_Event<D_Gui> event) {
        D_GuiMouseDragEvent e = (D_GuiMouseDragEvent) event;
        if (e.getButton() != (GLFW_MOUSE_BUTTON_LEFT)) {
            return;
        }

        float prevVal = this.value;
        float left = style.getX();

        float mouseX = Mouse.getX();
        if(mouseX <= left)
            mouseX = left;
        else if(mouseX >= left + style.getWidth())
            mouseX = left + style.getWidth();

        xRelativeToValue = (mouseX - left); // range (0 - width)
        this.setValue(Maths.fastFloor(Maths.map(xRelativeToValue, 0, style.getWidth(), minValue, maxValue) / increment) * increment);
        style.notifyObservers();
        this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
    }

    private void updateBarWidth() {
        xRelativeToValue = ((value - minValue)/(maxValue - minValue)) * style.getWidth();
    }

}
