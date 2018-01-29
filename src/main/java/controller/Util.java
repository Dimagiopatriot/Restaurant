package controller;

import model.entity.Dish;
import model.entity.Order;

import java.util.Map;

public class Util {

    public final static int LIMIT = 5;
    public final static int MIN_OFFSET = 0;

    public static int[] pages(int ordersCount) {
        int[] pages;
        int pagesLength = ordersCount / Util.LIMIT;
        if (ordersCount % Util.LIMIT > 0) {
            pagesLength++;
        }
        pages = new int[pagesLength];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = i + 1;
        }
        return pages;
    }

    public static boolean checkSolvency(float total, float userCount) {
        return (userCount - total) > 0.;
    }

    public static float calculateTotal(Order order) {
        float result = 0f;
        for (Map.Entry<Dish, Integer> entry : order.getPortionsToDishMap().entrySet()) {
            int portions = entry.getValue();
            float pricePerGram = entry.getKey().getPricePerPortion();
            result += pricePerGram * portions;
        }

        return result;
    }
}
