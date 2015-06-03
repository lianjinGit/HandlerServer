package com.handle.domain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.gson.Gson;
import com.handle.Factory;
import com.handle.util.DBUtil;

public abstract class DomianType {

    public String tableName;

    public abstract List<DomianType> loadAll();

    public abstract String getloadSql();

    public abstract void retrieve(ResultSet rs);

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int load() {
        int ret = 1;
        Connection con = Factory.getConnectionPool().getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = DBUtil.executeQuery(statement, getloadSql());
            retrieve(rs);
            DBUtil.relaseResource(rs, statement, con);
        }
        catch (SQLException e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    public DomianType initFromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, this.getClass());
    }

    public String getTableName() {

        return tableName;
    }

    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

}
