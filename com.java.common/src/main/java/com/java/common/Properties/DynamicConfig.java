package com.java.common.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author liudongting
 * @date 2019/7/18 11:12
 */
public class DynamicConfig {

    private static final Logger logger = LoggerFactory.getLogger(DynamicConfig.class);
    private static DynamicConfig instance = new DynamicConfig();

    public static DynamicConfig getInstance() {
        return instance;
    }

    private final Map<String, Properties> cache = new HashMap<String, Properties>();
    private final Map<String, ReloadStrategy> reloadStrategyMap = new HashMap<String, ReloadStrategy>();
    private final Lock loadLock = new ReentrantLock();

    private DynamicConfig() {
    }

    public String getProperty(String filePath, String key) {
        return getProperties(filePath).getProperty(key);
    }

    public String getProperty(String filePath, String key, String defaultValue) {
        String value = getProperty(filePath, key);
        return value != null && value.trim().length() > 0 ? value : defaultValue;
    }

    public int getIntProperty(String filePath, String key, int defaultValue) {
        String intValue = getProperty(filePath, key);
        try {
            return Integer.parseInt(intValue);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(String filePath, String key) {
        return Boolean.parseBoolean(getProperty(filePath, key));
    }

    public Properties newProperties(String filePath, ReloadStrategy reloadStrategy) {
        loadLock.lock();
        try {
            Properties properties = cache.get(filePath);
            if (properties == null) {
                properties = load(filePath);
                cache.put(filePath, properties);
                reloadStrategyMap.put(filePath, reloadStrategy != null ? reloadStrategy : new LastModifyReloadStrategy(filePath, 0));
            }
            return properties;
        } finally {
            loadLock.unlock();
        }
    }

    public Properties getProperties(String filePath) {
        Properties properties = cache.get(filePath);
        ReloadStrategy reloadStrategy = reloadStrategyMap.get(filePath);
        if (properties == null) {
            properties = newProperties(filePath, reloadStrategy);
        } else if (reloadStrategy != null && reloadStrategy.needReload()) {
            properties = freshProperties(filePath);
        }
        return properties;
    }

    private Properties freshProperties(String filePath) {
        loadLock.lock();
        try {
            Properties properties = load(filePath);
            cache.put(filePath, properties);
            return properties;
        } finally {
            loadLock.unlock();
        }
    }

    private Properties load(String filePath) {
        logger.warn("Loading config file {}.", filePath);
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(filePath);
            properties.load(in);
            logger.warn("Loaded config file {}, properties {}", filePath, properties);
        } catch (Exception e) {
            logger.error("Error reading file {}.", filePath, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        return properties;
    }

    public static interface ReloadStrategy {
        boolean needReload();
    }

    public static class LastModifyReloadStrategy implements ReloadStrategy {
        final String filePath;
        long lastModify;
        final long millSecondBetweenCheck;
        volatile long nextCheckTime;

        LastModifyReloadStrategy(String filePath, long millSecondBetweenCheck) {
            this.filePath = filePath;
            this.lastModify = getFileLastModify();
            this.millSecondBetweenCheck = millSecondBetweenCheck > 0 ? millSecondBetweenCheck : 500;
            this.nextCheckTime = System.currentTimeMillis() + millSecondBetweenCheck;
        }

        public boolean needReload() {
            if (System.currentTimeMillis() > nextCheckTime) {
                nextCheckTime = System.currentTimeMillis() + this.millSecondBetweenCheck;
                long fileLastModify = getFileLastModify();
                if (fileLastModify > lastModify) {
                    lastModify = fileLastModify;
                    return true;
                }
            }
            return false;
        }

        private long getFileLastModify() {
            try {
                URL url = getClass().getResource(filePath);
                if (url != null) {
                    File file = new File(url.getFile());
                    if (file.exists()) {
                        return file.lastModified();
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
            return 0;
        }
    }


    /**
     * 使用
     */
    public static void main(String[] args) {
        final String URl=  DynamicConfig.getInstance()
                .getProperty("snow.properties", "Url");
    }
}
