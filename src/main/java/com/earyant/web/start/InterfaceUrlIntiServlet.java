package com.earyant.web.start;

import com.earyant.sys.wechat.service.impl.InterfaceUrlInti;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * ClassName: InterfaceUrlIntiServlet
 *
 * @author dapengniao
 * @Description: 项目启动初始化servlet
 * @date 2016年3月10日 下午4:08:43
 */
public class InterfaceUrlIntiServlet extends HttpServlet {

    //    @Resource
//    InterfaceUrlInti interfaceUrlIntiService;
    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        InterfaceUrlInti.initDate();
    }

}
