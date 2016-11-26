package com.earyant.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by earyant on 2016/11/19.
 */
public class TestJson {


    public static void main(String[] args) throws Exception {

        String content = "2016-12-21";
        Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
        Matcher m = p.matcher(content);
        if (m.find()) {
//            Logger logger = LoggerFactory.getLogger(this.getClass());
            System.out.println("成功了");
        }



//        String s = "2015-05-18\"\n" +
//                "  ]\n" +
//                "}";
//        JsonStringUtils.jsonStringToObject(s, Day.class);
//        Gson gson = new Gson();
//        Day day = gson.fromJson(s, Day.class);
//        System.out.println(day.getString().get(0));

//        List<String> str =  new ArrayList<String>();
//        str.add("llll");
//        str.add("sss");
//        System.out.println(str.toString());
//        System.out.println(str.toArray());
//        System.out.println(str.());
//        System.out.println(str.toString());
    }


}
