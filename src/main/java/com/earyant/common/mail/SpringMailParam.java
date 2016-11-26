package com.earyant.common.mail;

import java.util.List;
    

/**
 * 读取发收邮件的基本信息 默认MD5加密信息
 */
public class SpringMailParam { 
	
	// 发送邮件的服务器的IP和端口 
	private String mailServerHost; 
	private int mailServerPort =25; 
	// 登陆邮件发送服务器的用户名和密码 
	private String userName; 
	private String password; 
	
	// 邮件发送者的地址 
	private String fromAddress;  
	// 邮件接收者的地址 
	private String toAddress; 
	
	// 邮件接收者的地址 抄送
	private String cc; 
	
	// 邮件接收者的地址 暗送
	private String bcc; 
	
	// 是否需要身份验证 
	private boolean validate = true; 
	// 邮件主题 
	private String subject; 
	// 邮件的文本内容 
	private String content; 
	// 邮件附件的文件名 
	private List<String[]> fileList;
	public String getMailServerHost() {
		return mailServerHost;
	}
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}
	public int getMailServerPort() {
		return mailServerPort;
	}
	public void setMailServerPort(int mailServerPort) {
		this.mailServerPort = mailServerPort;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String[]> getFileList() {
		return fileList;
	}
	public void setFileList(List<String[]> fileList) {
		this.fileList = fileList;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	} 	 
}
 