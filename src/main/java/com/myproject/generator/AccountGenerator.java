package com.myproject.generator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String PREFIX = "ACC0";
        String SUFFIX = "";
        try {
            Connection connection = sharedSessionContractImplementor.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(id) from account");
            if(resultSet.next()) {
                Integer id = resultSet.getInt(1) + 1;
                SUFFIX = id.toString();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return PREFIX + SUFFIX;
    }
}
