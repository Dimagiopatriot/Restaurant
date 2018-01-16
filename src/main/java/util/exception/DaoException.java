package util.exception;

public class DaoException extends RuntimeException {

    public DaoException(){
        super();
    }

    DaoException(String msg){
        super(msg);
    }
}
