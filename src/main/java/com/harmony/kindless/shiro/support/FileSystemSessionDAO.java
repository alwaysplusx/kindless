package com.harmony.kindless.shiro.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.harmony.umbrella.log.Log;
import com.harmony.umbrella.log.Logs;
import com.harmony.umbrella.util.FileUtils;

/**
 * @author wuxii@foxmail.com
 */
public class FileSystemSessionDAO extends CachingSessionDAO {

    private static final Log log = Logs.getLog(FileSystemSessionDAO.class);
    private String path;

    public FileSystemSessionDAO() {
    }

    public FileSystemSessionDAO(String path) {
        this.path = path;
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
        if (file.isFile()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Session) ois.readObject();
            } catch (Exception e) {
                log.warn(e);
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Session session) {
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

}
