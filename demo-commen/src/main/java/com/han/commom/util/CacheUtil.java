// package com.han.commom.util;
//
// import com.yestae.framework.redis.client.JedisClient;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.util.ObjectUtils;
// import redis.clients.jedis.Jedis;
// import redis.clients.jedis.JedisCluster;
// import redis.clients.jedis.JedisPool;
//
// import java.util.Map;
// import java.util.TreeSet;
//
// import static com.yestae.base.user.sdk.constant.Const.TOKEN_PREFIX;
//
// /**
//  * @Description 缓存工具类
//  * @Date 2019/7/26 16:56
//  * @Author hanyf
//  */
// @Slf4j
// @Component
// public class CacheUtil {
//
//
// 	@Autowired
// 	protected JedisCluster jedisCluster;
//
// 	@Autowired
// 	protected JedisClient<Object> client;
//
// 	@Value("${spring.redis.prefix}")
// 	private String redisCachePrefix;
//
// 	public String addPrefix(String key, String prefix) {
// 		return prefix + ":" + key;
// 	}
//
// 	/**
// 	 * 设置token
// 	 * @author liruifeng
// 	 * @param key key
// 	 * @param value 值
// 	 * @param seconds 有效时间
// 	 * @return
// 	 * @create 2018/12/12
// 	 **/
// 	public boolean setToken(String key, String value, int seconds) {
// 		return client.set(key, value, seconds);
// 	}
//
// 	/**
// 	 * 获取用户所有的keys
// 	 * @param userId
// 	 * @return
// 	 */
// 	public TreeSet<String> getUserKeys(String userId) {
// 		TreeSet<String> result = new TreeSet<>();
// 		userId = addPrefix(userId, TOKEN_PREFIX);
// 		userId += "*";
// 		TreeSet<String> keys = keys(userId);
// 		if (!ObjectUtils.isEmpty(keys)) {
// 			for (String key : keys) {
// 				key = key.replaceFirst(redisCachePrefix+":", "");
// 				result.add(key);
// 			}
// 		}
// 		return result;
// 	}
//
//
// 	/**
// 	 * 获取keys
// 	 * @param pattern
// 	 * @return
// 	 */
// 	public TreeSet<String> keys(String pattern) {
// 		pattern = redisCachePrefix+":"+pattern;
// 		TreeSet<String> keys = new TreeSet<>();
// 		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
// 		for (String k : clusterNodes.keySet()) {
// 			JedisPool jp = clusterNodes.get(k);
// 			Jedis connection = jp.getResource();
// 			try {
// 				keys.addAll(connection.keys(pattern));
// 			} catch (Exception e) {
// 				log.error("Getting keys error: {}", e);
// 			} finally {
// 				log.debug("Connection closed.");
// 				connection.close();//用完一定要close这个链接！！！
// 			}
// 		}
// 		return keys;
// 	}
// }
