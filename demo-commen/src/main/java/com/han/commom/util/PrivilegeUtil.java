package com.yestae.base.user.service.util;

import com.yestae.base.user.model.constant.PrivilegeConst;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Date 2019/8/1 14:02
 * @Author hanyf
 */
public class PrivilegeUtil {

    /**
     * 密码格式
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        if (StringUtils.isNotBlank(password)) {
            Matcher matcher = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{6,20})$").matcher(password);
            return matcher.matches();
        }
        return false;
    }

    public static Integer getMenuLevelsByParent(Integer parentMenuLevels) {
        if (null == parentMenuLevels) {
            return PrivilegeConst.MENU_LEVELS_ROOT;
        } else if (PrivilegeConst.MENU_LEVELS_ROOT.equals(parentMenuLevels)) {
            return PrivilegeConst.MENU_LEVELS_SECOND;
        } else if (PrivilegeConst.MENU_LEVELS_SECOND.equals(parentMenuLevels)) {
            return PrivilegeConst.MENU_LEVELS_THIRD;
        } else if (PrivilegeConst.MENU_LEVELS_THIRD.equals(parentMenuLevels)) {
            return PrivilegeConst.MENU_LEVELS_BUTTON;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        boolean validatePassword = validatePassword("aaasdasdads12a12312231231231qwadaa");
        System.out.println(validatePassword);
    }
}
