package com.handle.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Sex extends DomianType {

    private com.handle.util.domin.Sex sex;

    public com.handle.util.domin.Sex getSex() {

        return sex;
    }

    public void setSex(com.handle.util.domin.Sex sex) {

        this.sex = sex;
    }

    public Sex() {
        sex = new com.handle.util.domin.Sex();
        setTableName("sex");
    }

    private int sexId;

    private String sexDesc;

    public int getSexId() {

        return this.sex.getSexId();
    }

    public void setSexId(int sexId) {

        this.sex.setSexId(sexId);
    }

    public String getSexDesc() {

        return this.sex.getSexDesc();
    }

    public void setSexDesc(String sexDesc) {

        this.sex.setSexDesc(sexDesc);
    }

    @Override
    public List<DomianType> loadAll() {
        return null;
    }

    @Override
    public String getloadSql() {
        return "select * from " + getTableName() + " where sexId=" + this.getSexId();
    }

    @Override
    public void retrieve(ResultSet rs) {
        try {
            while (rs.next()) {
                setSexId(rs.getInt("sexId"));
                setSexDesc(rs.getString("sexDesc"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
