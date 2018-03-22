package com.harmony.kindless.shiro.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.harmony.umbrella.context.ContextHelper;
import com.harmony.umbrella.log.Log;
import com.harmony.umbrella.log.Logs;
import com.harmony.umbrella.util.FileUtils;

/**
 * @author wuxii@foxmail.com
 */
public class FileSystemSessionDAO extends CachingSessionDAO {

    private static final String HTTP_TOUCK_KEY = FileSystemSessionDAO.class.getName() + ".HTTP_TOUCH_KEY";
    private static final Log log = Logs.getLog(FileSystemSessionDAO.class);
    private String path;
    private boolean supportHttpSession;

    public FileSystemSessionDAO() {
    }

    public FileSystemSessionDAO(String path) {
        this.path = path;
    }

    public FileSystemSessionDAO(String path, boolean supportHttpSession) {
        this.path = path;
        this.supportHttpSession = supportHttpSession;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        File file = new File(path, (String) sessionId);
        Session session = null;
        if (file.isFile()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                session = (Session) ois.readObject();
            } catch (Exception e) {
                log.warn(e);
            }
        }
        return supportHttpSession && ContextHelper.getHttpSession() != null //
                ? new HttpSupportSession(session, ContextHelper.getHttpSession())//
                : session;
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof HttpSupportSession) {
            session = ((HttpSupportSession) session).delegate;
        }
        write(session);
    }

    @Override
    protected void doDelete(Session session) {
        FileUtils.deleteFile(new File(path, (String) session.getId()));
    }

    protected void write(Session session) {
        File file = new File(path, (String) session.getId());
        FileUtils.createFile(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(session);
        } catch (Exception e) {
            log.warn(e);
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSupportHttpSession() {
        return supportHttpSession;
    }

    public void setSupportHttpSession(boolean supportHttpSession) {
        this.supportHttpSession = supportHttpSession;
    }

    protected static class HttpSupportSession implements ValidatingSession {

        private Session delegate;
        private HttpSession httpSession;

        public HttpSupportSession(Session delegate, HttpSession httpSession) {
            this.setBothSession(delegate, httpSession);
        }

        @Override
        public Serializable getId() {
            return delegate.getId();
        }

        @Override
        public Date getStartTimestamp() {
            return delegate.getStartTimestamp();
        }

        @Override
        public Date getLastAccessTime() {
            return delegate.getLastAccessTime();
        }

        @Override
        public long getTimeout() throws InvalidSessionException {
            return delegate.getTimeout();
        }

        @Override
        public void setTimeout(long maxIdleTimeInMillis) throws InvalidSessionException {
            delegate.setTimeout(maxIdleTimeInMillis);
            try {
                int timeout = Long.valueOf(maxIdleTimeInMillis / 1000).intValue();
                httpSession.setMaxInactiveInterval(timeout);
            } catch (Exception e) {
                throw new InvalidSessionException(e);
            }
        }

        @Override
        public String getHost() {
            return delegate.getHost();
        }

        @Override
        public void touch() throws InvalidSessionException {
            delegate.touch();
            try {
                httpSession.setAttribute(HTTP_TOUCK_KEY, HTTP_TOUCK_KEY);
                httpSession.removeAttribute(HTTP_TOUCK_KEY);
            } catch (Exception e) {
                throw new InvalidSessionException(e);
            }
        }

        @Override
        public void stop() throws InvalidSessionException {
            delegate.stop();
            httpSession.invalidate();
        }

        @Override
        public Collection<Object> getAttributeKeys() throws InvalidSessionException {
            return delegate.getAttributeKeys();
        }

        @Override
        public Object getAttribute(Object key) throws InvalidSessionException {
            return delegate.getAttribute(key);
        }

        @Override
        public void setAttribute(Object key, Object value) throws InvalidSessionException {
            delegate.setAttribute(key, value);
            if (key instanceof String) {
                httpSession.setAttribute((String) key, value);
            }
        }

        @Override
        public Object removeAttribute(Object key) throws InvalidSessionException {
            Object val = delegate.removeAttribute(key);
            if (key instanceof String) {
                httpSession.removeAttribute((String) key);
            }
            return val;
        }

        @Override
        public boolean isValid() {
            if (delegate instanceof ValidatingSession) {
                return ((ValidatingSession) delegate).isValid();
            }
            return false;
        }

        @Override
        public void validate() throws InvalidSessionException {
            if (delegate instanceof ValidatingSession) {
                ((ValidatingSession) delegate).validate();
            }
        }

        public void setBothSession(Session session, HttpSession httpSession) {
            this.delegate = session;
            this.httpSession = httpSession;
            Collection<Object> keys = session.getAttributeKeys();
            for (Object key : keys) {
                if (key instanceof String) {
                    httpSession.setAttribute((String) key, session.getAttribute(key));
                }
            }
        }

    }

}
