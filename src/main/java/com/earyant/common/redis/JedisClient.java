package com.earyant.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.earyant.common.redis.exception.RedisCacheException;
import com.google.common.base.Optional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Service("jedisClient")
public class JedisClient {
	
	@Autowired
	JedisPool jedisPool;
	
	private static final Logger logger = LoggerFactory.getLogger(JedisClient.class);
	
	private static final String NX = "NX"; 
	private static final String EX = "EX"; 
	
	/**
	 * 
	 * @return
	 */
	public Optional<Jedis> jedis() throws RedisCacheException{
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			String errorMsg = "Get jedis instance error.";
			logger.error(errorMsg,e);
			throw new RedisCacheException(errorMsg,e);
		}
		return Optional.of(jedis);
	}
	
	/**
	 * 
	 * @param jedis
	 */
	public void returnJedis(Jedis jedis){
		if(jedis != null){
			jedis.close();
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean exists(String key) throws RedisCacheException{
		Boolean result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error("Redis EXISTS error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}

	public Boolean exists(byte[] key) throws RedisCacheException{
		Boolean result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error("Redis byte EXISTS error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("Redis GET error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	public byte[] get(byte[] key) throws RedisCacheException{
		if(key==null)return null;
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("Redis byte GET error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisCacheException
	 */
	public String set(String key, String value) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
		} catch (Exception e) {
			logger.error("Redis SET error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}	

	public String set(byte[] key, byte[] value) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
		} catch (Exception e) {
			logger.error("Redis byte SET error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return result;
	}
	
	/**
	 * @param key
	 * @param inc
	 * @return
	 * @throws RedisCacheException
	 */
	public Long incrBy(String key,long inc) throws RedisCacheException{
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.incrBy(key, inc);
		} catch (Exception e) {
			logger.error("Redis INCRBY error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 *
	 * @param key
	 * @param inc
	 * @return
	 * @throws RedisCacheException
	 */
	public Long decrBy(String key,long dec) throws RedisCacheException{
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.decrBy(key, dec);
		} catch (Exception e) {
			logger.error("Redis DECRBY error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param keys
	 * @return
	 * @throws RedisCacheException
	 */
	public List<String> mget(final String... keys) throws RedisCacheException{
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.mget(keys);
		} catch (Exception e) {
			logger.error("Redis MGET error,keys {}", keys,e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	public List<byte[]> mget(final byte[]... keys) throws RedisCacheException{
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.mget(keys);
		} catch (Exception e) {
			logger.error("Redis byte MGET error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @throws RedisCacheException
	 */
	public void del(String key) throws RedisCacheException{
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("Redis DEL error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	public void del(byte[] key) throws RedisCacheException{
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.error("Redis byte DEL error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	
	/**
	 * 
	 * @param pattern
	 * @return
	 * @throws RedisCacheException
	 */
	public Set<String> keys(String pattern) throws RedisCacheException{
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.keys(pattern);
		} catch (RuntimeException e) {
			logger.error("Redis KEYS error, pattern: {}",pattern, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return result;
	}
	
	public Set<byte[]> keys(byte[] pattern) throws RedisCacheException{
		Set<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.keys(pattern);
		} catch (RuntimeException e) {
			logger.error("Redis byte KEYS error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
		return result;
	}

	/**
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 * @throws RedisCacheException
	 */
	public Long expire(String key,int seconds) throws RedisCacheException{
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.expire(key,seconds);
		} catch (Exception e) {
			logger.error("Redis EXPIRE error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	public Long expire(byte[] key, int seconds) throws RedisCacheException{
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.expire(key,seconds);
		} catch (Exception e) {
			logger.error("Redis byte EXPIRE error.}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 * @throws RedisCacheException
	 */
	public String setex(String key,String value,int seconds) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.setex(key,seconds,value);
		} catch (Exception e) {
			logger.error("Redis SETEX error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	public String setex(byte[] key,byte[] value,int seconds) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.setex(key,seconds,value);
		} catch (Exception e) {
			logger.error("Redis byte SETEX error.", e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws RedisCacheException
	 */
	public Long ttl(String key) throws RedisCacheException{
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.ttl(key);
		} catch (Exception e) {
			logger.error("Redis TTL error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 * @throws RedisCacheException
	 */
	public String setnxex(String key, String value,int seconds) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.set(key, value, NX, EX, seconds);
		} catch (Exception e) {
			logger.error("Redis SETNX error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisCacheException
	 */
	public Long setnx(String key, String value) throws RedisCacheException{
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.setnx(key, value);
		} catch (Exception e) {
			logger.error("Redis setnx error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws RedisCacheException
	 */
	public String getset(String key, String value) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.getSet(key, value);
		} catch (Exception e) {
			logger.error("Redis GETSET error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param field
	 * @return
	 * @throws RedisCacheException
	 */
	public String hget( String key,String field) throws RedisCacheException{
		String result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hget(key,field);
		} catch (Exception e) {
			logger.error("Redis HGET error, key: {} field: {}",key,field,e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	

	public byte[] hget(byte[] key, byte[] field) throws RedisCacheException{
		byte[] result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hget(key,field);
		} catch (Exception e) {
			logger.error("Redis byte HGET error.",e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	/**
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @throws RedisCacheException
	 */
	public Long hset(String key,String field,String value) throws RedisCacheException{
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hset(key,field,value);
		} catch (Exception e) {
			logger.error("Redis HSET error, key: {} field: {}",key,field,e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}
	
	public  Map<String, String> hgetall( String key) throws RedisCacheException{
		Map<String, String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error("Redis HGETALL error, key: {}",key, e);
			throw new RedisCacheException(e);
		} finally {
			if(jedis!=null){
				jedis.close();
			}
		}
	    return result;
	}

}
