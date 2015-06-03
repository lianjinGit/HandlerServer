package com.handle.domain;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.handle.Factory;
import com.handle.util.DBUtil;
import com.handle.util.ErrorCodeMapping;
import com.handle.util.StringUil;

public class User extends DomianType {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(User.class);

    private com.handle.util.domin.User user;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public String getUserId() {

        return this.user.getUserId();
    }

    public void setUserId(String userId) {

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
        return "select * from "
            + getTableName()
            + " where userId='"
            + this.getUserId()
            + "' and password='"
            + this.getPassWord()
            + "'";

    }

    @Override
    public void retrieve(ResultSet rs) {
        try {
            while (rs.next()) {
                setUserId(rs.getString("userId"));
                setUserName(rs.getString("userName"));
                setUserTypeId(rs.getInt("useTypeId"));
                setSexId(rs.getInt("sexId"));
                setRigisterDate(rs.getTimestamp("rigisterDate"));
                setPassWord(rs.getString("password"));
                setLastAccessDate(rs.getTimestamp("lastAccessDate"));
                setEmailAddress(rs.getString("emailAddress"));
                setvCard(rs.getString("vCard"));
                setFound(true);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public String formatDate(Date date) {
        if (date == null)
            date = new Date();
        return sdf.format(date);
    }

    public int save() {
        int ret = 1;
        String sql =
            "insert into "
                + getTableName()
                + " (userId,userName,useTypeId,sexId,rigisterDate,password,lastAccessDate,emailAddress,vCard) values('%s','%s',%s,%s,'%s','%s','%s','%s','%s')";
        sql =
            String.format(
                sql,
                formatStr(getUserId()),
                formatStr(getUserName()),
                getUserTypeId(),
                getSexId(),
                formatDate(getRigisterDate()),
                formatStr(getPassWord()),
                formatDate(getLastAccessDate()),
                formatStr(getEmailAddress()),
                formatStr(getvCard()));
        if (logger.isInfoEnabled()) {
            logger.info("save() - sql=" + sql);
        }
        try {
            Connection con = Factory.getConnectionPool().getConnection();
            Statement statement = con.createStatement();
            DBUtil.executeUpdate(statement, sql);
            DBUtil.relaseResource(null, statement, con);
        }
        catch (SQLException e) {
            e.printStackTrace();
            ret = -1;
        }
        return ret;
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUserId("");
        user.setUserName("hahah");
        String str = user.toJsonString();
        Gson gson = new Gson();
        User user2 = gson.fromJson(str, User.class);
        System.out.println(user2.getUserName());
    }

    public int check() {
        if (StringUil.isNull(getUserId())) {
            return ErrorCodeMapping.ERROR_USERID_REQUIRED;
        }
        if (StringUil.isNull(getUserName())) {
            return ErrorCodeMapping.ERROR_USERNAME_REQUIRED;
        }
        if (getUserTypeId() == 0) {
            return ErrorCodeMapping.ERROR_USERTYPEID_REQUIRED;
        }
        if (getSexId() == 0) {
            return ErrorCodeMapping.ERROR_SEXID_REQUIRED;
        }
        if (StringUil.isNull(getPassWord())) {
            return ErrorCodeMapping.ERROR_PASSWORD_REQUIRED;
        }
        return ErrorCodeMapping.NO_ERROR;

    }

    public void loadByUserId() {
        String sql = "select * from " + getTableName() + " where userId='" + this.getUserId() + "'";
        Connection con = Factory.getConnectionPool().getConnection();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = DBUtil.executeQuery(statement, sql);
            retrieve(rs);
            DBUtil.relaseResource(rs, statement, con);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
