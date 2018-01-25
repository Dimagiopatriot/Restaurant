package model.entity;

import java.util.Map;

public class Bill {

    private int id;
    private Map<Dish, Integer> portionsToDishMap;
    private float total;
    private Status status;

    public enum Status {
        PAYED, NOT_PAYED;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static class Builder {
        int id;
        private Map<Dish, Integer> portionsToDishMap;
        private float total;
        private Status status;

        public Builder addId(int id) {
            this.id = id;
            return this;
        }

        public Builder addPortionsToDishMap(Map<Dish, Integer> portionsToDishMap) {
            this.portionsToDishMap = portionsToDishMap;
            return this;
        }

        public Builder addTotal(float total) {
            this.total = total;
            return this;
        }

        public Builder addStatus(Status status) {
            this.status = status;
            return this;
        }

        public Bill createBill() {
            Bill bill = new Bill();
            bill.setId(id);
            bill.setPortionsToDishMap(portionsToDishMap);
            bill.setStatus(status);
            bill.setTotal(total);
            return bill;
        }
    }

    public Map<Dish, Integer> getPortionsToDishMap() {
        return portionsToDishMap;
    }

    public void setPortionsToDishMap(Map<Dish, Integer> portionsToDishMap) {
        this.portionsToDishMap = portionsToDishMap;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (id != bill.id) return false;
        if (Float.compare(bill.total, total) != 0) return false;
        if (portionsToDishMap != null ? !portionsToDishMap.equals(bill.portionsToDishMap) : bill.portionsToDishMap != null)
            return false;
        return status == bill.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (portionsToDishMap != null ? portionsToDishMap.hashCode() : 0);
        result = 31 * result + (total != +0.0f ? Float.floatToIntBits(total) : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", portionsToDishMap=" + portionsToDishMap +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
