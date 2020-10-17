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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

/**
 * This class lets the user graphically select a value by sliding
 * a bar within a bounded interval.
 *
 * @author Abdul Kareem
 */
public class D_Slider extends D_Component {

    private static final float SLIDER_WIDTH = 125;
    private static final float SLIDER_HEIGHT = 30.0F;
    private final D_TextBox valueText;
    private final D_GuiQuad bar;
    private BigDecimal value;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private BigDecimal increment = BigDecimal.valueOf(0.1);
    private float xRelativeToValue = 0;

    public D_Slider() {
        this(0, 1);
    }

    public D_Slider(double value) {
        this(0, 1, value);
    }

    public D_Slider(double minValue, double maxValue) {
        this.bar = new D_GuiQuad();
        this.bar.setHoverable(false);
        this.addQuad(bar);
        this.minValue = BigDecimal.valueOf(minValue);
        this.maxValue = BigDecimal.valueOf(maxValue);
        this.value = BigDecimal.valueOf(0);

        style.setBounds(0, 0, SLIDER_WIDTH, SLIDER_HEIGHT, false);

        bar.style.setBounds(style.getX(), style.getY(), 0, SLIDER_HEIGHT);
        bar.style.setBgColor(0x667788);
        bar.setText(minValue + "", Color.BLACK);
        this.valueText = bar.getTextBox();
        this.valueText.setFontSize(70);
        this.valueText.setTextAlignment(TextAlignment.RIGHT);
        this.valueText.setBoxSize(SLIDER_WIDTH, SLIDER_HEIGHT);

        this.addEventListener(D_GuiResizeEvent.class, this::onSizeChange);
        this.addEventListener(D_GuiKeyEvent.class, this::onKeyPress);
        this.addEventListener(D_GuiMouseDragEvent.class, this::onMousePress);

        this.addConstraint(new D_TextAlignCenter(this.valueText, new D_TextAlignRight(valueText, 5)));
    }

    public D_Slider(double minValue, double maxValue, double value) {
        this(minValue, maxValue);
        setValue(value);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onStateChange(Observable o) {
        bar.style.setX(style.getX(), false);
        bar.style.setY(style.getY(), false);
        bar.style.setWidth(xRelativeToValue);
    }

    public double getValue() {
        return value.doubleValue();
    }

    public void setValue(double value) {

        if(value <= minValue.doubleValue()) {
            value = minValue.doubleValue();
        } else if(value >= maxValue.doubleValue()) {
            value = maxValue.doubleValue();
        }
        double prevVal = this.value.doubleValue();
        this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
        this.value = BigDecimal.valueOf(value);
        updateBarWidth();
        this.valueText.setText(String.format("%s", this.value.toString()));
        this.style.notifyObservers();
    }

    public void setValue(BigDecimal value) {
        setValue(value.doubleValue());
    }

    public double getMinValue() {
        return minValue.doubleValue();
    }

    public void setMinValue(double minValue) {
        this.minValue = BigDecimal.valueOf(minValue);
        if (this.value.doubleValue() < minValue) {
            setValue(minValue);
        }
    }

    public double getMaxValue() {
        return maxValue.doubleValue();
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = BigDecimal.valueOf(maxValue);
        if (this.value.doubleValue() > maxValue) {
            setValue(maxValue);
        }
    }

    public void setIncrement(double value) {
        this.increment = BigDecimal.valueOf(value);
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
        if (e.getAction() == GLFW_PRESS || e.getAction() == GLFW_REPEAT) {
            BigDecimal incrementValue = increment;
            if (e.getMods() == GLFW_MOD_SHIFT)
                incrementValue = increment.divide(BigDecimal.TEN, increment.scale() + 1, RoundingMode.FLOOR);
            else if (e.getMods() == GLFW_MOD_CONTROL)
                incrementValue = increment.multiply(BigDecimal.TEN);

            switch (e.getKey()) {
                case GLFW_KEY_LEFT:
                    setValue(value.subtract(incrementValue));
                    break;
                case GLFW_KEY_RIGHT:
                    setValue(value.add(incrementValue));
                    break;
                case GLFW_KEY_UP:
                    setValue(maxValue);
                    break;
                case GLFW_KEY_DOWN:
                    setValue(minValue);
                    break;
            }
        }
    }

    private void onMousePress(D_Event<D_Gui> event) {
        D_GuiMouseDragEvent e = (D_GuiMouseDragEvent) event;
        if (e.getButton() != (GLFW_MOUSE_BUTTON_LEFT)) {
            return;
        }

        float prevVal = this.value.floatValue();
        float left = style.getX();

        float mouseX = Mouse.getX();
        if (mouseX <= left)
            mouseX = left;
        else if (mouseX >= left + style.getWidth())
            mouseX = left + style.getWidth();

        xRelativeToValue = (mouseX - left); // range (0 - width)
        this.setValue(
                        Maths.map(
                                BigDecimal.valueOf(xRelativeToValue),
                                BigDecimal.ZERO,
                                BigDecimal.valueOf(style.getWidth()),
                                minValue,
                                maxValue
                        ).divide(increment,increment.scale(),RoundingMode.FLOOR)
                        .setScale(0, RoundingMode.FLOOR)
                        .multiply(increment)
        );
        style.notifyObservers();
        this.stackEvent(new D_GuiValueChangeEvent<>(this, prevVal, value));
    }

    private void updateBarWidth() {
        xRelativeToValue = value
                .subtract(minValue)
                .divide(maxValue.subtract(minValue), increment.scale(), RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(style.getWidth()))
                .floatValue();
    }

}
