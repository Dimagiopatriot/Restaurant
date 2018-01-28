package controller;

public class Util {

    public final static int LIMIT = 5;
    public final static int MIN_OFFSET = 0;

    public static int[] pages(int ordersCount) {
        int[] pages;
        int pagesLength = ordersCount / Util.LIMIT;
        if (ordersCount % Util.LIMIT > 0){
            pagesLength++;
        }
        pages = new int[pagesLength];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }
        return pages;
    }
}
