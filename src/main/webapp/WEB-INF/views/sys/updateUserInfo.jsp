<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/common/include/CommonTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>商品管理_修改商品</title>
    <%@include file="/common/include/CommonJsCss.jsp" %>
    <script type="text/javascript">

    </script>
</head>
<body>
<form id="updateuserInfoform" action="" method="post">
    <div id="tt" class="easyui-tabs" data-options="tools:[{
        iconCls:'icon-save',
        handler:function(){   
           if($('#updateuserInfoform').form('validate')){
				$.ajax({
					type: 'post' ,
					url: '<%=request.getContextPath()%>/oper/user/updateUserInfo.do' ,
					cache:false ,
					data:$('#updateuserInfoform').serialize() ,
					dataType:'json' ,
					success:function(result){
						//1 关闭窗口
						$('#xiugaidd').dialog('close');
						//2刷新datagrid 
						$('#t_userInfo').datagrid('reload');
						
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
        <div title="修改用户" style="padding:5px">
            <table class="table-edit" width="80%" align="center">
                <input type="hidden" name="id" value="${user.id}"/>
                <tr>
                    <td width="200">姓名:</td>
                    <td>
                        <input type="text" name="username" class="easyui-validatebox" value="${user.username}"
                               data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>登录名称:</td>
                    <td><input type="text" name="loginname" class="easyui-validatebox" value="${user.loginname }"
                               data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>登录密码:</td>
                    <td>
                        <input type="password" name="loginpwd" class="easyui-validatebox" value="${user.loginpwd }"
                               data-options="required:true"/>
                        <input type="hidden" name="yuanmima" value="${user.loginpwd }"/>
                    </td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td>
                        <select name="sex" id="sex" style="width: 150px">
                            <option value="0" <c:if test="${user.sex==0}"> selected="selected"</c:if>>男</option>
                            <option value="1" <c:if test="${user.sex==1}"> selected="selected"</c:if>>女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>是否管理员</td>
                    <td>
                        <select id="isadmin" name="isadmin" style="width: 150px">
                            <option value="0" <c:if test="${user.isadmin==0}"> selected="selected"</c:if>>否</option>
                            <option value="1" <c:if test="${user.isadmin==1}"> selected="selected"</c:if>>是</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
       