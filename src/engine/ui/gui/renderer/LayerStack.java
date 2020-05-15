package engine.ui.gui.renderer;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  This class holds all the layers in the current gui system.
 *  Also a wrapper around a list of Layers.
 *
 * @author kareem
 */
public class LayerStack implements Iterable<Layer> {

    private int stackPointer;
    private Layer overLay;
    private ArrayList<Layer> layers;

    protected LayerStack() {
        this.layers = new ArrayList<>();
        this.layers.add(overLay = new Layer());
    }

    protected void push(Layer layer) {
        layers.add(stackPointer,layer);
        stackPointer++;
    }

    protected Layer get(int index) {
        return index < stackPointer ? layers.get(index) : null;
    }

    protected Layer getOverLay() {
        return overLay;
    }

    @Override
    public Iterator<Layer> iterator() {
        return layers.iterator();
    }
}
