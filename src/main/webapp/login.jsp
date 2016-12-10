<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/icon.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/common/style/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/style/css/style_grey.css"/>

    <style>
        input[type=text] {
            width: 80%;
            height: 25px;
            font-size: 12pt;
            font-weight: bold;
            margin-left: 45px;
            padding: 3px;
            border-width: 0;
        }

        input[type=password] {
            width: 80%;
            height: 25px;
            font-size: 12pt;
            font-weight: bold;
            margin-left: 45px;
            padding: 3px;
            border-width: 0;
        }

        #loginformcodeInput {
            margin-left: 1px;
            margin-top: 1px;
        }

        #loginformvCode {
            margin: 0px 0 0 60px;
            height: 34px;
        }
    </style>
    <script type="text/javascript">
        if (window.self != window.top) {
            window.top.location = window.location;
        }


        $(function () {
            var flag = '${flag}';
            if (flag == '1') {
                $("#codeerror").text("验证码输入错误");
            }
            if (flag == "2") {
                $("#nameerror").text("用户名输入错误");
            }
            if (flag == "3") {
                $("#pwderror").text("密码输入错误");
            }
            $("#loginformBtn").click(function () {
                var loginName = $.trim($("#loginformidInput").val());
                var loginPwD = $.trim($("#loginformpwdInput").val());
                var loginCode = $.trim($("#loginformcodeInput").val());
                if (loginName == "") {
                    alert("用户名不能为空");
                    return;
                }
                if (loginPwD == "") {
                    alert("密码不能为空");
                    return;
                }
                if (loginCode == "") {
                    alert("验证码不能为空");
                    return;
                }
                $('#loginform').submit();
            });

        });
    </script>
</head>
<body>
<div
        style="width: 900px; height: 50px; position: absolute; text-align: left; left: 70%; top: 50%; margin-left: -450px;; margin-top: -280px;">
    <center><span style="float:left; font-size:38px; margin-top: 20px; color: #488ED5;">电信框架DEMO平台</span></center>
    <br/>
</div>
<div class="main-inner" id="mainCnt"
     style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px; background-image: url('<%=request.getContextPath()%>/common/style/images/bg_login.jpg')">
    <div id="loginBlock" class="login"
         style="margin-top: 80px; height: 255px;">
        <div class="loginFunc">
            <div id="lbNormal" class="loginFuncMobile">用户登录</div>
        </div>
        <div class="loginForm">
            <form id="loginform" name="loginform" method="post"
                  action="<%=request.getContextPath()%>/oper/user/validationUser.do">
                <div style="margin-left: 42px;color: red" id="codeerror"></div>
                <div style="margin-left: 42px;color: red" id="nameerror"></div>
                <div style="margin-left: 42px;color: red" id="pwderror"></div>
                <div id="idInputLine" class="loginFormIpt showPlaceholder"
                     style="margin-top: 5px;">
                    <input type="text" id="loginformidInput" name="loginname" class="easyui-validatebox"
                           maxlength="50"/>
                    <label for="idInput" class="placeholder" id="idPlaceholder">帐号：</label>
                </div>
                <div class="forgetPwdLine"></div>
                <div id="pwdInputLine" class="loginFormIpt showPlaceholder">
                    <input type="password" id="loginformpwdInput" class="easyui-validatebox" name="loginpwd"/>
                    <label for="pwdInput" class="placeholder" id="pwdPlaceholder">密码：</label>
                </div>
                <div class="loginFormIpt loginFormIptWiotTh"
                     style="margin-top:58px;">
                    <div id="codeInputLine" class="loginFormIpt showPlaceholder"
                         style="margin-left:0px;margin-top:-40px;width:50px;">
                        <input id="loginformcodeInput" class="loginFormTdIpt easyui-validatebox" type="text"
                               name="checkcode" title="请输入验证码"/>
                        <img id="loginformvCode" style="cursor: pointer;"
                             src="<%=request.getContextPath()%>/validatecode.jsp"
                             onclick="javascript:document.getElementById('loginformvCode').src='<%=request.getContextPath()%>/validatecode.jsp?time='+Math.random();"/>
                    </div>
                    <a href="javascript:void(0)" id="loginformBtn" name="loginformj_id19">
						<span
                                id="loginformloginBtn" class="btn btn-login"
                                style="margin-top:-36px;">登录</span>
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
<div
        style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: 220px;">
    <br/>
    <center>
        <span style="color: #488ED5;font-size: 16px">Powered By ctsig</span>
    </center>
</div>
</body>
</html>