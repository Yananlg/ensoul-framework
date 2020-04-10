package club.ensoul.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ShiroRedisSessionDAO extends AbstractSessionDAO {
    
    private String prefix = "shiro:session:";
    private RedisTemplate<String, Session> redisTemplate;
    
    // Redis缓存超时时间
    private Duration sessionInRedisTimeout = null;
    
    // 是否开启内存缓存
    private boolean sessionInMemoryEnabled = true;
    
    // 内存缓存时长
    private Duration sessionInMemoryTimeout = Duration.ofMinutes(10);
    
    // 内存缓存cookie
    private static ThreadLocal<Map<Serializable, ShiroSessionInMemory>> sessionsInThread = new ThreadLocal<>();
    
    public ShiroRedisSessionDAO(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public ShiroRedisSessionDAO(RedisTemplate<String, Session> redisTemplate, Duration sessionInRedisTimeout) {
        this.redisTemplate = redisTemplate;
        this.sessionInRedisTimeout = sessionInRedisTimeout;
    }
    
    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
        if(this.sessionInMemoryEnabled) {
            this.setSessionToThreadLocal(session.getId(), session);
        }
    }
    
    private void saveSession(Session session) throws UnknownSessionException {
        
        if(session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        
        try {
            
            String key = getRedisSessionKey(session.getId());
            if(sessionInRedisTimeout == null || sessionInRedisTimeout.toMillis() < 1) {
                redisTemplate.opsForValue().set(key, session, Duration.ofMillis(session.getTimeout()));
                return;
            }
            if(sessionInRedisTimeout.toMillis() < session.getTimeout()) {
                log.warn("Redis session sessionInRedisTimeout time: "
                        + sessionInRedisTimeout.toMillis()
                        + " is less than Session sessionInRedisTimeout: "
                        + session.getTimeout()
                        + " . It may cause some problems.");
            }
            redisTemplate.opsForValue().set(key, session, sessionInRedisTimeout);
        } catch(SerializationException e) {
            log.error("serialize session error. session id=" + session.getId());
            throw new UnknownSessionException(e);
        }
    }
    
    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        try {
            redisTemplate.delete(getRedisSessionKey(session.getId()));
        } catch(SerializationException e) {
            log.error("delete session error. session id=" + session.getId());
        }
    }
    
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        try {
            Set<String> keys = redisTemplate.keys(this.prefix + "*");
            if(keys != null && keys.size() > 0) {
                for(String key : keys) {
                    Session s = redisTemplate.opsForValue().get(key);
                    sessions.add(s);
                }
            }
        } catch(SerializationException e) {
            log.error("get active sessions error.");
        }
        return sessions;
    }
    
    @Override
    protected Serializable doCreate(Session session) {
        if(session == null) {
            log.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }
    
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        if(this.sessionInMemoryEnabled) {
            Session session = getSessionFromThreadLocal(sessionId);
            if(session != null) {
                return session;
            }
        }
        
        Session session = null;
        log.debug("read session from redis");
        try {
            session = redisTemplate.opsForValue().get(getRedisSessionKey(sessionId));
            if(this.sessionInMemoryEnabled) {
                setSessionToThreadLocal(sessionId, session);
            }
        } catch(SerializationException e) {
            log.error("read session error. settionId=" + sessionId);
        }
        return session;
    }
    
    private void setSessionToThreadLocal(Serializable sessionId, Session session) {
        Map<Serializable, ShiroSessionInMemory> sessionMap = sessionsInThread.get();
        if(sessionMap == null) {
            sessionMap = new HashMap<>();
            sessionsInThread.set(sessionMap);
        }
        ShiroSessionInMemory shiroSessionInMemory = new ShiroSessionInMemory();
        shiroSessionInMemory.setCreateTime(new Date());
        shiroSessionInMemory.setSession(session);
        sessionMap.put(sessionId, shiroSessionInMemory);
    }
    
    private Session getSessionFromThreadLocal(Serializable sessionId) {
        
        Session session = null;
        if(sessionsInThread.get() == null) {
            return null;
        }
        
        Map<Serializable, ShiroSessionInMemory> sessionMap = sessionsInThread.get();
        ShiroSessionInMemory shiroSessionInMemory = sessionMap.get(sessionId);
        if(shiroSessionInMemory == null) {
            return null;
        }
        
        Date now = new Date();
        long duration = now.getTime() - shiroSessionInMemory.getCreateTime().getTime();
        if(duration < sessionInMemoryTimeout.toMillis()) {
            session = shiroSessionInMemory.getSession();
            log.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }
        return session;
    }
    
    private String getRedisSessionKey(Serializable sessionId) {
        return this.prefix + sessionId;
    }
    
    /**
     * 设置缓存名称前缀，默认：shiro:session:
     *
     * @param prefix 缓存名称前缀
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    /**
     * 设置操作缓存的 {@link RedisTemplate}
     *
     * @param redisTemplate {@link RedisTemplate}
     */
    public void setRedisTemplate(RedisTemplate<String, Session> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * Redis缓存时长
     *
     * @param sessionInRedisTimeout {@link Duration}
     */
    public void setSessionInRedisTimeout(Duration sessionInRedisTimeout) {
        this.sessionInRedisTimeout = sessionInRedisTimeout;
    }
    
    /**
     * 是否开启内存缓存cookie功能
     *
     * @param sessionInMemoryEnabled {@code true} 开启，{@code false} 不开启
     */
    public void setSessionInMemoryEnabled(boolean sessionInMemoryEnabled) {
        this.sessionInMemoryEnabled = sessionInMemoryEnabled;
    }
    
    /**
     * 内存缓存时长
     *
     * @param sessionInMemoryTimeout {@link Duration}
     */
    public void setSessionInMemoryTimeout(Duration sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }
}
