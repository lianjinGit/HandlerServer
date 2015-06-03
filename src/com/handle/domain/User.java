package com.handle.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class User extends DomianType {

    private com.handle.util.domin.User user;

    public User() {
        user = new com.handle.util.domin.User();
        setTableName("user");
    }


    public com.handle.util.domin.User getUser() {

        return user;
    }


    public void setUser(com.handle.util.domin.User user) {

        this.user = user;
    }


    public int getUserId() {

        return this.user.getUserId();
    }

    public void setUserId(int userId) {

        this.user.setUserId(userId);
    }

    public String getUserName() {

        return this.user.getUserName();
    }

    public void setUserName(String userName) {

        this.user.setUserName(userName);
    }

    public String getPassWord() {

        return this.user.getPassWord();
    }

    public void setPassWord(String passWord) {

        this.user.setPassWord(passWord);
    }

    public UserType getUserType() {
        UserType ret = new UserType();
        com.handle.util.domin.UserType util = this.user.getUserType();
        ret.setUserType(util);
        return ret;
    }

    public void setUserType(UserType userType) {
        this.user.setUserType(userType.getUserType());
    }

    public int getUserTypeId() {
        return this.user.getUserTypeId();
    }

    public void setUserTypeId(int userTypeId) {

        this.user.setUserTypeId(userTypeId);
    }

    public String getEmailAddress() {

        return this.user.getEmailAddress();
    }

    public void setEmailAddress(String emailAddress) {

        this.user.setEmailAddress(emailAddress);
    }

    public Date getRigisterDate() {

        return this.user.getRigisterDate();
    }

    public void setRigisterDate(Date rigisterDate) {

        this.user.setRigisterDate(rigisterDate);
    }

    public Date getLastAccessDate() {

        return this.user.getLastAccessDate();
    }

    public void setLastAccessDate(Date lastAccessDate) {

        this.user.setLastAccessDate(lastAccessDate);
    }

    public int getSexId() {

        return this.user.getSexId();
    }

    public void setSexId(int sexId) {

        this.user.setSexId(sexId);;
    }

    public Sex getSex() {
        Sex ret = new Sex();
        ret.setSex(this.user.getSex());
        return ret;
    }

    public void setSex(Sex sex) {
      this.user.setSex(sex.getSex());
    }

    public String getvCard() {

        return this.user.getvCard();
    }

    public void setvCard(String vCard) {

        this.user.setvCard(vCard);
    }

    @Override
    public List<DomianType> loadAll() {
        return null;
    }

    @Override
    public String getloadSql() {
        return "select * from " + getTableName() + " where userId=" + this.getUserId();

    }

    @Override
    public void retrieve(ResultSet rs) {
        try {
            while (rs.next()) {
                setUserId(rs.getInt("userId"));
                setUserName(rs.getString("userName"));
                setUserTypeId(rs.getInt("useTypeId"));
                setSexId(rs.getInt("sexId"));
                setRigisterDate(rs.getTimestamp("rigisterDate"));
                setPassWord(rs.getString("password"));
                setLastAccessDate(rs.getTimestamp("lastAccessDate"));
                setEmailAddress(rs.getString("emailAddress"));
                setvCard(rs.getString("vCard"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public static void main(String[] args) {
        User user = new User();
        user.setUserId(123456);
        user.setUserName("hahah");
        String str = user.toJsonString();
        Gson gson = new Gson();
        User user2 = gson.fromJson(str, User.class);
        System.out.println(user2.getUserName());
    }

}
