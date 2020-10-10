package org.dtomics.DGUI.utils.observers;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    protected List<Observer> observers = new ArrayList<>();

    public final void addObserver(Observer o) {
        observers.add(o);
    }

    public final void delete(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        if (observers == null) return;
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = observers.get(i);
            if (observer == null) continue;
            observer.updateStates(this);
        }
    }

}
