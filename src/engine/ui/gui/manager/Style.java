package engine.ui.gui.manager;

import engine.ui.gui.components.D_Gui;
import engine.ui.gui.manager.events.D_GuiEvent;
import engine.ui.gui.manager.events.D_GuiResizeEvent;
import engine.ui.gui.manager.events.D_GuiStateChangeEvent;
import engine.ui.utils.colors.Color;
import engine.ui.utils.colors.ColorFactory;
import engine.ui.utils.observers.Observable;
import engine.ui.utils.observers.Observer;
import org.joml.Vector2f;

import java.util.HashMap;

public class Style extends Observable {

    public Style script(String script) {
        String[] options = script.split(";");
        for(String option : options) {
            String[] variables = option.split(":");
            switch (variables[0]) {
                case "x": setX(Float.parseFloat(variables[1]));break;
                case "y" : setY(Float.parseFloat(variables[1])); break;
                case "w" : setWidth(Float.parseFloat(variables[1])); break;
                case "h" : setHeight(Float.parseFloat(variables[1])); break;
                case "borderSize" : setBorderSize(Float.parseFloat(variables[1])); break;
                case "color":
                    String[] values = variables[1].split(",");
                    if(values.length == 1) {
                        this.color.set(Integer.parseInt(variables[1]));
                    } else if(values.length == 3){
                        this.color.set(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
                    }
                    break;
            }
        }
        return this;
    }


    private Vector2f position;
    private Vector2f center;
    private Vector2f dimension;

    private float alpha = 1;
    private Color color;
    private Color borderColor;

    private float borderWidth;

    private String name;

    public Style() {
        this.color = new Color();
        this.borderColor = new Color();
        this.position = new Vector2f(0);
        this.dimension = new Vector2f(0);
        this.center = new Vector2f(0);
    }

    public float getX() { return position.x; }
    public float getY() { return position.y; }
    public float getCenterX() { return center.x; }
    public float getCenterY() { return center.y; }
    public float getWidth() { return dimension.x; }
    public float getHeight() { return dimension.y; }

    public Vector2f getPosition() { return this.position; }
    public Vector2f getCenter() { return this.center; }
    public Vector2f getSize() { return this.dimension; }

    public String getName() { return name; }

    public float getBorderWidth() { return borderWidth; }

    public float getAlpha() { return alpha; }
    public Color getColor() { return color; }
    public Color getBorderColor() { return borderColor; }

    // setters
    public Style setBounds(float x, float y, float width, float height) { return setBounds(x,y,width,height,true); }
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

    public Style setPosition(float x, float y) { return setPosition(x,y,true); }
    public Style setPosition(Vector2f position) { return setPosition(position,true); }

    public Style setPosition(float x, float y, boolean changed) { return this.setX(x,false).setY(y,changed); }
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

    public Style setCenter(float x, float y) { return setCenter(x,y,true); }
    public Style setCenter(Vector2f position) { return setCenter(position,true); }

    public Style setCenter(float x, float y, boolean changed) { return setCenterX(x, false).setCenterY(y, changed); }
    public Style setCenter(Vector2f position, boolean changed) { return setCenter(position.x, position.y, changed); }

    public Style setWidth(float width, boolean changed) { return setSize(width, dimension.y, changed); }
    public Style setHeight(float height, boolean changed) { return setSize(dimension.x,height,changed); }

    public Style setWidth(float width) { return setWidth(width, true); }
    public Style setHeight(float height) { return setHeight(height,true); }

    public Style setSize(float w, float h) { return setSize(w,h,true); }
    public Style setSize(Vector2f size) { return setSize(size,true);}

    public Style setSize(float w, float h, boolean changed) {
        if(dimension.x != w && dimension.y != h) {
            for(Observer observer : observers)
                ((D_Gui) observer).stackEvent(new D_GuiResizeEvent((D_Gui) observer, dimension.x, dimension.y, w, h));
        }

        this.dimension.set(w, h);
        this.center.set(position.x + w / 2.0f, position.y - h / 2.0f);
        if(changed) notifyObservers();
        return this;
    }
    public Style setSize(Vector2f size, boolean changed) { return setSize(size.x,size.y,changed);}

    public Style setName(String name) {
        this.name = name;
        return this;
    }

    public Style setBorderSize(float size) {
        this.borderWidth = size;
        return this;
    }

    public void setAlpha(float alpha) {
        if(alpha >= 1) this.alpha = 1;
        else if(alpha <= 0) this.alpha = 0;
        else this.alpha = alpha;
    }

    public void setBorderColor(Color color) { this.borderColor.set(color); }
    public void setBorderColor(int borderColor) { this.borderColor = ColorFactory.createColor(borderColor, this.borderColor); }
    public void setBorderColor(float r, float g, float b) { this.borderColor = ColorFactory.createColor(r,g,b,this.borderColor); }
    public void setBorderColor(int r, int g, int b) {  setBorderColor(r / 255f, g / 255f, b / 255f); }

    public void setColor(float r, float g, float b) { this.color = ColorFactory.createColor(r,g,b,color); }
    public void setColor(int r, int g, int b) { setColor(r / 255f, b / 255f, g / 255f); }
    public void setColor(int color) { this.color = ColorFactory.createColor(color,this.color); }
    public void setColor(Color color) { this.color.set(color); }

    public void set(Style style) {
        this.position.set(style.getPosition());
        this.dimension.set(style.getSize());
        this.center.set(style.getCenter());
        this.color.set(style.getColor());
        this.borderColor.set(style.getBorderColor());

        this.borderWidth = style.getBorderWidth();
        this.alpha = style.getAlpha();
    }

    @Override
    public String toString() {
        return "pos : <"+position.x + " , "+position.y+"> \n"+
                "scale : <"+ dimension.x+" , "+ dimension.y+">\n"+
                "color : <"+color+">\n"+
                "border_color : <"+borderColor+">\n"+
                "alpha : "+alpha+"\n"+
                "border_size : "+borderWidth+"\n";
    }

    @Override
    protected void onNotification(Observer observer) {
        ((D_Gui)observer).stackEvent(new D_GuiStateChangeEvent((D_Gui)observer));
    }
}
