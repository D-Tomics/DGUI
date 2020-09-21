package org.dtomics.DGUI.gui.components;

import org.dtomics.DGUI.IO.Keyboard;
import org.dtomics.DGUI.IO.Mouse;
import org.dtomics.DGUI.gui.manager.events.D_GuiValueChangeEvent;
import org.dtomics.DGUI.gui.text.D_TextBox;
import org.dtomics.DGUI.gui.manager.constraints.guiTextConstraints.*;
import org.dtomics.DGUI.utils.colors.Color;
import org.dtomics.DGUI.utils.observers.Observable;

import static org.lwjgl.glfw.GLFW.*;

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

    private D_TextBox valueText;
    private D_GuiQuad bar;
    private float increment = 0.1f;
    private float xRelativeToValue = 0;

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
        this.valueText.setBoxSize(SLIDER_WIDTH,SLIDER_HEIGHT);
        this.valueText.setCentered(false);

        this.addConstraints(new D_TextAlignTop(valueText,0));
        this.addConstraints(new D_TextAlignRight(valueText,5));
    }

    public D_Slider(float minValue, float maxValue, float value) {
        this(minValue,maxValue);
        setValue(value);
    }

    @Override
    public void onUpdate() {

        if (this.isPressed()) {
            float prevVal = this.value;
            float left = style.getX();

            float mouseX = Mouse.getX();
            if(mouseX <= style.getX())
                mouseX = style.getX();
            else if(mouseX >= style.getX() + style.getWidth())
                mouseX = style.getX() + style.getWidth();

            xRelativeToValue = (mouseX - left); // range (0 - width)
            float val = (xRelativeToValue/style.getWidth()) * (maxValue - minValue)   + minValue;
            this.setValue((float) (Math.floor(val / increment) * increment));
            this.valueText.setText(value+"");
            style.notifyObservers();
            this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
        }

        if(this.isFocused()) {
            float incrementValue = increment;
            if(Keyboard.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
                incrementValue = 0.01f;
            else if(Keyboard.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
                incrementValue = 1f;

            if(Keyboard.isKeyRepeating(GLFW_KEY_LEFT)) {
                setValue(this.value - incrementValue);
            } else if(Keyboard.isKeyRepeating(GLFW_KEY_RIGHT)) {
                setValue(this.value + incrementValue);
            }
        }
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

    private void setValue(float value) {
        if(value <= minValue)
            value = minValue;
        else if(value > maxValue) value = maxValue;
        float prevVal = this.value;
        this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
        this.value = value;
        this.xRelativeToValue = ((value - minValue)/(maxValue - minValue)) * style.getWidth();
        this.valueText.setText(this.value+"");
        this.style.notifyObservers();
    }


}
