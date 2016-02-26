<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<div id="mainContent" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north' " style="height:60px;">
        支付管理后台
        <div ><a href="/auth/logout">退出登陆</a></div>
    </div>
    <div data-options="region:'south',title:'',split:true" style="height:60px;">
        资和信电子支付有限公司
    </div>
    <div data-options="region:'west',title:'功能列表',split:true" style="width:260px;">
        <ul id="treeManager">
        </ul>
    </div>
    <div data-options="region:'center'" style="background:#eee;">
        <div id="tabManager" class="easyui-tabs" data-options="fit:true" ></div>
    </div>
</div>

</body>

<script>

    $(document).ready(function () {

        $('#treeManager').tree({
            url: '/index/loadmanagertree',
            onClick: function (node) {
                var isTabExists = $('#tabManager').tabs("exists",node.text);
                if(isTabExists==false){
                    $('#tabManager').tabs('add', {
                        title: node.text,
                        href: node.url,
                        closable: true
                    });
                }
            }
        });

    })


</script>

</html>
