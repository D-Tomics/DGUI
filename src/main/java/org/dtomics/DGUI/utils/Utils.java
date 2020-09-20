package org.dtomics.DGUI.utils;

import java.util.List;

public class Utils {

    public static Integer getJDKVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        }
        return Integer.parseInt(version);
    }

    public static<T> void swap(List<T> list, int index1, int index2) {
        if(index1 >= list.size() || index2 >= list.size() || index1 < 0 || index2 < 0) return;
        T first = list.get(index1);
        list.set(index1,list.get(index2));
        list.set(index2,first);
    }

}
