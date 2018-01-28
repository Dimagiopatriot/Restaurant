package util.constant;

public interface LogMessages {

    String ERROR_IN_DAO = "error in dao";

    String SELECT_BY_EMAIL_PASSWORD = "selectByEmailPassword()";
    String UPDATE = "update()";
    String INSERT = "insert()";
    String SELECT = "select()";

    String UPDATE_USER_COUNT = "updateUserCount()";
    String UPDATE_USER_PHONE = "updateUserPhone()";
    String UPDATE_STATUS = "updateStatus()";
    String UPDATE_BILL_PRESENCE = "updateBillPresence()";

    String SELECT_BY_STATUS = "selectByStatus()";
    String SELECT_ALL = "selectAll()";
    String SELECT_BY_USER_ID = "selectByUserId()";
    String SELECT_FOR_BILL = "selectDishesForBill()";
    String SELECT_FOR_ORDER = "selectDishesForOrder()";
    String SELECT_BY_DISH_TYPE = "selectByDishType()";

    String INSERT_INTO_M2O_DISH_BILL = "IntoManyToOneDishBillTable()";
    String INSERT_INTO_M2O_DISH_ORDER = "IntoManyToOneDishOrderTable()";
}
