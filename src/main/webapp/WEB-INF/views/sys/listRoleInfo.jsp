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
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

    </script>
    <script type="text/javascript">
        $(function () {
            $('#t_roleInfo').datagrid({
                idField: 'id',
                url: '<%=request.getContextPath()%>/oper/role/listroleInfo.do',
                rownumbers: true,
                striped: true,
                fit: true,
                checkOnSelect: false,
                loadMsg: '正在加载...',
                frozenColumns: [[//冻结列特性 ,不要与fitColumns 特性一起使用
                    {
                        field: 'ck',
                        width: 50,
                        checkbox: true
                    }
                ]],
                columns: [[
                    {field: 'rolename', title: '角色名称', width: 320, align: 'center'},
                    {field: 'rolecode', title: '角色代码', width: 170, align: 'center'},
                    {field: 'roledesc', title: '角色描述', width: 290, align: 'center'},
                    {
                        field: 'isStop', title: '操作', width: 300, align: 'center',
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
                            $.ajax({
                                url: '<%=request.getContextPath()%>/oper/privilege/getAllPrivilegeInfo.do',
                                type: "POST",
                                async: false,
                                success: function (data) {
                                    $.fn.zTree.init($("#functionTree"), setting, jQuery.parseJSON(data));
                                }
                            });
                            $('#roleInfoform').get(0).reset();
                            $("#dd").dialog("open");

                        }

                    }, {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () {
                            var arr = $('#t_roleInfo').datagrid('getChecked');
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
                                        $.post('<%=request.getContextPath()%>/oper/role/deleteRoleInfo.do', {ids: ids}, function (result) {
                                            //1 刷新数据表格
                                            $('#t_roleInfo').datagrid('reload');
                                            //2 清空idField
                                            $('#t_roleInfo').datagrid('unselectAll');
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
                    }, {
                        text: '查询',
                        iconCls: 'icon-search',
                        handler: function () {
                            $('#lay').layout('expand', 'north');
                        }
                    }
                ]
            });

            //保存商品
            $("#saverole").click(function () {
                if ($('#roleInfoform').form('validate')) {
                    var rarray = new Array();
                    var xmArray = new Array();
                    var treeObj = $.fn.zTree.getZTreeObj("functionTree");
                    var nodes = treeObj.getCheckedNodes(true);

                    for (var i = 0; i < nodes.length; i++) {
                        if (nodes[i].level == 1) {
                            rarray.push(nodes[i].id);
                            xmArray.push(nodes[i].pId);
                        }

                    }
                    $.ajax({
                        type: 'post',
                        url: '<%=request.getContextPath()%>/oper/role/addRoleInfo.do?roleIds=' + xmArray.join(",") + '&priIds=' + rarray.join(","),
                        cache: false,
                        data: $('#roleInfoform').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            //1 关闭窗口
                            $('#dd').dialog('close');
                            //2刷新datagrid
                            $('#t_roleInfo').datagrid('reload');

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
            //查询，清空
            $('#searchbtn').click(function () {
                $('#t_roleInfo').datagrid('load', serializeForm($('#mysearch')));
            });
            $('#clearbtn').click(function () {
                $('#mysearch').form('clear');
                $('#t_roleInfo').datagrid('load', {});
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
                width: 1100,
                height: 400,
                modal: true,
                draggable: false,
                href: '<%=request.getContextPath()%>/oper/role/toupdateroleInfoPage.do?id=' + id

            });
        }
    </script>
</head>
<body>
<div id="lay" class="easyui-layout" style="width: 100%;height:100%" fit=true>
    <div region="north" title="商品查询" collapsed=true class="easyui-validatebox" style="height:60px;">
        <form id="mysearch" method="post">
            姓名:&nbsp;&nbsp;&nbsp;<input id="rolename" name="rolename" type="text" value=""/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            是否管理员:<select id="isadmin" class="easyui-combobox" name="isadmin" style="width:200px;">
            <option value="2">全部</option>
            <option value="0">否</option>
            <option value="1">是</option>
        </select> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            登录姓名:&nbsp;&nbsp;&nbsp;<input id="loginname" name="loginname" type="text" value=""/>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a id="searchbtn" class="easyui-linkbutton">查询</a> <a id="clearbtn" class="easyui-linkbutton">清空</a>
        </form>

    </div>
    <div region="center">
        <table id="t_roleInfo"></table>
    </div>
</div>
<div id="dd" style="height:auto;"
     data-options="title:'',width: 1100,height:300,maximizable:true,modal: true,draggable:false" class="easyui-dialog"
     closed=true>
    <form id="roleInfoform">
        <div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools', border:false" style="height:390px;">
            <div title="新增角色" style="padding:5px">
                <table class="table-edit" width="80%" align="center">
                    <tr>
                        <td width="200">角色名称:</td>
                        <td>
                            <input type="text" name="rolename" class="easyui-validatebox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>角色代码:</td>
                        <td><input type="text" name="rolecode" class="easyui-validatebox" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>角色描述:</td>
                        <td><input type="text" name="roledesc"/></td>
                    </tr>
                    <tr height="70">
                        <td>授权:</td>
                        <td>
                            <ul id="functionTree" class="ztree"></ul>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </form>
    <div id="tab-tools">
        <a type="submit" id="saverole" class="easyui-linkbutton" title="保存"
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
       