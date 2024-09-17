package org.examenprog2.examenfinalprog2.Repository;

import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    @Bean
    public Connection getConnection(){
        Connection connection =  null;
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");
        try {
           connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void main(String[] args){
        DataBaseConnection dbc = new DataBaseConnection();

        System.out.println(dbc.getConnection());
    }
}
