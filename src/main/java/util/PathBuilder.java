package util;

public class PathBuilder {
    private final static String MAIN_PART = "/view/";
    private final static String PAGE_TYPE = ".jsp";

    public static String buildPath(String page) {
        return MAIN_PART + page + PAGE_TYPE;
    }
}
