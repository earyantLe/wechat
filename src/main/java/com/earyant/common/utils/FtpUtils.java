package com.earyant.common.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;

public class FtpUtils {

    private static FTPClient ftp;

    /**
     * @param path     上传到ftp服务器哪个路径下
     * @param addr     地址
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    private static boolean connect(String path, String addr, int port, String username, String password) throws Exception {
        boolean result = false;
        ftp = new FTPClient();
        int reply;
        ftp.connect(addr, port);
        ftp.login(username, password);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return result;
        }
        ftp.changeWorkingDirectory(path);
        ftp.enterLocalPassiveMode();
        result = true;
        return result;
    }

    /**
     * @param file 上传的文件或文件夹
     * @throws Exception
     */
    private static Boolean upload(File file) throws Exception {
        Boolean boo = false;
        if (file.isDirectory()) {
            ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath() + "\\" + files[i]);
                if (file1.isDirectory()) {
                    upload(file1);
                    boo = ftp.changeToParentDirectory();
                    return boo;
                } else {
                    File file2 = new File(file.getPath() + "\\" + files[i]);
                    FileInputStream input = new FileInputStream(file2);
                    boo = ftp.storeFile(new String(file2.getName().getBytes("UTF-8"), "iso-8859-1"), input);
                    input.close();
                    return boo;
                }
            }
        } else {
            File file2 = new File(file.getPath());
            FileInputStream input = new FileInputStream(file2);
            boo = ftp.storeFile(new String(file2.getName().getBytes("UTF-8"), "iso-8859-1"), input);
            input.close();
            return boo;
        }
        return boo;
    }

    public static Boolean uploadFtp(File file) throws Exception {

        Boolean boo = connect(CommonUtils.getFtpString("ftpPath"), CommonUtils.getFtpString("ftpIp"), Integer.valueOf(CommonUtils.getFtpString("ftpPort")), CommonUtils.getFtpString("ftpUserName"), CommonUtils.getFtpString("ftpPassword"));
        if (boo) {
            return upload(file);
        } else {
            return false;
        }

    }
}  
