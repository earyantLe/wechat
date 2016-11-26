package com.earyant.test;

import org.junit.Test;

//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//表示继承了SpringJUnit4ClassRunner类
//@ContextConfiguration(locations = { "classpath:spring/applicationContext-redis.xml" })
public class TestRedisClient {
//	@Autowired
//	JedisClient jedisClient;
	@Test
	public void test() {
//		String key = "alice_2016461346";
//		String value = "Hello world!";
//		jedisClient.set(key,value);
//		System.out.println(jedisClient.get(key));
//		/**
//		 * trasaction or pipeline usage
//		 * multi delete below
//		 */
//		Optional<Jedis> optional = jedisClient.jedis();
//		if(optional.isPresent()){
//			Jedis jedis = optional.get();
//			try {
//				String pattern = "alice_*";
//	            Set<byte[]> keys = jedisClient.keys(pattern.getBytes());
//				Transaction tx = jedis.multi();
//				for (byte[] bytes : keys) {
//					tx.del(bytes);
//				}
//				tx.exec();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				jedisClient.returnJedis(jedis);
//			}
//		}else{
//			//throw exception
//		}
	}
}
