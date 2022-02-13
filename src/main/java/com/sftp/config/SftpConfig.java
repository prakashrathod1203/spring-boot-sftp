package com.sftp.config;

import com.sftp.model.SftpServer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "sftp")
public class SftpConfig {

    private List<SftpConfigProperties> list;

    public List<SftpConfigProperties> getList() {
        return list;
    }

    public void setList(List<SftpConfigProperties> list) {
        this.list = list;
    }

    public static class SftpConfigProperties {
        private SftpServer serverName;
        private String host;
        private Integer port;
        private String username;
        private String password;
        private Integer sessionTimeout;
        private Integer channelTimeout;
        private String remotePath;
        private String localPath;

        public SftpServer getServerName() {
            return serverName;
        }

        public void setServerName(SftpServer serverName) {
            this.serverName = serverName;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getSessionTimeout() {
            return sessionTimeout;
        }

        public void setSessionTimeout(Integer sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }

        public Integer getChannelTimeout() {
            return channelTimeout;
        }

        public void setChannelTimeout(Integer channelTimeout) {
            this.channelTimeout = channelTimeout;
        }

        public String getRemotePath() {
            return remotePath;
        }

        public void setRemotePath(String remotePath) {
            this.remotePath = remotePath;
        }

        public String getLocalPath() {
            return localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }
    }

}
