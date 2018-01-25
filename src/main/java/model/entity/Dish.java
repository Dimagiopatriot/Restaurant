package model.entity;

public class Dish {

    private int id;
    private String name;
    private String description;
    private float portionInGrams;
    private float pricePerPortion;
    private DishType dishType;

    public enum DishType {
        ENTREE, SECOND, DESSERT, DRINKS;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static class Builder {
        private int id;
        private String name;
        private String description;
        private float portionInGrams;
        private float pricePerPortion;
        private DishType dishType;

        public Builder addId(int id){
            this.id = id;
            return this;
        }

        public Builder addName(String name) {
            this.name = name;
            return this;
        }

        public Builder addDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder addPortionInGrams(float portionInGrams) {
            this.portionInGrams = portionInGrams;
            return this;
        }

        public Builder addPricePerPortion(float pricePerPortion) {
            this.pricePerPortion = pricePerPortion;
            return this;
        }

        public Builder addDishType(DishType dishType) {
            this.dishType = dishType;
            return this;
        }

        public Dish createDish() {
            Dish dish = new Dish();
            dish.setId(id);
            dish.setName(name);
            dish.setDescription(description);
            dish.setPortionInGrams(portionInGrams);
            dish.setPricePerPortion(pricePerPortion);
            dish.setDishType(dishType);
            return dish;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPortionInGrams() {
        return portionInGrams;
    }

    public void setPortionInGrams(float portionInGrams) {
        this.portionInGrams = portionInGrams;
    }

    public float getPricePerPortion() {
        return pricePerPortion;
    }

    public void setPricePerPortion(float pricePerPortion) {
        this.pricePerPortion = pricePerPortion;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
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

        Dish dish = (Dish) o;

        if (id != dish.id) return false;
        if (Float.compare(dish.portionInGrams, portionInGrams) != 0) return false;
        if (Float.compare(dish.pricePerPortion, pricePerPortion) != 0) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        return dishType == dish.dishType;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (portionInGrams != +0.0f ? Float.floatToIntBits(portionInGrams) : 0);
        result = 31 * result + (pricePerPortion != +0.0f ? Float.floatToIntBits(pricePerPortion) : 0);
        result = 31 * result + (dishType != null ? dishType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", portionInGrams=" + portionInGrams +
                ", pricePerPortion=" + pricePerPortion +
                ", dishType=" + dishType +
                '}';
    }
}
