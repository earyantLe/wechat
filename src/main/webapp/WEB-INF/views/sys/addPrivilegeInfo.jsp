<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>商品管理_商品列表</title>
    <%@include file="/common/include/CommonJsCss.jsp" %>
    <script type="text/javascript">
        $(function () {
            var pid = '${Pid}';
            var srcpath = '<%=request.getContextPath()%>/oper/privilege/toaddprivilegeInfoPage.do';
            $('#t_privilegeInfo').datagrid({
                idField: 'id',
                url: '<%=request.getContextPath()%>/oper/privilege/listPrivilegeInfo.do',
                rownumbers: true,
                striped: true,
                fit: true,
                checkOnSelect: false,
                queryParams: {id: pid},
                loadMsg: '正在加载...',
                frozenColumns: [[//冻结列特性 ,不要与fitColumns 特性一起使用
                    {
                        field: 'ck',
                        width: 50,
                        checkbox: true
                    }
                ]],
                columns: [[
                    {field: 'privilegename', title: '权限菜单', width: 320, align: 'center'},
                    {field: 'page', title: '权限URL', width: 170, align: 'center'},
                    {
                        field: 'ismenu', title: '是否菜单', width: 170, align: 'center',
                        formatter: function (value, row, index) {
                            var str = "";
                            if (row.ismenu == 0) {
                                str = "否";
                            }
                            if (row.ismenu == 1) {
                                str = "是";
                            }
                            return str;
                        }
                    },
                    {
                        field: 'isStop', title: '操作', width: 140, align: 'center',
                        formatter: function (value, row, index) {
                            var ss = "";
                            var editstr = '<a href="#" class="ico2 icon-edit" onclick="xiugai(' + row.id + ')" title="编辑"></a>&nbsp;&nbsp;';
                            ss = editstr;
                            return ss;
                        }
                    }
                ]],
                toolbar: [
                    {
                        text: '新增',
                        iconCls: 'icon-add',
                        handler: function () {
                            $('#privilegeInfoform').get(0).reset();
                            $("#dd").dialog("open");

                        }

                    }, {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () {
                            var arr = $('#t_privilegeInfo').datagrid('getChecked');
                            if (arr.length <= 0) {
                                $.messager.alert('提示', '请选择一条记录进行删除');

                            } else {
                                $.messager.confirm('提示信息', '确认删除?', function (r) {
                                    if (r) {
                                        var ids = '';
                                        for (var i = 0; i < arr.length; i++) {
                                            ids += arr[i].id + ',';
                                        }
                                        ids = ids.substring(0, ids.length - 1);
                                        $.post('<%=request.getContextPath()%>/oper/privilege/deleteChildPrivilegeInfo.do', {ids: ids}, function (result) {
                                            //1 刷新数据表格
                                            $('#t_privilegeInfo').datagrid('reload');
                                            //2 清空idField
                                            $('#t_privilegeInfo').datagrid('unselectAll');
                                            //3 给提示信息
                                            $.messager.show({
                                                title: '提示信息!',
                                                msg: '删除成功!',
                                                timeout: 70
                                            });
                                        });
                                    } else {
                                        return;
                                    }
                                });
                            }
                        }
                    }
                ]
            });

            //保存权限
            $("#saveprivilege").click(function () {
                if ($('#privilegeInfoform').form('validate')) {
                    $.ajax({
                        type: 'post',
                        url: '<%=request.getContextPath()%>/oper/privilege/addPrivilegeInfo.do?id=' + new Date().getTime() + '&pId=' + pid,
                        cache: false,
                        data: $('#privilegeInfoform').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            //1 关闭窗口
                            $('#dd').dialog('close');
                            //2刷新datagrid
                            $('#t_privilegeInfo').datagrid('reload');

                        },
                        error: function (result) {

                        }
                    });
                } else {
                    return false;
                }
            });
            $("#cancel").click(function () {
                $('#dd').dialog('close');
            });


        });
        //js方法：序列化表单
        function serializeForm(form) {
            var obj = {};
            $.each(form.serializeArray(), function (index) {
                if (obj[this['name']]) {
                    obj[this['name']] = obj[this['name']] + ',' + this['value'];
                } else {
                    obj[this['name']] = this['value'];
                }
            });
            return obj;
        }

        //修改
        function xiugai(id) {
            $('#xiugaidd').dialog({
                maximizable: true,
                title: '',
                width: 500,
                height: 200,
                modal: true,
                draggable: false,
                href: '<%=request.getContextPath()%>/oper/privilege/toupdateprivilegeInfoPage.do?id=' + id

            });
        }
    </script>
</head>
<body>
<div id="lay" class="easyui-layout" style="width: 100%;height:100%" fit=true>
    <div region="center">
        <table id="t_privilegeInfo"></table>
    </div>
</div>
<div id="dd" style="height:auto;width: auto;" data-options="title:'',maximizable:true,modal: true,draggable:false"
     class="easyui-dialog" closed=true>
    <form id="privilegeInfoform">
        <div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools', border:false"
             style="height:200px;width:500px">
            <div title="新增权限" style="padding:5px">
                <table class="table-edit" align="center">
                    <tr>
                        <td width="200">权限菜单名称:</td>
                        <td>
                            <input type="text" name="privilegename" class="easyui-validatebox" value=""
                                   data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>权限URL:</td>
                        <td><input type="text" name="page" class="easyui-validatebox" value=""
                                   data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>权限代码:</td>
                        <td><input type="text" name="privilegecode" class="easyui-validatebox" value=""
                                   data-options="required:true"/></td>
                    </tr>

                    <tr>
                        <td>是否菜单:</td>
                        <td>
                            <select name="ismenu" id="ismenu" style="width: 150px">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form>
    <div id="tab-tools">
        <a type="submit" id="saveprivilege" class="easyui-linkbutton" title="保存"
           data-options="plain:true,iconCls:'icon-save'"></a>
        <a type="submit" id="cancel" class="easyui-linkbutton" title="关闭"
           data-options="plain:true,iconCls:'icon-cancel'"></a>
    </div>
</div>
<div id="win"></div>
<!-- 修改弹出框 -->
<div id="xiugaidd"></div>

</body>
</html>
       