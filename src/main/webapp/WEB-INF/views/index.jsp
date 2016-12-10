<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>電信框架管理平台</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/style/css/default.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
    <!-- 导入ztree类库 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/zTreeStyle.css"
          type="text/css"/>
    <script src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.all-3.5.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            var setting = {
                view: {
                    showLine: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: function (event, treeId, treeNode, clickFlag) {
                        if (treeNode.page != undefined && treeNode.page != "") {
                            if ($("#tt").tabs('exists', treeNode.name)) {// 判断tab是否存在
                                $('#tt').tabs('select', treeNode.name); // 切换tab
                            } else {
                                // 开启一个新的tab页面
                                var content = '<div style="width:100%;height:100%;overflow:hidden;">'
                                    + '<iframe src="'
                                    + treeNode.page
                                    + '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

                                $('#tt').tabs('add', {
                                    title: treeNode.name,
                                    content: '<iframe frameborder=0 style=width:100%;height:100% src=' + treeNode.page + ' ></iframe>',
                                    closable: true
                                });
                            }
                        }
                    }
                }
            };


            //普通用户
            var normalNodes = [
                {
                    id: 2,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/businessAffairsVip/toViplist.do',
                    name: "普通用户菜单1",
                    icon: '<%=request.getContextPath()%>/ztree/css/zTreeStyle/img/diy/11.png'
                },
                {
                    id: 4,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/businessAffairsVip/topwdReset.do',
                    name: "普通用户菜单2",
                    icon: '<%=request.getContextPath()%>/ztree/css/zTreeStyle/img/diy/11.png'
                },
                {
                    id: 6,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/businessAffairsVip/toVipAuthentication.do',
                    name: "普通用户菜单3",
                    icon: '<%=request.getContextPath()%>/ztree/css/zTreeStyle/img/diy/11.png'
                }
            ];
            //有部分权限用户
            var permissionNodes = [
                    <shiro:hasPermission name="permission">{
                    id: 2,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/user/topermissionInfo.do',
                    name: "有权限用户菜单1",
                    icon: '<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/img/diy/11.png'
                }, </shiro:hasPermission>
                {
                    id: 3,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/user/topermissionInfo1.do',
                    name: "有权限用户菜单2",
                    icon: '<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/img/diy/11.png'
                }
            ];
            //有用户管理权限
            var userzNodes = [
                {
                    id: 2,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/user/tolistUserInfo.do',
                    name: "用户列表",
                    icon: '<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/img/diy/11.png'
                },
                {
                    id: 3,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/privilege/toprivilegeInfo.do',
                    name: "权限管理",
                    icon: '<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/img/diy/11.png'
                },
                {
                    id: 6,
                    pId: 1,
                    page: '<%=request.getContextPath()%>/oper/role/toListRoleInfo.do',
                    name: "角色管理",
                    icon: '<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/img/diy/11.png'
                }
            ];

            $(document).ready(function () {
                $.fn.zTree.init($("#userTreeMenu"), setting, userzNodes);
                $.fn.zTree.init($("#permissionTreeMenu"), setting, permissionNodes);
                $.fn.zTree.init($("#normalTreeMenu"), setting, normalNodes);
            });
        });

    </script>
</head>
<body>
<div id="cc" class="easyui-layout" fit=true style="width:100%;height:100%;">
    <div data-options="region:'north',border:false"
         style="height:80px;background:url('<%=request.getContextPath()%>/common/style/images/bg_top.jpg') no-repeat right;">
        <div>
            <img src="<%=request.getContextPath()%>/common/style/images/telecom.jpg"
                 style="width:120px;height:80px;padding:0px;margin-left:10px;vertical-align:middle;"/>
            <span style="font-size:25px; color:#666666 ;font-weight: bold;margin-left: 10px">电信框架DEMO平台</span>
        </div>
        <div id="sessionInfoDiv"
             style="position: absolute;right: 5px;top:10px;">
            [${ctrlUser.loginname }]，
            欢迎您，登录！
        </div>
        <div style="position: absolute; right: 5px; bottom: 10px; ">
            <a href="javascript:void(0);" class="easyui-menubutton"
               data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-lock'">控制面板</a>
        </div>
        <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
            <div onclick="editPassword();">修改密码</div>
            <div onclick="showAbout();">联系管理员</div>
            <div class="menu-sep"></div>
            <div onclick="javascript:location.href='<%=request.getContextPath()%>/oper/user/logout.do'">退出系统</div>
        </div>
    </div>
    <!--
    <div region="south" title="South Title" split="true" style="height:100px;"></div>
    <div region="east" collapsed=true iconCls="icon-reload" title="East" split="true" style="width:100px;"></div>
     -->
    <div region="west" data-options="iconCls:'icon-tip',region:'west',split:true,collapsible:false,title:'菜单导航'"
         style="width:200px;">
        <div id="aa" class="easyui-accordion" fit=true>
            <div title="普通用户" data-options="iconCls:'icon-man'">
                <ul id="normalTreeMenu" class="ztree"></ul>
            </div>
            <shiro:hasAnyRoles name="permission,admin">
                <div title="有权限展示" data-options="iconCls:'icon-man'">
                    <ul id="permissionTreeMenu" class="ztree"></ul>
                </div>
            </shiro:hasAnyRoles>
            <div title="用户管理" data-options="iconCls:'icon-man'">
                <ul id="userTreeMenu" class="ztree"></ul>
            </div>
        </div>
    </div>

    <div data-options="region:'center'">
        <div id="tt" fit="true" class="easyui-tabs" border="false">
            <div title="系统公告" id="subWarp"
                 style="width:100%;height:100%;overflow:hidden">
                <iframe src="<%=request.getContextPath()%>/oper/user/toHome.do"
                        style="width:100%;height:100%;border:0;"></iframe>
                <%--				这里显示公告栏、预警信息和代办事宜--%>
            </div>
        </div>
    </div>
    <div data-options="region:'south'" style="height:50px;">
        <center style="padding-top: 10px">版权所有</center>
    </div>
</div>

</body>
</html>