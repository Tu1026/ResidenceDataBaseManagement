package handler;

import java.sql.Connection;

public class DataHandler {

    private Connection connection;

    public DataHandler(){

    }



    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
