package engine.ui.utils.observers;

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
        if(observers == null) return;
        for(Observer observer : observers) {
            if(observer == null) continue;
            observer.updateStates(this);
        }
    }

}
