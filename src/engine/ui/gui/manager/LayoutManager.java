package engine.ui.gui.manager;

import engine.ui.gui.manager.layouts.Layout;

public abstract class LayoutManager implements Layout {

    private static final int PADDING_TOP = 0,PADDING_BOTTOM = 1,PADDING_LEFT = 2, PADDING_RIGHT = 3;
    private static final int MARGIN_TOP = 0,MARGIN_LEFT = 1;
    private float[] padding = new float[]{10,10,10,10};
    private float[] margin = new float[] {0,0};
    private float[] defaultMargin = new float[] {20,0};

    public float getPaddingTop() {
        return padding[PADDING_TOP];
    }

    public float getPaddingBottom() {
        return padding[PADDING_BOTTOM];
    }

    public float getPaddingLeft() {
        return padding[PADDING_LEFT];
    }

    public float getPaddingRight() {
        return padding[PADDING_RIGHT];
    }

    public float getMarginTop() {
        return margin[MARGIN_TOP] + defaultMargin[MARGIN_TOP];
    }

    public float getMarginLeft() {
        return margin[MARGIN_LEFT] + defaultMargin[MARGIN_LEFT];
    }

    public void setPadding(int pad) {
        padding[PADDING_TOP] = pad;
        padding[PADDING_BOTTOM] = pad;
        padding[PADDING_LEFT] = pad;
        padding[PADDING_RIGHT] = pad;
    }

    public void setPadding(int top, int bottom, int left, int right) {
        padding[PADDING_TOP] = top;
        padding[PADDING_BOTTOM] = bottom;
        padding[PADDING_LEFT] = left;
        padding[PADDING_RIGHT] = right;
    }

    public void setPaddingTop(int padd) {
        padding[PADDING_TOP] = padd;
    }

    public void setPaddingBottom(int padd) {
        padding[PADDING_BOTTOM] = padd;
    }

    public void setPaddingLeft(int padd) {
        padding[PADDING_LEFT] = padd;
    }

    public void setPaddingRight(int padd) {
        padding[PADDING_RIGHT] = padd;
    }

    public void setMarginTop(float marg) {
        margin[MARGIN_TOP] = marg;
    }

    public void setMarginLeft(float marg) {
        margin[MARGIN_LEFT] = marg;
    }

    public void setMargin(float marg) {
        margin[MARGIN_TOP] = margin[MARGIN_LEFT] = marg;
    }

    public void setMargin(float top, float left) {
        margin[MARGIN_TOP] = top;
        margin[MARGIN_LEFT] = left;
    }

}
