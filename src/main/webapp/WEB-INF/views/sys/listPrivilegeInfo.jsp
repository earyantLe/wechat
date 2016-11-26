<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
 <HEAD>
  <TITLE> ZTREE DEMO </TITLE>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/common/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
  <style>
	body {
	background-color: white;
	margin:0; padding:0;
	text-align: center;
	}
	div, p, table, th, td {
		list-style:none;
		margin:0; padding:0;
		color:#333; font-size:12px;
		font-family:dotum, Verdana, Arial, Helvetica, AppleGothic, sans-serif;
	}
	#testIframe {margin-left: 10px;}
  </style>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<SCRIPT type="text/javascript">
		
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
				
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename,
				onClick:onClick
			}
		};

	 	$(function(){
			$.ajax({
				url:'<%=request.getContextPath()%>/oper/privilege/getPrivilegeInfo.do',
				type:"POST",
				async:false,
				success:function(data){
    		     $.fn.zTree.init($("#treeDemo"), setting,jQuery.parseJSON(data));
				}
			});
		}); 
		
		var log, className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			if(confirm("确认删除 节点 -- " + treeNode.name + " 吗？")){
				 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				 var nodes = zTree.getSelectedNodes();
				 if(nodes[0]){
				 var nodeId=nodes[0].id;
				//ajax调用删除节点
					  $.ajax({
							url:'<%=request.getContextPath()%>/oper/privilege/deletePrivilegeInfo.do',
							type:"POST",
							async:false,
							data:{id:nodeId},
							success:function(data){
								window.frames[0].location.reload(true);
							}
						});
				 }
			  }else{
				  return false;
			  }
		}
		function onRemove(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}else{
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				var nodes = zTree.getSelectedNodes();
				if(nodes[0]){
					var nodeFlag=nodes[0].level;
					var nodeId=nodes[0].id;
						$.ajax({
							url:'<%=request.getContextPath()%>/oper/privilege/updateprivilegeInfo.do',
							type:"POST",
							async:false,
							data:{id:nodeId,privilegename:newName,ismenu:'1'},
							success:function(data){
								window.frames[0].location.reload(true);
							}
						});
						 
				}
			}
			
		}
		function onRename(e, treeId, treeNode, isCancel) {
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		function showRemoveBtn(treeId, treeNode) {
			return treeNode.level==1;
		}
		function showRenameBtn(treeId, treeNode) {
			return treeNode.level==1||treeNode.level==2;
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var fg= treeNode.level==0;
			if(fg){
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='add node' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#addBtn_"+treeNode.tId);
				if (btn) btn.bind("click", function(){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var sTime=new Date().getTime();
					var namec="菜单"+sTime;
					$.ajax({
						url:'<%=request.getContextPath()%>/oper/privilege/addPrivilegeInfo.do',
						type:"POST",
						async:false,
						data:{id:sTime,privilegename:namec,pId:'1',ismenu:'0'},
						dataType:'json',
						success:function(data){
							zTree.addNodes(treeNode, {id:sTime, pId:treeNode.id, name:namec});
						},
						error:function(){
							alert('添加失败');
						}
					});
				});
			}else{
				return false;
			}
			
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		function onClick(treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			if(nodes[0]){
			var nodeFlag=nodes[0].level;
			var pflag=nodes[0].pId;
			var nodeId=nodes[0].id;
			//点击时候判断 传不同的参数
			
			if(nodeFlag==1){
				$("#Iframe").attr("src","<%=request.getContextPath()%>/oper/privilege/toAddprivilegeInfo.do?id="+nodeId);
			}
		}
		}
		$(document).ready(function(){
			
			$("#selectAll").bind("click", selectAll);
		});
		
	</SCRIPT>
	<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
 </HEAD>

<BODY>
<TABLE border=0 height=600px align=left>
	<TR>
		<TD width=20% align=left valign=top style="BORDER-RIGHT: #999999 1px dashed">
			<ul id="treeDemo" class="ztree" style="width:260px; overflow:auto;"></ul>
		</TD>
		<TD width=80% align=left valign=top><IFRAME ID="Iframe" Name="Iframe" FRAMEBORDER=0 SCROLLING=AUTO style="width: 100%;height: 100%" SRC=""></IFRAME></TD>
	</TR>
</TABLE>

</BODY>
</HTML>
