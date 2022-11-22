package com.myproject.onlinecourses.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String PREFIX = "PAY_00";
        String SUFFIX = "";
        try {
            Connection connection = session.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(id) from payment");
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
