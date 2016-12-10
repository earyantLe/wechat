package com.earyant.common.mail;

import com.earyant.CommonConstants;
import com.earyant.common.utils.DateUtils;
import com.earyant.common.utils.PoiUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 根据数据集合 生成excel文档 作为邮件附件发送
 */
public class SpringMailSendExcel {

    public static final String FORMATYMDHMS = "yyyyMMddHHmmss";

    /**
     * 生成路径 生成文件 返回文件路径和文件名  文件中附加主键
     */
    public List<String[]> CreateExcel(ArrayList<ArrayList<String>> list, String excelType, String type, Map map) throws Exception {
        List<String[]> fileList = new ArrayList<String[]>();
        String path = CommonConstants.SYSTEMPATH + File.separator + CommonConstants.MAIL_PATH + File.separator + DateUtils.getCurrentYearMonthToString() + File.separator + type + File.separator;
        File file = new File(path);
        file.mkdirs();
        Random rd = new Random();
        String name = DateUtils.getCurrentDateStr(FORMATYMDHMS) + rd.nextInt();
        PoiUtils.writer(path, name, excelType, list, map);
        String[] tmparr = new String[2];
        tmparr[0] = name + "." + excelType;
        tmparr[1] = path + File.separator + name + "." + excelType;
        fileList.add(tmparr);
        return fileList;
    }
}
