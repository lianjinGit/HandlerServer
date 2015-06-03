package com.handle.util;

import java.util.List;

import com.handle.domain.Sex;
import com.handle.domain.UserType;

public class DataCache {

    public static List<Sex> allSex;

    public static List<UserType> allUserType;

    public static Sex getSex(int sexId) {
        for (Sex sex : allSex) {
            if (sex.getSexId() == sexId) {
                return sex;
            }
        }
        return null;
    }

    public static UserType getUserType(int userTypeId) {
        for (UserType userType : allUserType) {
            if (userType.getTypeId() == userTypeId) {
                return userType;
            }
        }
        return null;
    }
}
