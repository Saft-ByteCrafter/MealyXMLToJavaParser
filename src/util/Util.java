package util;

public class Util {
    public static String strip(String in) { //just what i assume would be the most common characters that would throw syntax errors
        return in.replace(".", "_").replace(",", "_");
    }

}
