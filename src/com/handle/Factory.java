package com.handle;

import com.handle.db.ConnectPoolImp;
import com.handle.db.ConnectionPool;

public class Factory {

    private static ConnectionPool connectionPool;

    public static ConnectionPool getConnectionPool() {
        if (connectionPool == null) {
            connectionPool = new ConnectPoolImp();
        }
        return connectionPool;
    }

    public static void main(String[] args) {
        getConnectionPool();
    }

}
