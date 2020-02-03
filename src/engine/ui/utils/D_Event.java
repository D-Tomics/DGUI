package engine.ui.utils;

import java.io.Serializable;

public class D_Event<T> implements Serializable {

    T source;
    protected D_Event(T source) {
        this.source = source;
    }

    public T getSource() {
        return source;
    }
}
