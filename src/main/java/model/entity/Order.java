package model.entity;

import java.util.Map;

public class Order {

    private int id;
    private Map<Dish, Integer> portionsToDishMap;
    private boolean isBillPresentForOrder;
    private Status status;
    private int userId;

    public enum Status {
        SEND_TO_KITCHEN, CANCELED, DONE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static class Builder {
        private int id;
        private Map<Dish, Integer> portionsToDishMap;
        private boolean isBillPresentForOrder;
        private Status status;

        public Builder addId(int id){
            this.id = id;
            return this;
        }

        public Builder addPortionsToDishMap(Map<Dish, Integer> portionsToDishMap) {
            this.portionsToDishMap = portionsToDishMap;
            return this;
        }

        public Builder addIsBillPresentForOrder(boolean isBillPresentForOrder) {
            this.isBillPresentForOrder = isBillPresentForOrder;
            return this;
        }

        public Builder addStatus(Status status) {
            this.status = status;
            return this;
        }

        public Order createOrder() {
            Order order = new Order();
            order.setId(id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (isBillPresentForOrder != order.isBillPresentForOrder) return false;
        if (userId != order.userId) return false;
        if (portionsToDishMap != null ? !portionsToDishMap.equals(order.portionsToDishMap) : order.portionsToDishMap != null)
            return false;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (portionsToDishMap != null ? portionsToDishMap.hashCode() : 0);
        result = 31 * result + (isBillPresentForOrder ? 1 : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", portionsToDishMap=" + portionsToDishMap +
                ", isBillPresentForOrder=" + isBillPresentForOrder +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}
