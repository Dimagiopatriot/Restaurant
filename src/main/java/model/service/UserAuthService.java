package model.service;

import model.dao.util.DaoFactory;
import model.entity.UserAuth;

import java.util.Optional;

public class UserAuthService {

    private DaoFactory daoFactory;

    public UserAuthService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class Holder{
        static final UserAuthService INSTANCE = new UserAuthService(DaoFactory.getInstance());
    }

    public static UserAuthService getInstance(){
        return Holder.INSTANCE;
    }

    public Optional<UserAuth> selectByEmailPassword(String email, String password){
        return daoFactory.getUserAuthDao().selectByEmailPassword(email, password);
    }

    public boolean update(UserAuth userAuth){
        return daoFactory.getUserAuthDao().update(userAuth);
    }

    public boolean insert(UserAuth userAuth){
        return daoFactory.getUserAuthDao().insert(userAuth);
    }

    public Optional<UserAuth> select(int id){
        return daoFactory.getUserAuthDao().select(id);
    }
}
