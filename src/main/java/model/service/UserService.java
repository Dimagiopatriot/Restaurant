package model.service;

import model.dao.util.ConnectionManager;
import model.dao.util.DaoFactory;
import model.entity.User;
import model.entity.UserAuth;
import util.exception.DaoException;

import java.util.Optional;

public class UserService {

    private DaoFactory daoFactory;
    private ConnectionManager connectionManager;
    private UserAuthService userAuthService;

    public UserService(DaoFactory daoFactory, ConnectionManager connectionManager, UserAuthService userAuthService) {
        this.daoFactory = daoFactory;
        this.connectionManager = connectionManager;
        this.userAuthService = userAuthService;
    }

    private static class Holder{
        static final UserService INSTANCE = new UserService(DaoFactory.getInstance(), ConnectionManager.getInstance(),
                UserAuthService.getInstance());
    }

    public static UserService getInstance(){
        return Holder.INSTANCE;
    }

    public boolean updateUserPhone(User user){
        boolean isUpdated = false;
        try {
            connectionManager.startTransaction();
            isUpdated = daoFactory.getUserDao().updateUserPhone(user);
            connectionManager.commit();
        } catch (DaoException ex){
            connectionManager.rollback();
        }
        return isUpdated;
    }

    public boolean updateUserCount(User user){
        boolean isUpdated = false;
        try {
            connectionManager.startTransaction();
            isUpdated = daoFactory.getUserDao().updateUserCount(user);
            connectionManager.commit();
        } catch (DaoException ex){
            connectionManager.rollback();
        }
        return isUpdated;
    }

    public boolean update(User user){
        boolean isUpdated = false;
        try {
            connectionManager.startTransaction();
            isUpdated = daoFactory.getUserDao().update(user);
            connectionManager.commit();
        } catch (DaoException ex){
            connectionManager.rollback();
        }
        return isUpdated;
    }

    public boolean insert(User user){

        boolean isCreated = false;
        try{
            connectionManager.startTransaction();
            userAuthService.insert(user.getUserAuth());
            isCreated = daoFactory.getUserDao().insert(user);
            connectionManager.commit();
        } catch (DaoException ex){
            connectionManager.rollback();
            return isCreated;
        }
        return isCreated;
    }

    public Optional<User> select(int id){
        return daoFactory.getUserDao().select(id);
    }

    public Optional<User> selectByEmailPassword(String email, String password){
        Optional<UserAuth> userAuth = userAuthService.selectByEmailPassword(email, password);
        Optional<User> user;
        if (!userAuth.isPresent()){
            return Optional.empty();
        }
        user = select(userAuth.get().getId());
        user.ifPresent(user1 -> user1.setUserAuth(userAuth.get()));
        return user;
    }
}
