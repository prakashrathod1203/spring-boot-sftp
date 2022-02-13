package com.sftp.service;

import com.sftp.model.SftpServer;

public interface SftpService {
    boolean uploadFile(SftpServer serverName, String filePath);
    boolean downloadFile(SftpServer serverName);
}
