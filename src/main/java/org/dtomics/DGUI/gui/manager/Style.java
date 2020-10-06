package org.dtomics.DGUI.gui.manager;

import lombok.ToString;
import org.dtomics.DGUI.gui.components.D_Gui;
import org.dtomics.DGUI.gui.manager.events.D_GuiResizeEvent;
import org.dtomics.DGUI.gui.renderer.Texture;
import org.dtomics.DGUI.utils.colors.Color;
import org.dtomics.DGUI.utils.observers.Observable;
import org.dtomics.DGUI.utils.observers.Observer;
import org.joml.Vector2f;

/**
 * This class represents the visual style position and size of a gui.
 * The user can change the above mentioned params of a gui via this class.
 * Every gui created on an application has a Style object with it.
 *
 * @author Abdul Kareem
 */

@ToString
public class Style extends Observable {

    private static final int TOP = 0, BOTTOM = 1, LEFT = 2, RIGHT = 3;
    private final Vector2f position;
    private final Vector2f center;
    private final Vector2f size;
    private Texture bgTexture;
    private final Color bgColor;
    private final Color borderColor;
    private float cornerRadius;
    private float borderWidth;
    private final float[] padding = new float[4];
    private final float[] margin = new float[]{10, 10, 10, 10};

    public Style() {
        this.bgColor = new Color();
        this.borderColor = new Color();
        this.position = new Vector2f(0);
        this.size = new Vector2f(0);
        this.center = new Vector2f(0);
    }

    public float getX() {
        return position.x;
    }

    public Style setX(float x) {
        return setX(x, true);
    }

    public float getY() {
        return position.y;
    }

    public Style setY(float y) {
        return setY(y, true);
    }

    public float getCenterX() {
        return center.x;
    }

    public Style setCenterX(float x) {
        return setCenterX(x, true);
    }

    public float getCenterY() {
        return center.y;
    }

    public Style setCenterY(float y) {
        return setCenterY(y, true);
    }

    public float getWidth() {
        return size.x;
    }

    public Style setWidth(float width) {
        return setWidth(width, true);
    }

    public float getHeight() {
        return size.y;
    }

    public Style setHeight(float height) {
        return setHeight(height, true);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public Style setCornerRadius(float radius) {
        if (radius <= Math.min(size.x, size.y) / 2.0f)
            this.cornerRadius = radius;
        return this;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public Style setBorderWidth(float size) {
        this.borderWidth = size;
        return this;
    }

    public float getMarginTop() {
        return margin[TOP];
    }

    public Style setMarginTop(float margin) {
        return this.setMarginTop(margin, true);
    }

    public float getMarginBottom() {
        return margin[BOTTOM];
    }

    public Style setMarginBottom(float margin) {
        return this.setMarginBottom(margin, true);
    }

    public float getMarginLeft() {
        return margin[LEFT];
    }

    public Style setMarginLeft(float margin) {
        return this.setMarginLeft(margin, true);
    }

    public float getMarginRight() {
        return margin[RIGHT];
    }

    public Style setMarginRight(float margin) {
        return this.setMarginRight(margin, true);
    }

    public float getMarginWidth() {
        return margin[LEFT] + margin[RIGHT];
    }

    public float getMarginHeight() {
        return margin[TOP] + margin[BOTTOM];
    }

    public float getPaddingTop() {
        return padding[TOP];
    }

    public Style setPaddingTop(float padding) {
        return this.setPaddingTop(padding, true);
    }

    public float getPaddingBottom() {
        return padding[BOTTOM];
    }

    public Style setPaddingBottom(float padding) {
        return this.setPaddingBottom(padding, true);
    }

    public float getPaddingRight() {
        return padding[RIGHT];
    }

    public Style setPaddingRight(float padding) {
        return this.setPaddingRight(padding, true);
    }

    public float getPaddingLeft() {
        return padding[LEFT];
    }

    public Style setPaddingLeft(float padding) {
        return this.setPaddingLeft(padding, true);
    }

    public float getPaddingWidth() {
        return padding[LEFT] + padding[RIGHT];
    }

    public float getPaddingHeight() {
        return padding[TOP] + padding[BOTTOM];
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Style setPosition(Vector2f position) {
        return setPosition(position, true);
    }

    public Vector2f getCenter() {
        return this.center;
    }

    public Style setCenter(Vector2f position) {
        return setCenter(position, true);
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Style setSize(Vector2f size) {
        return setSize(size, true);
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Style setBgColor(int color) {
        this.bgColor.set(color);
        return this;
    }

    public Style setBgColor(Color color) {
        this.bgColor.set(color);
        return this;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public Style setBorderColor(int color) {
        this.borderColor.set(color);
        return this;
    }

    public Style setBorderColor(Color color) {
        this.borderColor.set(color);
        return this;
    }

    public Texture getBgTexture() {
        return bgTexture;
    }

    public Style setBgTexture(Texture bgTexture) {
        this.bgTexture = bgTexture;
        return this;
    }

    // setters
    public Style setBounds(float x, float y, float width, float height) {
        return setBounds(x, y, width, height, true);
    }

    public Style setBounds(float x, float y, float width, float height, boolean changed) {
        return setPosition(x, y, false).setSize(width, height, changed);
    }

    public Style setX(float x, boolean changed) {
        position.x = x;
        center.x = x + size.x / 2.0f;
        if (changed) notifyObservers();
        return this;
    }

    public Style setY(float y, boolean changed) {
        position.y = y;
        center.y = y - size.y / 2.0f;
        if (changed) notifyObservers();
        return this;
    }

    public Style setPosition(float x, float y) {
        return setPosition(x, y, true);
    }

    public Style setPosition(float x, float y, boolean changed) {
        return this.setX(x, false).setY(y, changed);
    }

    public Style setPosition(Vector2f position, boolean changed) {
        return setPosition(position.x, position.y, changed);
    }

    public Style setCenterX(float x, boolean changed) {
        center.x = x;
        position.x = center.x - size.x / 2.0f;
        if (changed) notifyObservers();
        return this;
    }

    public Style setCenterY(float y, boolean changed) {
        center.y = y;
        position.y = center.y + size.y / 2.0f;
        if (changed) notifyObservers();
        return this;
    }

    public Style setCenter(float x, float y) {
        return setCenter(x, y, true);
    }

    public Style setCenter(float x, float y, boolean changed) {
        return setCenterX(x, false).setCenterY(y, changed);
    }

    public Style setCenter(Vector2f position, boolean changed) {
        return setCenter(position.x, position.y, changed);
    }

    public Style setWidth(float width, boolean changed) {
        return setSize(width, size.y, changed);
    }

    public Style setHeight(float height, boolean changed) {
        return setSize(size.x, height, changed);
    }

    public Style setSize(float w, float h) {
        return setSize(w, h, true);
    }

    public Style setSize(Vector2f size, boolean changed) {
        return setSize(size.x, size.y, changed);
    }

    public Style setSize(float w, float h, boolean changed) {
        if (size.x != w || size.y != h) {
            for (Observer observer : observers)
                ((D_Gui) observer).stackEvent(new D_GuiResizeEvent((D_Gui) observer, size.x, size.y, w, h));
        }

        this.size.set(w, h);
        this.center.set(position.x + w / 2.0f, position.y - h / 2.0f);
        if (changed) notifyObservers();
        return this;
    }

    public Style setAlpha(float alpha) {
        this.bgColor.a(Math.max(0, Math.min(1, alpha)));
        return this;
    }

    public Style setBorderColor(int r, int g, int b) {
        this.borderColor.set(r, g, b);
        return this;
    }

    public Style setBorderColor(float r, float g, float b) {
        this.borderColor.set(r, g, b);
        return this;
    }

    public Style setBgColor(int r, int g, int b) {
        this.bgColor.set(r, g, b);
        return this;
    }

    public Style setBgColor(float r, float g, float b) {
        this.bgColor.set(r, g, b);
        return this;
    }

    public Style setMargin(float margin) {
        return this.setMargin(margin, true);
    }

    public Style setMargin(float top, float bottom, float left, float right) {
        return this.setMargin(top, bottom, left, right, true);
    }

    public Style setMarginTop(float margin, boolean changed) {
        this.margin[TOP] = margin;
        if (changed) notifyObservers();
        return this;
    }

    public Style setMarginBottom(float margin, boolean changed) {
        this.margin[BOTTOM] = margin;
        if (changed) notifyObservers();
        return this;
    }

    public Style setMarginLeft(float margin, boolean changed) {
        this.margin[LEFT] = margin;
        if (changed) notifyObservers();
        return this;
    }

    public Style setMarginRight(float margin, boolean changed) {
        this.margin[RIGHT] = margin;
        if (changed) notifyObservers();
        return this;
    }

    public Style setMargin(float margin, boolean changed) {
        return setMargin(margin, margin, margin, margin, changed);
    }

    public Style setMargin(float top, float bottom, float left, float right, boolean changed) {
        margin[TOP] = top;
        margin[BOTTOM] = bottom;
        margin[LEFT] = left;
        margin[RIGHT] = right;
        if (changed)
            notifyObservers();
        return this;
    }

    public Style setPadding(float padding) {
        return this.setPadding(padding, true);
    }

    public Style setPadding(float top, float bottom, float left, float right) {
        return setPadding(top, bottom, left, right, true);
    }

    public Style setPaddingTop(float padding, boolean changed) {
        this.padding[TOP] = padding;
        if (changed) notifyObservers();
        return this;
    }

    public Style setPaddingBottom(float padding, boolean changed) {
        this.padding[BOTTOM] = padding;
        if (changed) notifyObservers();
        return this;
    }

    public Style setPaddingLeft(float padding, boolean changed) {
        this.padding[LEFT] = padding;
        if (changed) notifyObservers();
        return this;
    }

    public Style setPaddingRight(float padding, boolean changed) {
        this.padding[RIGHT] = padding;
        if (changed) notifyObservers();
        return this;
    }

    public Style setPadding(float padding, boolean changed) {
        return this.setPadding(padding, padding, padding, padding, changed);
    }

    public Style setPadding(float top, float bottom, float left, float right, boolean changed) {
        padding[TOP] = top;
        padding[BOTTOM] = bottom;
        padding[RIGHT] = right;
        padding[LEFT] = left;
        if (changed)
            notifyObservers();
        return this;
    }

    public Style set(Style style) {
        this.position.set(style.getPosition());
        this.size.set(style.getSize());
        this.center.set(style.getCenter());
        this.bgColor.set(style.getBgColor());
        this.borderColor.set(style.getBorderColor());

        this.borderWidth = style.getBorderWidth();
        this.setMargin(style.getMarginTop(), style.getMarginBottom(), style.getMarginLeft(), style.getMarginRight());
        this.setPadding(style.getPaddingTop(), style.getPaddingBottom(), style.getPaddingLeft(), style.getPaddingRight());
        return this;
    }

}
