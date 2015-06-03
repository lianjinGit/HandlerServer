package com.handle.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.handle.Factory;

public class DBUtil {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(DBUtil.class);

    public static int executeUpdate(Statement stmt, String sql) {
        try {
            return stmt.executeUpdate(sql);
        }
        catch (SQLException e) {
            logger.error("SQL:" + sql);
            logger.error(e);
            return -1;
        }
    }

    public static ResultSet executeQuery(Statement stmt, String sql) {
        try {
            return stmt.executeQuery(sql);
        }
        catch (SQLException e) {
            logger.error("SQL:" + sql);
            logger.error(e);
            e.printStackTrace();
            return null;
        }
    }

    public static boolean executeOther(Statement stmt, String sql) {
        try {
            return stmt.execute(sql);
        }
        catch (SQLException e) {
            logger.error("SQL:" + sql);
            logger.error(e);
            return false;
        }
    }

    public static void relaseResultSet(ResultSet re) {
        try {
            if (re != null) {
                re.close();
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public static void relaseStatement(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public static void relaseConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
    }

    public static void relaseResource(ResultSet re, Statement stm, Connection con) {
        try {
            if (re != null) {
                re.close();
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        try {
            if (stm != null) {
                stm.close();
            }
        }
        catch (SQLException e) {
            logger.error(e);
        }
        if (con != null) {
            Factory.getConnectionPool().releaseConnection(con);
        }
    }

}
