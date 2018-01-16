package model.entity;

public class User {

    private String name;
    private String surname;
    private String phone;
    private float count;
    private UserAuth userAuth;

    public static class Builder{
        private String name;
        private String surname;
        private String phone;
        private float count;
        private UserAuth userAuth;

        public Builder addName(String name){
            this.name = name;
            return this;
        }

        public Builder addSurname(String surname){
            this.surname = surname;
            return this;
        }

        public Builder addPhone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder addCount(float count){
            this.count = count;
            return this;
        }

        public Builder addUserAuth(UserAuth userAuth){
            this.userAuth = userAuth;
            return this;
        }

        public User createUser(){
            User user = new User();
            user.setName(name);
            user.setSurname(surname);
            user.setPhone(phone);
            user.setCount(count);
            user.setUserAuth(userAuth);
            return user;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (Float.compare(user.count, count) != 0) return false;
        if (!name.equals(user.name)) return false;
        if (!surname.equals(user.surname)) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return userAuth.equals(user.userAuth);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (count != +0.0f ? Float.floatToIntBits(count) : 0);
        result = 31 * result + userAuth.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", count=" + count +
                ", userAuth=" + userAuth +
                '}';
    }
}
