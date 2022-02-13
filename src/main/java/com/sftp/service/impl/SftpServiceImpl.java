package com.sftp.service.impl;

import com.jcraft.jsch.*;
import com.sftp.config.SecurePassword;
import com.sftp.config.SftpConfig;
import com.sftp.exception.NoSuchSmtpServer;
import com.sftp.model.SftpServer;
import com.sftp.service.SftpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.Vector;

@Service
public class SftpServiceImpl implements SftpService {

    @Autowired
    private SftpConfig sftpConfig;

    private Logger logger = LoggerFactory.getLogger(SftpServiceImpl.class);

    @Override
    public boolean uploadFile(SftpServer serverName, String filePath) {
        ChannelSftp channelSftp = null;
        try {
            SftpConfig.SftpConfigProperties config =  getConfiguration(serverName);
            channelSftp = createChannelSftp(config);
            channelSftp.put(filePath, config.getRemotePath());
            return true;
        } catch(SftpException | JSchException | NoSuchSmtpServer ex) {
            logger.error("Error upload file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return false;
    }

    @Override
    public boolean downloadFile(SftpServer serverName) {
        ChannelSftp channelSftp = null;
        try {
            SftpConfig.SftpConfigProperties config =  getConfiguration(serverName);
            channelSftp = createChannelSftp(config);
            channelSftp.cd(config.getRemotePath());
            Vector<ChannelSftp.LsEntry> v = channelSftp.ls(".");
            Iterator var12 = v.iterator();
            while (var12.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) var12.next();
                String filename = file.getFilename();
                if (!file.getAttrs().isDir()) {
                    channelSftp.get(filename, config.getLocalPath());
                }
            }
            return true;
        } catch(SftpException | JSchException | NoSuchSmtpServer ex) {
            logger.error("Error download file", ex);
        } finally {
            disconnectChannelSftp(channelSftp);
        }
        return false;
    }

    private ChannelSftp createChannelSftp(SftpConfig.SftpConfigProperties config) throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(config.getUsername(), config.getHost(), config.getPort());
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(SecurePassword.decrypt(config.getPassword()));
        session.connect(config.getSessionTimeout());
        Channel channel = session.openChannel("sftp");
        channel.connect(config.getChannelTimeout());
        return (ChannelSftp) channel;
    }

    private void disconnectChannelSftp(ChannelSftp channelSftp) {
        try {
            if( channelSftp == null)
                return;
            if(channelSftp.isConnected())
                channelSftp.disconnect();
            if(channelSftp.getSession() != null)
                channelSftp.getSession().disconnect();
        } catch(Exception ex) {
            logger.error("SFTP disconnect error", ex);
        }
    }

    private SftpConfig.SftpConfigProperties getConfiguration(SftpServer serverName) throws NoSuchSmtpServer {
        Optional<SftpConfig.SftpConfigProperties> sftpConfigProperties = sftpConfig.getList().stream().filter(sftp -> sftp.getServerName().equals(serverName)).findFirst();
        if(sftpConfigProperties.isPresent()) {
            return sftpConfigProperties.get();
        } else {
            throw new NoSuchSmtpServer(String.format("Invalid server configuration for : %s", serverName.name()));
        }
    }
}
