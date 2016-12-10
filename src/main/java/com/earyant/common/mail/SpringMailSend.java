package com.earyant.common.mail;

import com.earyant.CommonConstants;
import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.*;
import java.util.*;


/**
 * 发送邮件 公共类
 */
public class SpringMailSend {
    private static final Logger log = Logger.getLogger(SpringMailSend.class);

    /**
     * 发送邮件 多个用户名用；区分 附件需要文件名和路径
     */
    public static HashMap<String, Object> sendMimeMessage(SpringMailParam springMailParam) {

        //创建类 把信息赋值
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        senderImpl.setHost(springMailParam.getMailServerHost());
        senderImpl.setPort(springMailParam.getMailServerPort());
        senderImpl.setUsername(springMailParam.getUserName());
        senderImpl.setPassword(springMailParam.getPassword());

        //是否进行用户名密码验证
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", springMailParam.isValidate());
        prop.put("mail.smtp.host", springMailParam.getMailServerHost());
        senderImpl.setJavaMailProperties(prop);

        //处理日志
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("mail_type", CommonConstants.MAIL_TYPE_SEND);
        map.put("mail_title", springMailParam.getSubject());
        map.put("mail_cnt", springMailParam.getContent());
        map.put("mail_frommail", springMailParam.getFromAddress());
        map.put("mail_host", springMailParam.getMailServerHost());
        map.put("mail_staff", springMailParam.getToAddress() + springMailParam.getCc());

        List<String[]> fileList = springMailParam.getFileList();
        //判断是否存在附件
        if (null == fileList || fileList.size() == 0) {
            try {
                sendTextMail(senderImpl, springMailParam);
                map.put("mail_status", CommonConstants.STATUS_OK);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                map.put("mail_status", CommonConstants.STATUS_NO);
            }
        } else {
            try {
                sendMimeMessage(senderImpl, springMailParam);
                map.put("mail_filepath", fileList.get(0)[0] + fileList.get(0)[1]);
                map.put("mail_status", CommonConstants.STATUS_OK);
            } catch (Exception e) {
                log.error(e.getMessage());
                e.printStackTrace();
                map.put("mail_status", CommonConstants.STATUS_NO);
            }
        }
        return map;
    }

    /**
     * 发送文本邮件
     */
    private static void sendTextMail(JavaMailSender sender, SpringMailParam springMailParam) throws Exception {
        SimpleMailMessage mail = new SimpleMailMessage();
        String[] arrarr = springMailParam.getToAddress().split(";");
        mail.setTo(arrarr);
        mail.setFrom(springMailParam.getFromAddress());
        mail.setSubject(springMailParam.getSubject());
        mail.setText(springMailParam.getContent());
        mail.setCc(springMailParam.getCc().split(";"));
        sender.send(mail);
    }

    /**
     * 发送带附件的邮件
     */
    private static void sendMimeMessage(final JavaMailSender sender, final SpringMailParam springMailParam) throws Exception {
        MimeMessagePreparator mimeMail = new MimeMessagePreparator() {
            @SuppressWarnings("unchecked")
            public void prepare(MimeMessage mimeMessage) throws Exception {
                //区分多个地址
                String[] arrarr = springMailParam.getToAddress().split(";");
                InternetAddress[] addr = new InternetAddress[arrarr.length];

                for (int i = 0; i < arrarr.length; i++) {
                    addr[i] = new InternetAddress(arrarr[i]);
                }

                mimeMessage.setRecipients(Message.RecipientType.TO, addr);
                if (springMailParam.getCc() != null) {
                    String[] arcc = springMailParam.getCc().split(";");
                    InternetAddress[] addrcc = new InternetAddress[arcc.length];
                    for (int i = 0; i < arcc.length; i++) {
                        addrcc[i] = new InternetAddress(arcc[i]);
                    }
                    mimeMessage.setRecipients(Message.RecipientType.CC, addrcc);
                }
                mimeMessage.setFrom(new InternetAddress(springMailParam.getFromAddress()));
                mimeMessage.setSubject(springMailParam.getSubject(), "gb2312");
                Multipart mp = new MimeMultipart();
                // 向Multipart添加正文
                MimeBodyPart content = new MimeBodyPart();
                content.setText(springMailParam.getContent());
                // 向MimeMessage添加（Multipart代表正文）
                mp.addBodyPart(content);
                //向Multipart添加附件
                Iterator it = springMailParam.getFileList().iterator();
                while (it.hasNext()) {
                    MimeBodyPart attachFile = new MimeBodyPart();
                    String[] arr = (String[]) it.next();
                    FileDataSource fds = new FileDataSource(arr[1]);//rid
                    attachFile.setDataHandler(new DataHandler(fds));
                    attachFile.setFileName(MimeUtility.encodeText(arr[0], "gb2312", "B"));
                    mp.addBodyPart(attachFile);
                }
                // 向Multipart添加MimeMessage
                mimeMessage.setContent(mp);
                mimeMessage.setSentDate(new Date());
            }
        };
        // 发送带附件的邮件
        sender.send(mimeMail);
    }

    @SuppressWarnings("unchecked")
    public static void test() {
        SpringMailParam info = new SpringMailParam();
        info.setMailServerHost("ismtp.sdcncsi.com.cn");
        info.setMailServerPort(25);
        info.setFromAddress("sd-wangxinshuai@sdcncsi.com.cn");
        info.setToAddress("wangxinshuai@cnview.com.cn");
        info.setUserName("sd-wangxinshuai@sdcncsi.com.cn");
        info.setPassword("sdun/123");
        info.setSubject("javamail");
        info.setContent("测试java买了");
        info.setCc("wangxinshuai@cnview.com.cn");
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        ArrayList<String> list1 = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        list1.add("第一列");
        list1.add("第二列");
        list1.add("第三列");
        list2.add("aaa111");
        list2.add("bbb222");
        list2.add("ccc333");
        list.add(list1);
        list.add(list2);
        SpringMailSendExcel s = new SpringMailSendExcel();
        List fileList1 = null;
        try {
            fileList1 = s.CreateExcel(list, "xls", "", null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        info.setFileList(fileList1);
        SpringMailSend.sendMimeMessage(info);
    }

    public static void main(String[] str) {
        test();
    }
}
