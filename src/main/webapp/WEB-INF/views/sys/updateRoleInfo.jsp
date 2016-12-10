<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/include/CommonTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>修改角色</title>
    <%@include file="/common/include/CommonJsCss.jsp" %>
    <script type="text/javascript">
    </script>
</head>
<body>
<form id="updateroleInfoform" action="" method="post">
    <div id="tt" class="easyui-tabs" data-options="tools:[{
        iconCls:'icon-save',
        handler:function(){   
       
           if($('#updateroleInfoform').form('validate')){
				$.ajax({
					type: 'post' ,
					url: '<%=request.getContextPath()%>/oper/role/updateRoleInfo.do?roleIds='+rids+'&priIds='+prids ,
					cache:false ,
					data:$('#updateroleInfoform').serialize() ,
					dataType:'json' ,
					success:function(result){
						//1 关闭窗口
						$('#xiugaidd').dialog('close');
						//2刷新datagrid 
						$('#t_roleInfo').datagrid('reload');
						
					} ,
					error:function(result){
						
					}
				});
			  }else{
				  return false;
			  }   
        }   
      },{
         iconCls:'icon-cancel',
        handler:function(){   
           $('#xiugaidd').dialog('close');
        } 
      }] , border:false,title:'保存'">
        <div title="修改角色" style="padding:5px">
            <table class="table-edit" width="80%" align="center">
                <tr>
                    <td width="200">角色名称:</td>
                    <td>
                        <input type="hidden" name="id" value="${role.id}"/>
                        <input type="text" name="rolename" value="${role.rolename}" class="easyui-validatebox"
                               data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>角色代码:</td>
                    <td><input type="text" name="rolecode" value="${role.rolecode}" class="easyui-validatebox"
                               data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>角色描述:</td>
                    <td><input type="text" value="${role.roledesc}" name="roledesc"/></td>
                </tr>
                <tr height="70">
                    <td>授权:</td>
                    <td>
                        <ul id="dTree" class="ztree"></ul>
                    </td>
                </tr>
                <script type="text/javascript">
                    var rid = '${role.id}';
                    var setting = {
                        check: {
                            enable: true
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback: {
                            onCheck: onClick
                        }
                    };
                    function onClick() {
                        var rarray = new Array();
                        var xmArray = new Array();
                        var treeObj = $.fn.zTree.getZTreeObj("dTree");
                        var nodes = treeObj.getCheckedNodes(true);

                        for (var i = 0; i < nodes.length; i++) {
                            if (nodes[i].level == 1) {
                                rarray.push(nodes[i].id);
                                xmArray.push(nodes[i].pId);
                            }

                        }
                        rids = xmArray.join(",");
                        prids = rarray.join(",");
                    }
                    $(function () {
                        $.ajax({
                            url: '<%=request.getContextPath()%>/oper/privilege/getAllPrivilegeInfoANDchecked.do',
                            type: "POST",
                            data: {"roleId": rid},
                            async: false,
                            success: function (data) {
                                $.fn.zTree.init($("#dTree"), setting, jQuery.parseJSON(data));
                            }
                        });
                        var rarray = new Array();
                        var xmArray = new Array();
                        var treeObj = $.fn.zTree.getZTreeObj("dTree");
                        var nodes = treeObj.getCheckedNodes(true);

                        for (var i = 0; i < nodes.length; i++) {
                            if (nodes[i].level == 1) {
                                rarray.push(nodes[i].id);
                                xmArray.push(nodes[i].pId);
                            }

                        }
                        rids = xmArray.join(",");
                        prids = rarray.join(",");
                    });

                </script>
            </table>
        </div>
    </div>
</form>
</body>
</html>
       