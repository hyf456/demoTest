package com.yestae.base.user.service.log.factory;

import com.yestae.base.user.model.entity.LoginLog;
import com.yestae.base.user.model.entity.LoginLogVO;
import com.yestae.base.user.model.entity.User;
import com.yestae.base.user.model.util.SpringContextHolder;
import com.yestae.base.user.service.dao.LoginLogMapper;
import com.yestae.base.user.service.dao.UserMapper;
import com.yestae.common.enums.BooleanEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @Description 日志工厂
 * @Date 2019/8/1 11:25
 * @Author hanyf
 */
@Slf4j
public class LogTaskFactory {

    private static LoginLogMapper loginLogMapper = SpringContextHolder.getBean(LoginLogMapper.class);
    private static UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

    public static TimerTask loginLog(final Long userId, final String ip, String browser, String token) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    User load = userMapper.getById(userId);
                    LoginLog loginLog = LogFactory.createLoginLog(browser, BooleanEnum.YES, load, null, ip, token);
                    loginLogMapper.insertSelective(loginLog);
                } catch (Exception e) {
                    log.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String internalMessage, final String ip, String browser) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                        browser, BooleanEnum.NO, username, msg, internalMessage, ip);
                try {
                    loginLogMapper.insertSelective(loginLog);
                } catch (Exception e) {
                    log.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Long userId, final String ip, String browser, String token) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    User load = userMapper.getById(userId);
                    LoginLog loginLog = LogFactory.createLoginLog(browser, BooleanEnum.YES, load, null, ip, token);
                    LoginLogVO loginLogVOParam = new LoginLogVO();

                    loginLogMapper.logout(token);
                    loginLogMapper.insertSelective(loginLog);
                } catch (Exception e) {
                    log.error("创建退出日志异常!", e);
                }
            }
        };
    }

}
