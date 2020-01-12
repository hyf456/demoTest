package com.yestae.base.user.service.util;

import com.yestae.base.user.model.util.ToolUtil;
import com.yestae.common.entity.BaseInfoEntity;
import com.yestae.common.enums.BooleanEnum;
import com.yestae.common.utils.DateUtil;

/**
 * @Description 赋值创建人和修改人
 * @Date 2019/8/26 15:27
 * @Author hanyf
 */
public class UserAssignmentUtil {

    public static void setCreateAndUpdateAndId(BaseInfoEntity baseInfo) {
        baseInfo.setId(GenerateIdUtil.nextId());
        long millis = DateUtil.getCurrentMillis();
        baseInfo.setCreateId(UserUtil.getCurrentUserId());
        baseInfo.setCreateName(UserUtil.getCurrentUserName());
        baseInfo.setCreateTime(millis);
        baseInfo.setUpdateId(UserUtil.getCurrentUserId());
        baseInfo.setUpdateName(UserUtil.getCurrentUserName());
        baseInfo.setUpdateTime(millis);
        if (ToolUtil.isEmpty(baseInfo.getDeleteFlag())) {
            baseInfo.setDeleteFlag(BooleanEnum.NO.getValue());
        }
        if (ToolUtil.isEmpty(baseInfo.getStatus())) {
            baseInfo.setStatus(BooleanEnum.YES.getValue());
        }
    }

    public static void setCreateAndUpdate(BaseInfoEntity baseInfo) {
        long millis = DateUtil.getCurrentMillis();
        baseInfo.setCreateId(UserUtil.getCurrentUserId());
        baseInfo.setCreateName(UserUtil.getCurrentUserName());
        baseInfo.setCreateTime(millis);
        baseInfo.setUpdateId(UserUtil.getCurrentUserId());
        baseInfo.setUpdateName(UserUtil.getCurrentUserName());
        baseInfo.setUpdateTime(millis);
        if (ToolUtil.isEmpty(baseInfo.getDeleteFlag())) {
            baseInfo.setDeleteFlag(BooleanEnum.NO.getValue());
        }
        if (ToolUtil.isEmpty(baseInfo.getStatus())) {
            baseInfo.setStatus(BooleanEnum.YES.getValue());
        }
    }

    public static void setUpdate(BaseInfoEntity baseInfo) {
        long millis = DateUtil.getCurrentMillis();
        baseInfo.setUpdateId(UserUtil.getCurrentUserId());
        baseInfo.setUpdateName(UserUtil.getCurrentUserName());
        baseInfo.setUpdateTime(millis);
    }

    public static void main(String[] args) {
        Integer dd = 0;
        if (ToolUtil.isEmpty(dd)) {
            System.out.println(dd);
        }

    }
}
