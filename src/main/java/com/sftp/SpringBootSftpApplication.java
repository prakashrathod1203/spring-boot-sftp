package com.sftp;

import com.sftp.model.SftpServer;
import com.sftp.service.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootSftpApplication  implements ApplicationRunner {

	@Autowired
	private SftpService sftpService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSftpApplication.class, args);

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		sftpService.downloadFile(SftpServer.C3);
	}
}
