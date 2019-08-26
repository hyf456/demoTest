package com.han.commom.util;

import com.google.common.collect.Maps;
import com.yestae.base.user.model.entity.User;
import com.yestae.base.user.sdk.dto.TokenContent;
import com.yestae.framework.redis.client.JedisClient;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import static com.yestae.base.user.sdk.constant.Const.TOKEN_PREFIX;

/**
 * @Description 用户工具类
 * @Date 2019/7/26 18:09
 * @Author hanyf
 */
@Component
public class UserUtil {

	private static final String AUTHORIZATION = "Authorization";
	/**
	 * header中的当前用户id
	 */
	public static final String HEADER_USER_ID = "userId";

	/**
	 * header中的当前用户Name
	 */
	public static final String HEADER_USER_NAME = "userName";

	/**
	 * web token 超时时间
	 */
	@Value("${web.token.expire.time:86400}")
	private int webTokenExpireTime;

	/**
	 * 盐
	 */
	@Value("${user.token.secretkey:8c7d6436694217bce63e5abfbdfde75e}")
	public String userTokenSecretkey;

	@Autowired
	protected JedisClient<Object> client;

	@Autowired
	protected CacheUtil cacheUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 获取当前登陆用户的id
	 */
	public static Long getCurrentUserId() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		return NumberUtils.createLong(request.getHeader(HEADER_USER_ID));
	}

	/**
	 * 获取当前登陆用户的名称
	 */
	public static String getCurrentUserName() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		return request.getHeader(HEADER_USER_NAME);
	}

	public static String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		String token = request.getHeader(AUTHORIZATION);
		return token;
	}


	/**
	 * 生成token
	 * @param userDb 用户
	 */
	public TokenContent generateToken(User userDb) {
		TokenContent tokenInfo = new TokenContent();
		Date loginDate = new Date();
		//生成token失效时间
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + webTokenExpireTime * 1000;
		//生成token
			Date exp = new Date(expMillis);
			String userId = userDb.getId().toString();
			String token = Jwts.builder().setId(userId)
					.setIssuedAt(new Date(nowMillis))
					.claim("loginDate", loginDate)
					.claim("tokenExpireTime", webTokenExpireTime)
					.claim("userId", userDb.getId())
					.signWith(SignatureAlgorithm.HS512, userDb.getSalt()).compact();
			String key = this.addPrefix(userId, token, TOKEN_PREFIX);
			cacheUtil.setToken(key, token, webTokenExpireTime);
			tokenInfo.setToken(token);
			tokenInfo.setLoginDate(loginDate);
			tokenInfo.setTokenExpireDate(exp);
			tokenInfo.setTokenExpireTime(webTokenExpireTime);
			tokenInfo.setId(userId);
			tokenInfo.setUserId(userDb.getId());
			tokenInfo.setName(userDb.getName());
		return tokenInfo;
	}

	public String addPrefix(String userId, String token, String prefix) {
		String key = getKey(userId, token);
		return prefix + ":" + key;
	}

	/**
	 * @Author hanyf
	 * @Description key生成
	 * @Date 17:20 2019/8/5
	 * @Param [userId, appKey, token]
	 * @return java.lang.String
	 **/
	private String getKey(String userId, String token) {
		StringBuffer key = new StringBuffer();
		key.append(userId);
		key.append(":");
		key.append(token);
		return key.toString();
	}

	/**
	 * 获取用户下的获取token列表
	 * @param userId
	 * @return
	 */
	public Map<String, String> getUserTokenMap(String userId){
		TreeSet<String> userKeys = cacheUtil.getUserKeys(userId);
		Map<String, String> tokenMap = Maps.newHashMap();
		for (String key : userKeys) {
			tokenMap.put(client.get(key), key);
		}
		return tokenMap;
	}


	/**
	 * 生成token
	 * @param openId
	 */
	// public TokenContent generateToken(String openId) {
	// 	//生成token失效时间
	// 	long nowMillis = System.currentTimeMillis();
	// 	int ttlMillis = appTokenExpireTime;
	// 	long expMillis = nowMillis + ttlMillis * 1000;
	// 	//生成token
	// 	Date exp = new Date(expMillis);
	// 	String token = Jwts.builder().setId(passwordEncoder.encode(openId))
	// 			.setIssuedAt(new Date(nowMillis))
	// 			.setExpiration(exp)
	// 			.signWith(SignatureAlgorithm.HS512, userTokenSecretkey).compact();
	// 	TokenContent tokenContent = new TokenContent();
	// 	tokenContent.setToken(token);
	// 	tokenContent.setTokenExpireTime(ttlMillis);
	// 	tokenContent.setTokenExpireDate(exp);
	// 	return tokenContent;
	// }


	/**
	 * 根据token获取token信息
	 * @return token信息
	 */
	public TokenContent getTokenContent(String token) {
		Claims claims = Jwts.parser().setSigningKey(userTokenSecretkey).parseClaimsJws(token).getBody();
		TokenContent tokenContent = new TokenContent();
		tokenContent.setId(claims.getId());
		Object userId = claims.get("userId");
		Object tokenExpireTime = claims.get("tokenExpireTime");
		if (tokenExpireTime != null) {
			tokenContent.setTokenExpireTime((Integer) tokenExpireTime);
		}
		tokenContent.setUserId(NumberUtil.getLong(userId));
		Object loginDateObject = claims.get("loginDate");
		Date loginDate = DateUtil.parseDate(loginDateObject.toString());
		tokenContent.setLoginDate(loginDate);
		Object tokenExpireDateObject = claims.get("tokenExpireDate");
		Date tokenExpireDate = DateUtil.parseDate(tokenExpireDateObject.toString());
		tokenContent.setTokenExpireDate(tokenExpireDate);
		return tokenContent;
	}

	public int getWebTokenExpireTime() {
		return webTokenExpireTime;
	}

	public void setWebTokenExpireTime(int webTokenExpireTime) {
		this.webTokenExpireTime = webTokenExpireTime;
	}



	/**
	 * 获取6位数验证码
	 * @return 验证码
	 */
	public static String getCode(){
		Random rad=new Random();
		int code  = 100000+rad.nextInt(899999);
		return code+"";
	}
}
