<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">

<div id="menumainContent" class="easyui-layout" data-options="fit:true">

    <div data-options="region:'west',title:'功能列表',split:true" style="width:280px;">
        <ul id="menutreeManager">
        </ul>
    </div>

    <div data-options="region:'center'" style="background:#eee;" data-options="fit:true">

        <div  class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',title:'详细信息'" style="height: 360px">
                    <div  style="margin-left: 100px;margin-top: 30px">菜单ID:<span id="menuID"></span></div>
                    <div  style="margin-left: 100px;margin-top: 30px">菜单名称:<span id="menuText"></span></div>
                    <div  style="margin-left: 100px;margin-top: 30px">菜单URL:<span id="menuUrl" ></span></div>
            </div>

            <div data-options="region:'south',title:'菜单动作'"  style="height: 560px"   >
                <form id="menuForm">
                    <input type="hidden" name="menuPID" id="menuPID" >
                <br/>
                <div  style="margin-left: 100px;margin-top: 30px">菜单名称:<span><input type="text" id="menuNameInput" name="menuNameInput"></span></div>
                <div  style="margin-left: 100px;margin-top: 30px">菜单URL:<span><input type="text" id="menuUrlInput" name="menuUrlInput"></span></div>

                <br/>
                <br/>
                <br/>
                <br/>
                <br/>
                <div style="margin-left: 300px">
                    &nbsp;<input type="button" id="addMenuBtn" value="添加" > &nbsp;&nbsp;
                    &nbsp;<input type="button" value="修改" > &nbsp;&nbsp;
                    &nbsp;<input type="button" value="删除" > &nbsp;&nbsp;
                </div>
                </form>
            </div>
        </div>

    </div>

</div>

<script>


    $(document).ready(function () {

        $('#menutreeManager').tree({
            url: '/index/loadmanagertree',
            onClick: function (node) {
                // 清空之前缓存
                $("#menuPID").val("");
                $("#menuID").html("");
                $("#menuText").html("");
                $("#menuUrl").html("");
                //写入最新结果
                $("#menuPID").val(node.id);
                $("#menuID").html(node.id);
                $("#menuText").html(node.text);
                $("#menuUrl").html(node.url);
            }
        });

        //添加菜单
        $("#addMenuBtn").click(function(){
            if($("#menuID").html()==""){
                $.messager.alert("提示信息","请选择节点");
                return;
            }
            if($("#menuNameInput").val()==""){
                $.messager.alert("提示信息","请填写菜单名称");
                return;
            }
            if($("#menuUrlInput").val()==""){
                $.messager.alert("提示信息","请填写菜单地址");
                return;
            }
            $('#menuForm').submit();
        })


        $('#menuForm').form({
            url:'/auth/addmenu',
            onSubmit:function(){
                return $(this).form('validate');
            },
            success:function(data){
                var data = jQuery.parseJSON(data);
                if(data.code == "000"){
                    $.messager.alert('提示信息',"添加成功");
                    //刷新数据
                    $('#menutreeManager').tree('reload');
                    $('#treeManager').tree('reload');
                }
                else{
                    $.messager.alert('提示信息',"添加失败,原因:"+data.msg);
                }
            }
        });

    })


</script>
</body>



