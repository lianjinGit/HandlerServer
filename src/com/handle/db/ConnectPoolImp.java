package com.handle.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import com.handle.resoureManager.RescourceManager;

/**
* @ClassName: ConnectPoolImp
* @Description: 简单的一个类，用于创建数据连接
*
*/

public class ConnectPoolImp implements ConnectionPool {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(ConnectPoolImp.class);

    private Vector<Connection> connectionVector;

    private int maxConnection;

    private int initConnection;

    private String db_driver;

    private String url;

    private String userName;

    private String password;

    private ReentrantLock lock = new ReentrantLock();

    public ConnectPoolImp() {
        init();
    }

    @Override
    public Connection getConnection() {
        lock.lock();
        Connection con = connectionVector.get(0);
        connectionVector.remove(0);
        lock.unlock();
        return con;

    }

    @Override
    public void releaseConnection(Connection connection) {
        lock.lock();
        connectionVector.add(connection);
        lock.unlock();
    }

    public void init() {
        connectionVector = new Vector<Connection>();
        db_driver = RescourceManager.getInstance().getProperty("uri.db.driver");
        userName = RescourceManager.getInstance().getProperty("uri.db.userName");
        password = RescourceManager.getInstance().getProperty("uri.db.password");
        initConnection = Integer.parseInt(RescourceManager.getInstance().getProperty("uri.db.initConnection"));
        maxConnection = Integer.parseInt(RescourceManager.getInstance().getProperty("uri.db.maxConnection"));
        url = RescourceManager.getInstance().getProperty("uri.db.url");
        if (logger.isInfoEnabled()) {
            logger.info("init() - maxConnection="
                + maxConnection
                + ",url="
                + url
                + ", initConnection="
                + initConnection
                + ", db_driver="
                + db_driver
                + ", userName="
                + userName
                + ", password="
                + password);
        }
        try {
            Class.forName(db_driver);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error("db driver class-->" + db_driver + " is not found", e);
        }
        for (int jj = 0; jj < initConnection; jj++) {
            try {
                Connection con = DriverManager.getConnection(url, userName, password);
                connectionVector.add(con);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
