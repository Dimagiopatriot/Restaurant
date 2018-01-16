package model.dao;

import model.entity.UserAuth;

import java.util.Optional;

public interface UserAuthDao extends GenericDao<UserAuth> {

    Optional<UserAuth> selectByEmailPassword(String email, String password);
    boolean deleteById(int id);

}
