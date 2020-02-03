package engine.ui.utils;

import java.util.List;

public class Utils {

    public static<T> void swap(List<T> list, int index1, int index2) {
        if(index1 >= list.size() || index2 >= list.size() || index1 < 0 || index2 < 0) return;
        T first = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,first);
    }

}
