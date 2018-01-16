package model.entity;

public class Dish {

    private String name;
    private String description;
    private float portionInGrams;
    private float pricePerPortion;
    private DishType dishType;

    public enum DishType {
        ENTREE, SECOND, DESSERT, DRINKS
    }

    public static class Builder {
        private String name;
        private String description;
        private float portionInGrams;
        private float pricePerPortion;
        private DishType dishType;

        public Builder addName(String name) {
            this.name = name;
            return this;
        }

        public Builder addDescription(String description) {
            this.description = description;
            return this;
        }

        private Builder addPortionInGrams(float portionInGrams) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (Float.compare(dish.portionInGrams, portionInGrams) != 0) return false;
        if (Float.compare(dish.pricePerPortion, pricePerPortion) != 0) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        return dishType == dish.dishType;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (portionInGrams != +0.0f ? Float.floatToIntBits(portionInGrams) : 0);
        result = 31 * result + (pricePerPortion != +0.0f ? Float.floatToIntBits(pricePerPortion) : 0);
        result = 31 * result + (dishType != null ? dishType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", portionInGrams=" + portionInGrams +
                ", pricePerPortion=" + pricePerPortion +
                ", dishType=" + dishType +
                '}';
    }
}
