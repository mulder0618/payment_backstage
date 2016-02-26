<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<table id="userListTable"  style="width: 100%;height: 100%"></table>

<div id="userAddWin" ></div>
<div id="userBindRolesWin" ></div>
<script>

    //查询订单列表
    function searchUser() {
        var param = {
            'userStatus': $("#userStatus").combobox('getValue'),
            'userCreateDate': $("#userCreateDate").datetimebox('getValue'),
            'userName': $("#userName").val()
        };
        $('#userListTable').datagrid('options').queryParams = param;
        $('#userListTable').datagrid('reload');
    }

    /**
    *添加角色
     */
    function addUser(){
        var $win;
        $win = $('#userAddWin').window({
            title:'新增用户',
            href:"/user/showuseradd",
            width: 620,
            height: 350,
            shadow: true,
            modal: true,
            iconCls: 'icon-add',
            closed: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
        $win.window('open');
    }


    $(document).ready(function () {
        $('#userListTable').datagrid({
            url: "/user/loaduserlist",
            striped:true,
            loadMsg:'数据加载中...请稍后......',
            pageList: [5,10,20,25,50,100],                      //可以设置每页记录条数的列表
            pageSize:25,
            singleSelect:true,                                     //是否单选
            pagination:true,                                        //分页控件
            rownumbers:true,                                 //行号
            toolbar: [
                {
                    text:"状态筛选: <input id='userStatus'>"
                },
                {
                    text:"注册时间: <input id='userCreateDate'>"
                },
                {
                    text: "用户名: <input id='userName'>"
                },
                {
                    id: 'userSearch',
                    text: '查询',
                    iconCls: 'icon-search',
                    handler:searchUser
                },
                {
                    id: 'userAdd',
                    text: '新增用户',
                    iconCls: 'icon-add',
                    handler:addUser
                }
            ],
            columns: [[
                {field: 'userid', title: '用户ID', width: '13%' , fitColumns:true, align:'center'},
                {field: 'username', title: '用户名', width: '13%' , fitColumns:true, align:'center'},
                {field: 'createdate', title: '创建时间', width: '14%' , fitColumns:true, align:'center', sortable:'true',
                    formatter:function(value,row,index){
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
                    }
                },
                {field: 'status', title: '状态', width: '15%', fitColumns:true, align:'center', sortable:'true',
                    formatter:function(value,row,index){
                        if(value==0){
                            return "未启用";
                        }
                        else{
                            return "已启用";
                        }
                    }
                }
            ]],
            fit:true
        });

        //作业状态下拉选择框
        $('#userStatus').combobox({
            url:'/order/loadpayorderstatus',
            valueField:'id',
            textField:'text',
            width:'106',
            //value:"0"
        });

        //注册时间
        $('#userCreateDate').datebox({
            required:false
        });


    })


</script>
</body>



