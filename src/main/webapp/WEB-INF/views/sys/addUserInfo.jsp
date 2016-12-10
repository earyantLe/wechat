<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <!-- 导入jquery核心类库 -->
    <%@include file="/common/include/CommonJsCss.jsp" %>
    <script type="text/javascript">
        $(function () {
            // 点击保存
            $('#save').click(function () {
                // 校验form
                if ($("#functionForm").form('validate')) {
                    // 提交form
                    $("#functionForm").submit();
                }
            });
        });
    </script>
</head>
<body class="easyui-layout" data-options="fit:true">
<div data-options="region:'center'">
    <form id="functionForm" method="post" action="">
        <table class="table-edit" width="80%" align="center">
            <tr class="title">
                <td colspan="2">功能权限信息</td>
            </tr>
            <tr>
                <td width="200">编号</td>
                <td>
                    <input type="text" name="id" class="easyui-validatebox" data-options="required:true"/>
                </td>
            </tr>
            <tr>
                <td>名称</td>
                <td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>关键字</td>
                <td><input type="text" name="code" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>访问路径</td>
                <td><input type="text" name="page"/></td>
            </tr>
            <tr>
                <td>是否生成菜单</td>
                <td>
                    <select name="generatemenu" class="easyui-combobox">
                        <option value="0">不生成</option>
                        <option value="1">生成</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>描述</td>
                <td>
                    <textarea name="description" rows="4" cols="60"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>