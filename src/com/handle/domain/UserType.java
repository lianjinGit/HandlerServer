package com.handle.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserType extends DomianType {

    private com.handle.util.domin.UserType userType;

    public com.handle.util.domin.UserType getUserType() {

        return userType;
    }

    public void setUserType(com.handle.util.domin.UserType userType) {

        this.userType = userType;
    }

    public UserType() {
        userType = new com.handle.util.domin.UserType();
        setTableName("userType");
    }

    public int getTypeId() {

        return this.userType.getTypeId();
    }

    public void setTypeId(int typeId) {

        this.userType.setTypeId(typeId);
    }

    public String getTypedesc() {

        return this.userType.getTypedesc();
    }

    public void setTypedesc(String typedesc) {

        this.userType.setTypedesc(typedesc);
    }

    public String getTypeRights() {

        return this.userType.getTypeRights();
    }

    public void setTypeRights(String typeRights) {

        this.userType.setTypeRights(typeRights);
    }

    @Override
    public List<DomianType> loadAll() {
        return null;
    }

    @Override
    public String getloadSql() {
        return "select *  from " + getTableName() + " where typeId=" + this.getTypeId();
    }

    @Override
    public void retrieve(ResultSet rs) {
        try {
            while (rs.next()) {
                this.setTypeId(rs.getInt("typeId"));
                this.setTypedesc(rs.getString("typeDesc"));
                this.setTypeRights(rs.getString("typeRights"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
