package com.ctsig.common.mail;
 
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.ctsig.CommonConstants;
import com.ctsig.common.utils.CommonUtils;

/** 
* 简单邮件（不带附件的邮件）发送器 
*/ 
public class SimpleMailSender  {  
	 private static final Logger log = Logger.getLogger(SimpleMailSender.class);
	/** 
	 * 以文本格式发送邮件 
 	* @param mailInfo 待发送的邮件的信息 
 	*/ 
	public static HashMap<String, Object>  sendTextMail(MailSenderInfo mailInfo) { 
		HashMap<String, Object> map=new HashMap<String, Object>();	 
		map.put("mail_type", CommonConstants.MAIL_TYPE_SEND);
		map.put("mail_title", mailInfo.getSubject());
		map.put("mail_cnt", mailInfo.getContent());		
		map.put("mail_frommail", mailInfo.getFromAddress());
		map.put("mail_host", mailInfo.getMailServerHost());
		map.put("mail_protocol", mailInfo.getMailServerPort());
		map.put("mail_filepath", "");
		map.put("mail_staff", mailInfo.getToAddress());   
		
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null; 
	  Properties pro = mailInfo.getProperties();
	  if (mailInfo.isValidate()) { 
	  // 如果需要身份认证，则创建一个密码验证器 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()); 
	  }
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	  try { 
		// 根据session创建一个邮件消息 
		  Message mailMessage = new MimeMessage(sendMailSession); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress(mailInfo.getFromAddress()); 
		  // 设置邮件消息的发送者 
		  mailMessage.setFrom(from); 
		  // 创建邮件的接收者地址，并设置到邮件消息中 
		  Address to = new InternetAddress(mailInfo.getToAddress()); 
		  mailMessage.setRecipient(Message.RecipientType.TO,to); 
		  // 设置邮件消息的主题 
		  mailMessage.setSubject(mailInfo.getSubject()); 
		  // 设置邮件消息发送的时间 
		  mailMessage.setSentDate(new Date()); 
		  // 设置邮件消息的主要内容 
		  String mailContent = mailInfo.getContent(); 
		  mailMessage.setText(mailContent); 
		  // 发送邮件 
		  Transport.send(mailMessage);
		  map.put("mail_status",CommonConstants.STATUS_OK); 
		  return map; 
	  } catch (MessagingException ex) { 
		  log.error(ex.getMessage());
		  ex.printStackTrace(); 
		  map.put("mail_status",CommonConstants.STATUS_NO);
	  }  
	  return map; 
	} 
	
	/** 
	  * 以HTML格式发送邮件 
	  * @param mailInfo 待发送的邮件信息 
	 * @throws MessagingException 
	  */ 
	public static HashMap<String, Object> sendHtmlMail(MailSenderInfo mailInfo) throws Exception{ 
		HashMap<String, Object> map=new HashMap<String, Object>();	 
		map.put("mail_type", CommonConstants.MAIL_TYPE_SEND);
		map.put("mail_title", mailInfo.getSubject());
		map.put("mail_cnt", mailInfo.getContent());		
		map.put("mail_frommail", mailInfo.getFromAddress());
		map.put("mail_host", mailInfo.getMailServerHost());
		map.put("mail_protocol", mailInfo.getMailServerPort());
		map.put("mail_filepath", "");
		map.put("mail_staff", mailInfo.getToAddress());
	  // 判断是否需要身份认证 
	  MyAuthenticator authenticator = null;
	  Properties pro = mailInfo.getProperties();
	  //如果需要身份认证，则创建一个密码验证器  
	  if (mailInfo.isValidate()) { 
		authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
	  } 
	  // 根据邮件会话属性和密码验证器构造一个发送邮件的session 
	  Session sendMailSession = Session.getDefaultInstance(pro,authenticator); 
	
		  // 根据session创建一个邮件消息 
		  Message mailMessage = new MimeMessage(sendMailSession); 
		  // 创建邮件发送者地址 
		  Address from = new InternetAddress(mailInfo.getFromAddress()); 
		  // 设置邮件消息的发送者 
		  mailMessage.setFrom(from); 
		  // 创建邮件的接收者地址，并设置到邮件消息中 
		  Address to = new InternetAddress(mailInfo.getToAddress()); 
		  // Message.RecipientType.TO属性表示接收者的类型为TO 
		  mailMessage.setRecipient(Message.RecipientType.TO,to); 
		  // 设置邮件消息的主题 
		  mailMessage.setSubject(mailInfo.getSubject()); 
		  // 设置邮件消息发送的时间 
		  mailMessage.setSentDate(new Date()); 
		  // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象 
		  Multipart mainPart = new MimeMultipart(); 
		  // 创建一个包含HTML内容的MimeBodyPart 
		  BodyPart html = new MimeBodyPart(); 
		  // 设置HTML内容 
		  html.setContent(mailInfo.getContent(), "text/html; charset=utf-8"); 
		  mainPart.addBodyPart(html); 
		  // 将MiniMultipart对象设置为邮件内容 
		  mailMessage.setContent(mainPart); 
		  mailMessage.addHeader("X-Priority", "3");
		  mailMessage.addHeader("X-MSMail-Priority", "Normal");
		  mailMessage.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
		  mailMessage.addHeader("X-MimeOLE", "Produced By Microsoft MimeOLE V6.00.2900.2869");
		  mailMessage.addHeader("ReturnReceipt", "1");
		  
		  // 发送邮件 
		  try{
			  Transport.send(mailMessage);
			  map.put("mail_status",CommonConstants.STATUS_OK);	
			  
		  }catch(Exception e){
			  e.printStackTrace();
			  
		  }
		  
		  
		  return map; 
	  
	
	}
	
	public static void send(){
		MailSenderInfo info =new MailSenderInfo();
		info.setMailServerHost("smtp.mxhichina.com");
		info.setMailServerPort("25");
		info.setFromAddress("noreply@onehearts.net");
		info.setToAddress("380016693@qq.com");
		info.setUserName("noreply@onehearts.net");
		info.setPassword("HYsolution1234");
		info.setSubject("javamail");
		 
		String str="尊贵的英菲尼迪车主：<br><br>感谢您注册Owner Portal，目前您的资料已审核完毕，车库功能已为您开启，欢迎体验！<br><br>您可以通过以下方式进入Owner Portal<br><img  src='http://localhost:9988/infserver/data/ewm/ewm_ios.jpg'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img  src='http://localhost:9988/infserver/data/ewm/ewm_ios.jpg'>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<img  src='http://localhost:9988/infserver/data/ewm/ewm_ios.jpg'><br><br>东风英菲尼迪汽车有限公司<br><br>此邮件为系统发送，仅做告知无需回复";
		
		info.setContent(str);
		System.out.println(CommonUtils.toJson(info));
		try {
			SimpleMailSender.sendHtmlMail(info);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] str){
		send();
	}
} 