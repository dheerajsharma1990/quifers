package com.quifers.hibernate;

import com.quifers.dao.*;
import com.quifers.dao.impl.*;

public class DaoFactory {

    private DaoWrapper daoWrapper;

    public DaoFactory(DaoWrapper daoWrapper) {
        this.daoWrapper = daoWrapper;
    }

    public AdminAccountDao getAdminAccountDao() {
        return new AdminAccountDaoImpl(daoWrapper);
    }

    public AdminDao getAdminDao() {
        return new AdminDaoImpl(daoWrapper);
    }

    public FieldExecutiveAccountDao getFieldExecutiveAccountDao() {
        return new FieldExecutiveAccountDaoImpl(daoWrapper);
    }

    public FieldExecutiveDao getFieldExecutiveDao() {
        return new FieldExecutiveDaoImpl(daoWrapper);
    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl(daoWrapper);
    }

}
