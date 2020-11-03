// package com.yestae.base.user.service.util;
//
// import com.google.common.collect.Maps;
// import com.yestae.base.user.model.entity.User;
// import com.yestae.base.user.model.util.*;
// import com.yestae.base.user.model.dto.TokenContent;
// import com.yestae.base.user.model.dto.UserDto;
// import com.yestae.common.utils.DateUtil;
// import com.yestae.framework.common.utils.StringUtils;
// import com.yestae.framework.redis.client.JedisClient;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import org.apache.commons.lang.math.NumberUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
//
// import javax.servlet.http.HttpServletRequest;
// import java.util.Date;
// import java.util.Map;
// import java.util.Random;
// import java.util.TreeSet;
//
// import static com.yestae.base.user.model.constant.HeaderConstant.*;
// import static com.yestae.base.user.model.constant.RedisConstant.TOKEN_PREFIX;
//
// /**
//  * @Description 用户工具类
//  * @Date 2019/7/26 18:09
//  * @Author hanyf
//  */
// @Component
// public class UserUtil {
//
//
//     /**
//      * web token 超时时间 一天
//      */
//     @Value("${web.token.expire.time:86400}")
//     private int webTokenExpireTime;
//
//     /**
//      * 盐
//      */
//     @Value("${user.token.secretkey:8c7d6436694217bce63e5abfbdfde75e}")
//     public String userTokenSecretkey;
//
//     @Autowired
//     protected JedisClient<Object> client;
//
//     @Autowired
//     protected CacheUtil cacheUtil;
//
//     @Autowired
//     private PasswordEncoder passwordEncoder;
//
//     /**
//      * 获取当前登陆用户的id
//      */
//     public static Long getCurrentUserId() {
//         HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//         return NumberUtils.createLong(request.getHeader(HEADER_USER_ID));
//     }
//
//     /**
//      * 获取当前登陆用户的名称
//      */
//     public static String getCurrentUserName() {
//         HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//         return request.getHeader(HEADER_USER_NAME);
//     }
//
//     public static String getToken() {
//         HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//         String token = request.getHeader(AUTHORIZATION);
//         if (ToolUtil.isEmpty(token)) {
//             token = request.getHeader(AUTHORIZATION_TOKEN);
//         }
//         if (ToolUtil.isEmpty(token)) {
//             token = request.getHeader(TOKEN);
//         }
//         return token;
//     }
//
//     public static UserDto getUserDto() {
//         UserDto userDto = new UserDto();
//         try {
//             HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
//             String userDtoString = request.getHeader(HEADER_USER_MESSAGE);
//             if (ToolUtil.isEmpty(userDtoString)) {
//                 return userDto;
//             }
//             userDto = ClazzConverterUtil.converterClass(userDtoString, UserDto.class);
//         } catch (Exception e) {
//         }
//         return userDto;
//     }
//
//
//     /**
//      * 生成token
//      *
//      * @param userDb 用户
//      */
//     public TokenContent generateToken(User userDb) {
//         TokenContent tokenInfo = new TokenContent();
//         Long loginDate = DateUtil.getCurrentMillis();
//         //生成token失效时间
//         long nowMillis = System.currentTimeMillis();
//         long expMillis = nowMillis + webTokenExpireTime * 1000;
//         //生成token
//         Date exp = new Date(expMillis);
//         String userId = userDb.getId().toString();
//         String token = Jwts.builder().setId(userId)
//                 .setIssuedAt(new Date(nowMillis))
//                 .claim("loginDate", loginDate)
//                 .claim("tokenExpireTime", webTokenExpireTime)
//                 .claim("userId", userDb.getId())
//                 .claim("tokenExpireDate", exp)
//                 .claim("userName", userDb.getName())
//                 .claim("account", userDb.getAccount())
//                 .claim("ip", userDb.getIp())
//                 .claim("browser", userDb.getBrowser())
//                 .signWith(SignatureAlgorithm.HS512, userTokenSecretkey).compact();
//         String key = this.addPrefix(userId, token, TOKEN_PREFIX);
//         cacheUtil.setToken(key, token, webTokenExpireTime);
//         tokenInfo.setToken(token);
//         tokenInfo.setLoginDate(loginDate);
//         tokenInfo.setTokenExpireDate(exp);
//         tokenInfo.setTokenExpireTime(webTokenExpireTime);
//         tokenInfo.setId(userId);
//         tokenInfo.setUserId(userDb.getId());
//         tokenInfo.setUserName(userDb.getName());
//         tokenInfo.setIp(userDb.getIp());
//         tokenInfo.setBrowser(userDb.getBrowser());
//         return tokenInfo;
//     }
//
//     public String addPrefix(String userId, String token, String prefix) {
//         String key = getKey(userId, token);
//         return prefix + ":" + key;
//     }
//
//     public String addPrefix(String token, String prefix) {
//         return prefix + ":" + token;
//     }
//
//     /**
//      * @return java.lang.String
//      * @Author hanyf
//      * @Description key生成
//      * @Date 17:20 2019/8/5
//      * @Param [userId, appKey, token]
//      **/
//     private String getKey(String userId, String token) {
//         StringBuffer key = new StringBuffer();
//         key.append(userId);
//         key.append(":");
//         key.append(token);
//         return key.toString();
//     }
//
//     /**
//      * 获取用户下的获取token列表
//      *
//      * @param userId
//      * @return
//      */
//     public Map<String, String> getUserTokenMap(String userId) {
//         TreeSet<String> userKeys = cacheUtil.getUserKeys(userId);
//         Map<String, String> tokenMap = Maps.newHashMap();
//         for (String key : userKeys) {
//             tokenMap.put(client.get(key), key);
//         }
//         return tokenMap;
//     }
//
//
//     /**
//      * 生成token
//      * @param openId
//      */
//     // public TokenContent generateToken(String openId) {
//     // 	//生成token失效时间
//     // 	long nowMillis = System.currentTimeMillis();
//     // 	int ttlMillis = appTokenExpireTime;
//     // 	long expMillis = nowMillis + ttlMillis * 1000;
//     // 	//生成token
//     // 	Date exp = new Date(expMillis);
//     // 	String token = Jwts.builder().setId(passwordEncoder.encode(openId))
//     // 			.setIssuedAt(new Date(nowMillis))
//     // 			.setExpiration(exp)
//     // 			.signWith(SignatureAlgorithm.HS512, userTokenSecretkey).compact();
//     // 	TokenContent tokenContent = new TokenContent();
//     // 	tokenContent.setToken(token);
//     // 	tokenContent.setTokenExpireTime(ttlMillis);
//     // 	tokenContent.setTokenExpireDate(exp);
//     // 	return tokenContent;
//     // }
//
//
//     /**
//      * 根据token获取token信息
//      *
//      * @return token信息
//      */
//     public TokenContent getTokenContent(String token) {
//         TokenContent tokenContent = new TokenContent();
//         try {
//             if (ToolUtil.isEmpty(token)) {
//                 return tokenContent;
//             }
//             Claims claims = Jwts.parser().setSigningKey(userTokenSecretkey).parseClaimsJws(token).getBody();
//             tokenContent.setId(claims.getId());
//             Object userId = claims.get("userId");
//             tokenContent.setUserId(NumberUtil.getLong(userId));
//
//             Object tokenExpireTime = claims.get("tokenExpireTime");
//             if (tokenExpireTime != null) {
//                 tokenContent.setTokenExpireTime(NumberUtil.getInt(tokenExpireTime));
//             }
//
//             Object loginDateObject = claims.get("loginDate");
//             tokenContent.setLoginDate(NumberUtil.getLong(loginDateObject));
//
//             Object tokenExpireDateObject = claims.get("tokenExpireDate");
//             Date tokenExpireDate = new Date(NumberUtil.getLong(tokenExpireDateObject));
//             tokenContent.setTokenExpireDate(tokenExpireDate);
//
//             Object userName = claims.get("userName");
//             tokenContent.setUserName(StringUtils.toString(userName));
//
//             Object account = claims.get("account");
//             tokenContent.setAccount(StringUtils.toString(account));
//
//             Object ip = claims.get("ip");
//             tokenContent.setIp(StringUtils.toString(ip));
//
//             Object browser = claims.get("browser");
//             tokenContent.setBrowser(StringUtils.toString(browser));
//
//             tokenContent.setToken(token);
//         } catch (Exception e) {
//             System.out.println(e);
//         }
//         return tokenContent;
//     }
//
//     public int getWebTokenExpireTime() {
//         return webTokenExpireTime;
//     }
//
//     public void setWebTokenExpireTime(int webTokenExpireTime) {
//         this.webTokenExpireTime = webTokenExpireTime;
//     }
//
//
//     /**
//      * 获取6位数验证码
//      *
//      * @return 验证码
//      */
//     public static String getCode() {
//         Random rad = new Random();
//         int code = 100000 + rad.nextInt(899999);
//         return code + "";
//     }
//
//     public static void main(String[] args) {
//         TokenContent tokenContent = new UserUtil().getTokenContent("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTY3NTAwMDMxLCJsb2dpbkRhdGUiOjE1Njc1MDAwMzE5NTUsInRva2VuRXhwaXJlVGltZSI6ODY0MDAsInVzZXJJZCI6MSwidG9rZW5FeHBpcmVEYXRlIjoxNTY3NTg2NDMxOTU1fQ.iFMWnk_LdwlSpnTTUlR4v-RPGZIf9exfaU36_gUsfgTLq6MlE4rDovVOeKKjqr2TkC1t7fZm2R19CXnG1anThw");
//         // System.out.println(tokenContent);
//         User user = new User();
//         user.setId(1L);
//         TokenContent tokenContent1 = new UserUtil().generateToken(user);
//
//         TokenContent tokenContent2 = new UserUtil().getTokenContent(tokenContent1.getToken());
//         System.out.println(tokenContent2);
//     }
// }
