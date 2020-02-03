package engine.ui.utils;

public final class Debug {

    private static
    char[] hex = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    public static <T> void log(T obj) {
        System.out.println(obj);
    }

    public static String getHex(int integer) {
        String hexNum = "";
        int num = 0;
        for(;integer != 0;) {
            hexNum += hex[(byte)integer & 0x0f];
            integer >>= 4;
        }
        char[] arr = hexNum.toCharArray();
        hexNum = "0x";
        for(int i = arr.length-1; i >= 0; i--) hexNum += arr[i];
        return hexNum;
    }

}
