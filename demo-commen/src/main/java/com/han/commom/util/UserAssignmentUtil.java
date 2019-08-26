package com.han.commom.util;

import com.yestae.common.entity.BaseInfoEntity;

import java.time.Instant;

/**
 * @Description 赋值创建人和修改人
 * @Date 2019/8/26 15:27
 * @Author hanyf
 */
public class UserAssignmentUtil<T extends BaseInfoEntity> {

	public void setCreateAndUpdateAndId(T baseInfo) {
		baseInfo.setId(GenerateIdUtil.nextId());
		long millis = Instant.now().getEpochSecond();
		baseInfo.setCreateId(UserUtil.getCurrentUserId());
		baseInfo.setCreateName(UserUtil.getCurrentUserName());
		baseInfo.setCreateTime(millis);
		baseInfo.setUpdateId(UserUtil.getCurrentUserId());
		baseInfo.setUpdateName(UserUtil.getCurrentUserName());
		baseInfo.setUpdateTime(millis);
	}

	public void setCreateAndUpdate(T baseInfo) {
		long millis = Instant.now().getEpochSecond();
		baseInfo.setCreateId(UserUtil.getCurrentUserId());
		baseInfo.setCreateName(UserUtil.getCurrentUserName());
		baseInfo.setCreateTime(millis);
		baseInfo.setUpdateId(UserUtil.getCurrentUserId());
		baseInfo.setUpdateName(UserUtil.getCurrentUserName());
		baseInfo.setUpdateTime(millis);
	}

	public void setUpdate(T baseInfo) {
		long millis = Instant.now().getEpochSecond();
		baseInfo.setUpdateId(UserUtil.getCurrentUserId());
		baseInfo.setUpdateName(UserUtil.getCurrentUserName());
		baseInfo.setUpdateTime(millis);
	}
}
