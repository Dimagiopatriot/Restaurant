package model.entity;

public class UserAuth {

    private int id;
    private String email;
    private String password;
    private Role role;

    public enum Role {
        ADMIN,
        CLIENT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public static class Builder {
        private int id;
        private String email;
        private String password;
        private Role role;

        public Builder addId(int id) {
            this.id = id;
            return this;
        }

        public Builder addEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder addRole(Role role) {
            this.role = role;
            return this;
        }

        public UserAuth createUserAuth() {
            UserAuth userAuth = new UserAuth();
            userAuth.setId(id);
            userAuth.setEmail(email);
            userAuth.setPassword(password);
            userAuth.setRole(role);
            return userAuth;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuth userAuth = (UserAuth) o;

        if (id != userAuth.id) return false;
        if (email != null ? !email.equals(userAuth.email) : userAuth.email != null) return false;
        if (password != null ? !password.equals(userAuth.password) : userAuth.password != null) return false;
        return role == userAuth.role;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
