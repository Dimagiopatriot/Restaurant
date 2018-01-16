package model.entity;

import java.util.Map;

public class Order {

    private Map<Dish, Integer> portionsToDishMap;
    private boolean isBillPresentForOrder;
    private Status status;

    public enum Status {
        SEND_TO_KITCHEN, CANCELED, DONE
    }

    public static class Builder {
        private Map<Dish, Integer> portionsToDishMap;
        private boolean isBillPresentForOrder;
        private Status status;

        public Builder addPortionsToDishMap(Map<Dish, Integer> portionsToDishMap) {
            this.portionsToDishMap = portionsToDishMap;
            return this;
        }

        public Builder addIsBillPresentForOrder(boolean isBillPresentForOrder) {
            this.isBillPresentForOrder = isBillPresentForOrder;
            return this;
        }

        public Builder addSatus(Status status){
            this.status = status;
            return this;
        }

        public Order createOrder() {
            Order order = new Order();
            order.setPortionsToDishMap(portionsToDishMap);
            order.setStatus(status);
            order.setBillPresentForOrder(isBillPresentForOrder);
            return order;
        }
    }

    public Map<Dish, Integer> getPortionsToDishMap() {
        return portionsToDishMap;
    }

    public void setPortionsToDishMap(Map<Dish, Integer> portionsToDishMap) {
        this.portionsToDishMap = portionsToDishMap;
    }

    public boolean isBillPresentForOrder() {
        return isBillPresentForOrder;
    }

    public void setBillPresentForOrder(boolean billPresentForOrder) {
        isBillPresentForOrder = billPresentForOrder;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (isBillPresentForOrder != order.isBillPresentForOrder) return false;
        if (portionsToDishMap != null ? !portionsToDishMap.equals(order.portionsToDishMap) : order.portionsToDishMap != null)
            return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = portionsToDishMap != null ? portionsToDishMap.hashCode() : 0;
        result = 31 * result + (isBillPresentForOrder ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "portionsToDishMap=" + portionsToDishMap +
                ", isBillPresentForOrder=" + isBillPresentForOrder +
                ", status=" + status +
                '}';
    }
}
