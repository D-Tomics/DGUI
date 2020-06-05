package engine.ui.gui.manager;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.events.D_GuiResizeEvent;
import engine.ui.gui.renderer.Texture;
import engine.ui.utils.colors.Color;
import engine.ui.utils.observers.Observable;
import engine.ui.utils.observers.Observer;
import org.joml.Vector2f;

/**
 * This class represents the visual style position and size of a gui.
 * The user can change the above mentioned params of a gui via this class.
 * Every gui created on an application has a Style object with it.
 *
 * @author  Abdul Kareem
 */
public class Style extends Observable {

    private Vector2f position;
    private Vector2f center;
    private Vector2f dimension;

    private Texture bgTexture;

    private Color bgColor;
    private Color borderColor;

    private float cornerRadius;
    private float borderWidth;

    private static final int TOP = 0,BOTTOM = 1,LEFT = 2, RIGHT = 3;
    private float[] padding = new float[4];
    private float[] margin = new float[] { 10,10,10,10};

    public Style() {
        this.bgColor = new Color();
        this.borderColor = new Color();
        this.position = new Vector2f(0);
        this.dimension = new Vector2f(0);
        this.center = new Vector2f(0);
    }

    public float getX()            { return position.x;   }
    public float getY()            { return position.y;   }
    public float getCenterX()      { return center.x;     }
    public float getCenterY()      { return center.y;     }
    public float getWidth()        { return dimension.x;  }
    public float getHeight()       { return dimension.y;  }
    public float getCornerRadius() { return cornerRadius; }
    public float getBorderWidth()  { return borderWidth;  }

    public float getMarginTop()     { return margin[TOP];                    }
    public float getMarginBottom()  { return margin[BOTTOM];                 }
    public float getMarginLeft()    { return margin[LEFT];                   }
    public float getMarginRight()   { return margin[RIGHT];                  }
    public float getMarginWidth()   { return margin[LEFT] + margin[RIGHT];   }
    public float getMarginHeight()  { return margin[TOP] + margin[BOTTOM];   }

    public float getPaddingTop()    { return padding[TOP];                   }
    public float getPaddingBottom() { return padding[BOTTOM];                }
    public float getPaddingRight()  { return padding[RIGHT];                 }
    public float getPaddingLeft()   { return padding[LEFT];                  }
    public float getPaddingWidth()  { return padding[LEFT] + padding[RIGHT]; }
    public float getPaddingHeight() { return padding[TOP] + padding[BOTTOM]; }


    public Vector2f getPosition() { return this.position;  }
    public Vector2f getCenter()   { return this.center;    }
    public Vector2f getSize()     { return this.dimension; }

    public Color getBgColor()     { return bgColor;     }
    public Color getBorderColor() { return borderColor; }

    public Texture getBgTexture() { return bgTexture; }

    // setters
    public Style setBounds(float x, float y, float width, float height)                  { return setBounds(x,y,width,height,true);                       }
    public Style setBounds(float x, float y, float width, float height, boolean changed) { return setPosition(x,y, false).setSize(width,height, changed); }

    public Style setX(float x, boolean changed) {
        position.x = x;
        center.x = x + dimension.x / 2.0f;
        if(changed) notifyObservers();
        return this;
    }

    public Style setY(float y, boolean changed) {
        position.y = y;
        center.y = y - dimension.y / 2.0f;
        if(changed) notifyObservers();
        return this;
    }

    public Style setX(float x) { return setX(x,true); }
    public Style setY(float y) { return setY(y,true); }

    public Style setPosition(float x, float y)                   { return setPosition(x,y,true);               }
    public Style setPosition(float x, float y, boolean changed)  { return this.setX(x,false).setY(y,changed);  }
    public Style setPosition(Vector2f position)                  { return setPosition(position,true);          }
    public Style setPosition(Vector2f position, boolean changed) { return setPosition(position.x, position.y, changed); }

    public Style setCenterX(float x, boolean changed) {
        center.x = x;
        position.x = center.x - dimension.x/2.0f;
        if(changed) notifyObservers();
        return this;
    }

    public Style setCenterY(float y, boolean changed) {
        center.y = y;
        position.y = center.y + dimension.y/2.0f;
        if(changed) notifyObservers();
        return this;
    }

    public Style setCenterX(float x) { return setCenterX(x, true); }
    public Style setCenterY(float y) { return setCenterY(y, true); }

    public Style setCenter(float x, float y)                   { return setCenter(x,y,true);                         }
    public Style setCenter(float x, float y, boolean changed)  { return setCenterX(x, false).setCenterY(y, changed); }
    public Style setCenter(Vector2f position)                  { return setCenter(position,true);                    }
    public Style setCenter(Vector2f position, boolean changed) { return setCenter(position.x, position.y, changed);           }

    public Style setWidth(float width)                    { return setWidth(width, true);       }
    public Style setWidth(float width, boolean changed)   { return setSize(width, dimension.y, changed); }
    public Style setHeight(float height)                  { return setHeight(height,true);      }
    public Style setHeight(float height, boolean changed) { return setSize(dimension.x,height,changed);  }

    public Style setSize(float w, float h)               { return setSize(w,h,true);     }
    public Style setSize(Vector2f size)                  { return setSize(size,true);    }
    public Style setSize(Vector2f size, boolean changed) { return setSize(size.x,size.y,changed); }

    public Style setSize(float w, float h, boolean changed) {
        if(dimension.x != w || dimension.y != h) {
            for(Observer observer : observers)
                ((D_Gui) observer).stackEvent(new D_GuiResizeEvent((D_Gui) observer, dimension.x, dimension.y, w, h));
        }

        this.dimension.set(w, h);
        this.center.set(position.x + w / 2.0f, position.y - h / 2.0f);
        if(changed) notifyObservers();
        return this;
    }

    public Style setCornerRadius(float radius) {
        if(radius <= Math.min(dimension.x, dimension.y) / 2.0f)
            this.cornerRadius = radius;
        return this;
    }

    public Style setBorderWidth(float size) { this.borderWidth = size; return this; }

    public Style setAlpha(float alpha) { this.bgColor.a(Math.max(0, Math.min(1, alpha))); return this; }

    public Style setBorderColor(int color)                 { this.borderColor.set(color); return this; }
    public Style setBorderColor(Color color)               { this.borderColor.set(color); return this; }
    public Style setBorderColor(int r, int g, int b)       { this.borderColor.set(r,g,b); return this; }
    public Style setBorderColor(float r, float g, float b) { this.borderColor.set(r,g,b); return this; }

    public Style setBgColor(int color)                 { this.bgColor.set(color); return this; }
    public Style setBgColor(Color color)               { this.bgColor.set(color); return this; }
    public Style setBgColor(int r, int g, int b)       { this.bgColor.set(r,g,b); return this; }
    public Style setBgColor(float r, float g, float b) { this.bgColor.set(r,g,b); return this; }

    public Style setBgTexture(Texture bgTexture) { this.bgTexture = bgTexture; return this; }

    public Style setMarginTop(float margin)     { this.margin[TOP]     = margin; return this; }
    public Style setMarginBottom(float margin)  { this.margin[BOTTOM]  = margin; return this; }
    public Style setMarginLEFT(float margin)    { this.margin[LEFT]    = margin; return this; }
    public Style setMarginRight(float margin)   { this.margin[RIGHT]   = margin; return this; }
    public Style setMargin(float margin)        { return setMargin(margin, margin, margin, margin); }

    public Style setMargin(float top, float bottom, float left, float right) {
        margin[TOP] = top;
        margin[BOTTOM] = bottom;
        margin[LEFT] = left;
        margin[RIGHT] = right;
        return this;
    }

    public Style setPaddingTop(float padding)    { this.padding[TOP]    = padding; return this; }
    public Style setPaddingBottom(float padding) { this.padding[BOTTOM] = padding; return this; }
    public Style setPaddingLeft(float padding)   { this.padding[LEFT]   = padding; return this; }
    public Style setPaddingRight(float padding)  { this.padding[RIGHT]  = padding; return this; }
    public Style setPadding(float padding)       { return this.setPadding(padding, padding, padding, padding);}

    public Style setPadding(float top, float bottom, float left, float right) {
        padding[TOP] = top;
        padding[BOTTOM] = bottom;
        padding[RIGHT] = right;
        padding[LEFT] = left;
        return this;
    }

    public Style set(Style style) {
        this.position.set(style.getPosition());
        this.dimension.set(style.getSize());
        this.center.set(style.getCenter());
        this.bgColor.set(style.getBgColor());
        this.borderColor.set(style.getBorderColor());

        this.borderWidth = style.getBorderWidth();
        this.setMargin(style.getMarginTop(), style.getMarginBottom(), style.getMarginLeft(), style.getMarginRight());
        this.setPadding(style.getPaddingTop(), style.getPaddingBottom(), style.getPaddingLeft(), style.getPaddingRight());
        return this;
    }

    @Override
    public String toString() {
        return "pos : <"+position.x + " , "+position.y+"> \n"+
                "scale : <"+ dimension.x+" , "+ dimension.y+">\n"+
                "color : <"+ bgColor +">\n"+
                "border_color : <"+borderColor+">\n"+
                "border_size : "+borderWidth+"\n";
    }

}
