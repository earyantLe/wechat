package com.ctsig.common.security;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctsig.common.redis.JedisClient;
import com.ctsig.common.utils.SerializationUtil;

public class ShiroRedisSessionDAO extends AbstractSessionDAO {

	private static Logger logger = LoggerFactory.getLogger(ShiroRedisSessionDAO.class); 
	@Autowired 
	private JedisClient jedisClient; 
	private String keyPrefix = "shiro_redis_session:"; 
	private final int EXPIRE = 1800; 
	@Override 
	public void update(Session session) throws UnknownSessionException { 
		this.saveSession(session); 
	} 
	/** * save session * @param session * @throws UnknownSessionException */ 
	private void saveSession(Session session) throws UnknownSessionException{ 
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return; 
		} 
		byte[] key = this.getKeyBytes(session.getId()); 
		byte[] value = SerializationUtil.serialize(session); 
		session.setTimeout(EXPIRE*1000); 
		this.jedisClient.setex(key, value, EXPIRE); 
	} 
	
	@Override 
	public void delete(Session session) { 
		if(session == null || session.getId() == null){ 
			logger.error("session or session id is null"); 
			return; 
		} 
		this.jedisClient.del(this.getKeyBytes(session.getId())); 
		logger.info("SessionDAO.delete sessionId[{}]",session.getId()); 
	} 
	@Override 
	public Collection<Session> getActiveSessions() { 
		Set<Session> sessions = new HashSet<Session>(); 
		Set<byte[]> keys = this.jedisClient.keys((this.keyPrefix + "*").getBytes()); 
		if(keys != null && keys.size()>0){ 
			for(byte[] key:keys){ 
				Session s = (Session)SerializationUtil.deserialize(this.jedisClient.get(key));
				sessions.add(s);
			}
		} 
		return sessions;
		} 
	@Override 
	protected Serializable doCreate(Session session) { 
		Serializable sessionId = this.generateSessionId(session);  
		logger.info("SessionDAO.doCreate sessionId[{}]",sessionId);        
		this.assignSessionId(session, sessionId);        
		this.saveSession(session); 
		return sessionId; 
		} 
	@Override 
	protected Session doReadSession(Serializable sessionId) { 
		if(sessionId == null){ 
			logger.error("session id is null");
			return null;
		} 
		byte[] bytes = this.jedisClient.get(this.getKeyBytes(sessionId)); 
		Session s = null; 
		if(bytes == null){
			s = SecurityUtils.getSubject().getSession(true); 
			this.assignSessionId(s, sessionId);
			this.saveSession(s);
		}
		s = (Session)SerializationUtil.deserialize(bytes); 
		logger.info("SessionDAO.doReadSession sessionId[{}]",sessionId); 
		if(s==null){ logger.error("session is null"); } 
		return s;
	} 
	private byte[] getKeyBytes(Serializable id) {
		byte[] bis = SerializationUtil.serialize(id);
		return bis;
	}
}
