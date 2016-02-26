<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<h2>新增用户</h2>
<form id="userForm" method="post">
    <div>
        <label for="userName">用户名:</label>
        <input class="easyui-validatebox" type="text" name="userName" required="true" />
    </div>
    <br>
    <div>
        <label for="password">密码:</label>
        <input class="easyui-validatebox" type="password" name="password" required="true" />
    </div>
    <br>
    <div>
        <label for="roles">角色:</label>
        <input   name="role" id="role" />
    </div>
    <div>
        <input type="submit" value="添加">
    </div>
</form>

<script>

    $('#userForm').form({
        url:'/user/add',
        onSubmit:function(){
            return $(this).form('validate');
        },
        success:function(data){
            var data = jQuery.parseJSON(data);
            if(data.result == "success"){
                $.messager.alert('提示信息',"添加成功");
                //刷新数据
                $('#userListTable').datagrid('reload');
                //关闭新增窗口
                $('#userAddWin').window('close');
            }
            else{
                $.messager.alert('提示信息',"添加失败,原因:"+data.error);
            }
        }
    });

    //作业状态下拉选择框
    $('#role').combobox({
        url:'/role/getallrole',
        valueField:'roleid',
        textField:'rolename',
        width:'106',
        multiple:true
        //value:"0"
    });

</script>
</body>



