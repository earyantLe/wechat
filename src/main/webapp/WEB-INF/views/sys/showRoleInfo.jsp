<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/include/CommonTaglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>修改角色</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/easyui/themes/icon.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.exedit-3.5.js"></script>
	<script type="text/javascript">
	</script>
  </head>
  <body>
    <form id="updateUserroleInfoform" action="" method="post">
		<div id="tt" class="easyui-tabs" data-options="tools:[{   
        iconCls:'icon-save',
        handler:function(){   
       
           if($('#updateroleInfoform').form('validate')){
				$.ajax({
					type: 'post' ,
					url: '<%=request.getContextPath()%>/oper/userRole/addUserRoleInfo.do?roleid='+rid+'&userid='+uid ,
					cache:false ,
					data:$('#updateUserroleInfoform').serialize() ,
					dataType:'json' ,
					success:function(result){
						//1 关闭窗口
						$('#juesedd').dialog('close');
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
           $('#juesedd').dialog('close');
        } 
      }] , border:false,title:'保存'">
			<div title="分配角色" style="padding:5px">
			    <table class="table-edit" width="80%" align="center">
					<tr height="70">
						<td>选择角色:</td>
						<td>
							<ul id="treeDemo" class="ztree"></ul>
						</td>
					</tr>
					<script type="text/javascript">
					
					var setting = {
							view: {
								selectedMulti: false
							},
							check: {
								enable: true,
								chkStyle: "radio",
							},
							data: {
								simpleData: {
									enable: true
								}
							},
							callback: {
								onCheck: onCheck
							}
						};

						$(function(){
							uid='${id}';
							 $.ajax({
									url:'<%=request.getContextPath()%>/oper/role/getAllRoleInfo.do?uid='+uid,
									type:"POST",
									async:false,
									success:function(data){
					  		         $.fn.zTree.init($("#treeDemo"), setting, jQuery.parseJSON(data));
									}
								});
						});
						
						function onCheck(e, treeId, treeNode) {
							rid=treeNode.id;
						}		

	</script>
		</table>
	</div>
</div> 
   </form>
  </body>  