<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<h2>新增角色</h2>
<form id="roleForm" method="post">
    <input id="selectedAuths" name="selectedAuths" type="hidden">
    <br/>
    <div>
        <label for="roleName">角色名:</label>
        <input class="easyui-validatebox" type="text" name="roleName" required="true" />
    </div>
    <br>
    <div>
        <label for="roleIdent">角色标识(如:SUPERADMIN):</label>
        <input class="easyui-validatebox" type="text" name="roleIdent" required="true" />
    </div>
    <br>
    <div>
        <label for="auth">所属权限</label>
        <ul id="addRoleTreeManager"  >
        </ul>
    </div>
    <br>
    <div>
        <input type="submit" value="添加">
    </div>
</form>

<script>

    $('#roleForm').form({
        url:'/role/add',
        onSubmit:function(){
            var checkedAuths = "";
            var data = $('#addRoleTreeManager').tree('getChecked');
            for(var i=0;i<data.length;i++){
                checkedAuths = data[i].id + "," + checkedAuths ;
            }
            $("#selectedAuths").val(checkedAuths);
            return $(this).form('validate');
        },
        success:function(data){
            var data = jQuery.parseJSON(data);
            if(data.result == "success"){
                $.messager.alert('提示信息',"添加成功");
                //刷新数据
                $('#roleListTable').datagrid('reload');
                //关闭新增窗口
                $('#roleAddWin').window('close');
            }
            else{
                $.messager.alert('提示信息',"添加失败,原因:"+data.error);
            }
        }
    });



    $('#addRoleTreeManager').tree({
        url: '/index/loadmanagertree',
        checkbox:"true",
        onClick: function (node) {
        }
    });

</script>
</body>



