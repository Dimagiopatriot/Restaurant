package model.dao;

import model.entity.User;

public interface UserDao extends GenericDao<User> {

    boolean updateUserPhone(User user);
    boolean updateUserCount(User user);
}

