<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<table id="roleListTable"  style="width: 100%;height: 100%"></table>
<div id="roleAddWin"></div>

<script>

    //查询角色列表
    function searchRole() {
        var param = {
            'roleStatus': $("#roleStatus").combobox('getValue'),
            'roleCreateDate': $("#roleCreateDate").datetimebox('getValue'),
            'roleName': $("#roleName").val()
        };
        $('#roleListTable').datagrid('options').queryParams = param;
        $('#roleListTable').datagrid('reload');
    }

    /**
    *添加角色
     */
    function addRole(){
        var $addroleWin;
        $addroleWin = $('#roleAddWin').window({
            title:'新增角色',
            href:"/role/showroleadd",
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
        $addroleWin.window('open');
    }
    function userupd(){
        var row= $('#roleListTable').datagrid("getSelections");

        if(row.length>0){
            $.ajax({
                url:'/role/updateStat',
                type:'post',
                data:"id="+row[0].roleid+"&status="+row[0].status,
                success: function(data){
                    if(data==1){
                        alert('修改成功');
                        searchRole();
                    }else{
                        alert(data+'修改失败');
                    }

                }
            })
        }
    }



    $(document).ready(function () {
        $('#roleListTable').datagrid({
            url: "/role/loadrolelist",
            striped:true,
            loadMsg:'数据加载中...请稍后......',
            pageList: [5,10,20,25,50,100],                      //可以设置每页记录条数的列表
            pageSize:25,
            singleSelect:true,                                     //是否单选
            pagination:true,                                        //分页控件
            rownumbers:true,                                 //行号
            toolbar: [
                {
                    text:"状态筛选: <input id='roleStatus'>"
                },
                {
                    text:"注册时间: <input id='roleCreateDate'>"
                },
                {
                    text: "角色名: <input id='roleName'>"
                },
                {
                    id: 'roleSearch',
                    text: '查询',
                    iconCls: 'icon-search',
                    handler:searchRole
                },
                {
                    id: 'roleAdd',
                    text: '新增角色',
                    iconCls: 'icon-add',
                    handler:addRole
                },
                {
                    id: 'userUpd',
                    text: '修改状态',
                    iconCls: 'icon-remove',
                    handler:userupd
                }
            ],
            columns: [[
                {field: 'roleid', title: '角色ID', width: '13%' , fitColumns:true, align:'center'},
                {field: 'rolename', title: '角色名', width: '13%' , fitColumns:true, align:'center'},
                {field: 'roleident', title: '角色标识', width: '13%' , fitColumns:true, align:'center'},
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
        $('#roleStatus').combobox({
            url:'/order/loadpayorderstatus',
            valueField:'id',
            textField:'text',
            width:'106',
            //value:"0"
        });

        //注册时间
        $('#roleCreateDate').datebox({
            required:false
        });


    })


</script>
</body>