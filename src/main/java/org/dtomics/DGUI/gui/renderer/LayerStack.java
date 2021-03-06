package org.dtomics.DGUI.gui.renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class holds all the layers in the current gui system.
 * Also a wrapper around a list of Layers.
 *
 * @author kareem
 */
public class LayerStack implements Iterable<Layer> {

    private int stackPointer;
    private final ArrayList<Layer> layers;

    protected LayerStack() {
        this.layers = new ArrayList<>();
    }

    protected void push(Layer layer) {
        layers.add(stackPointer, layer);
        stackPointer++;
    }

    protected List<Layer> getLayers() {
        return layers;
    }

    protected Layer getLayer(int level) {
        return level < stackPointer ? layers.get(level) : null;
    }

    protected Layer getOverLay(int index) {
        Layer overLay = null;
        if (stackPointer + index > layers.size()) {
            overLay = new Layer();
            layers.add(overLay);
            return overLay;
        } else
            return layers.get(stackPointer + index - 1);
    }

    @Override
    public Iterator<Layer> iterator() {
        return layers.iterator();
    }
}
