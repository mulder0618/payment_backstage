<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/2/15
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增商户信息</title>
</head>
<body>
<h2>新增商户信息</h2>
<form method="post" id="merInfo">
    <div>
        <label for="merId">商户ID:</label>
        <input name="merId" class="easyui-validatebox" type="text" required="true">
    </div>
    <br>
    <div>
        <label for="merName"> 商户名称:</label>
        <input name="merName" class="easyui-validatebox" type="text" required="true">
    </div>
    <br>
    <div>
        <label for="status">状态:</label>
        <select name="status">
            <option value="1">启用</option>
            <option value="0">停用</option>
        </select>
    </div>
    <br>
    <div style="display:none;">
        <label for="SupportPaymentWay">支付方式:</label>
        <input name="SupportPaymentWay" class="easyui-validatebox" type="text">
    </div>
    <br>
    <div>
        <label for="Authcode">商户秘钥:</label>
        <input name="Authcode" class="easyui-validatebox" type="text" required="true">
    </div>
    <br>
    <div>
        <input type="submit" value="添加">
    </div>
</form>

<script>

    $('#merInfo').form({
        url: '/merchant/toadd',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            if (data== "1") {
                $.messager.alert('提示信息', "添加成功");
                //刷新数据
                $('#merlistTable').datagrid('reload');
                //关闭新增窗口
                $('#meraddWin').window('close');
            }
            else {
                $.messager.alert('提示信息', "添加失败,原因:" + data.error);
            }
        }
    });


</script>
</body>
</html>
