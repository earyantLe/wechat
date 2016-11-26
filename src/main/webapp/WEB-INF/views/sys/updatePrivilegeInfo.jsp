<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/include/CommonTaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>商品管理_修改商品</title>
    <%@include file="/common/include/CommonJsCss.jsp"%>
	<script type="text/javascript">
	
	</script>
  </head>
  <body>
    <form id="updateprivilegeInfoform" action="" method="post">
		<div id="tt" class="easyui-tabs" data-options="tools:[{   
        iconCls:'icon-save',
        handler:function(){   
           if($('#updateprivilegeInfoform').form('validate')){
				$.ajax({
					type: 'post' ,
					url: '<%=request.getContextPath()%>/oper/privilege/updatePrivilegeInfo.do' ,
					cache:false ,
					data:$('#updateprivilegeInfoform').serialize() ,
					dataType:'json' ,
					success:function(result){
						//1 关闭窗口
						$('#xiugaidd').dialog('close');
						//2刷新datagrid 
						$('#t_privilegeInfo').datagrid('reload');
						
					} ,
					error:function(result){
						
					}
				});
			  }else{
				  return false;
			  }   
        }   
      }] , border:false,title:'保存'">
			<div title="修改用户" style="padding:5px">
			    <table class="table-edit" width="80%" align="center">
			       <input type="hidden" name="id"  value="${pri.id}"/>
			       <input type="hidden" name="pId"  value="${pri.pId}"/>
					<tr>
						<td width="200">权限菜单:</td>
						<td>
							<input type="text" name="privilegename" class="easyui-validatebox" value="${pri.privilegename}" data-options="required:true" />						
						</td>
					</tr>
					<tr>
						<td>权限URL:</td>
						<td><input type="text" name="page" class="easyui-validatebox" value="${pri.page }"data-options="required:true" /></td>
					</tr>
					<tr>
						<td>权限代码:</td>
						<td><input type="text" name="privilegecode" class="easyui-validatebox" value="${pri.privilegecode}"data-options="required:true" /></td>
					</tr>
					
					<tr>
						<td>是否菜单:</td>
						<td>
						 <select name="ismenu" id="ismenu"  style="width: 150px">
								<option value="0" <c:if test="${pri.ismenu==0}"> selected="selected"</c:if>>否</option>
								<option value="1" <c:if test="${pri.ismenu==1}"> selected="selected"</c:if>>是</option>
							</select>
						</td>
					</tr>
					</table>
			</div>
		</div> 
   </form>
  </body>
</html>
       