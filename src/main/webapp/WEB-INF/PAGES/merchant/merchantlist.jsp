<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/2/15
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<html>
<head>
    <title>商户信息列表</title>
</head>
<body>
<table id="merlistTable"  style="width: 100%;height: 100%"></table>

<div id="meraddWin" ></div>
</body>
<script>
    $(function(){
        $('#merlistTable').datagrid({
                url: "/merchant/listmerchant",
                striped:true,
                loadMsg:'数据加载中...请稍后......',
                pageList: [5,10,20,25,50,100],                      //可以设置每页记录条数的列表
                pageSize:25,
                singleSelect:true,                                     //是否单选
                pagination:true,                                        //分页控件
                rownumbers:true,                                 //行号
                toolbar: [
            {
                text:"状态筛选: <input id='merStatus'>"
            },
            {
                text:"注册时间: <input id='CreateDate'>"
            },
            {
                text: "商户名: <input id='merName'>"
            },
            {
                id: 'merSearch',
                text: '查询',
                iconCls: 'icon-search',
                handler:merSearch
            },
            {
                id: 'merAdd',
                text: '新增商户',
                iconCls: 'icon-add',
                handler:merAdd
            },
            {
                id: 'merupdate',
                text: '状态修改',
                iconCls: 'icon-remove',
                handler:merupdate
            },
            {
                id: 'merupdateInfo',
                text: '修改信息',
                iconCls: 'icon-edit',
                handler:toupdate
            }
        ],
                columns: [[
            {field: 'ID', title: 'ID', width: '3%' , fitColumns:true, align:'center'},
            {field: 'MerchantID', title: '商户ID', width: '13%' , fitColumns:true, align:'center'},
            {field: 'MerchantName', title: '商户名称', width: '13%' , fitColumns:true, align:'center'},
            {field: 'CreateDate', title: '创建时间', width: '14%' , fitColumns:true, align:'center', sortable:'true',
                formatter:function(value,row,index){
                    var unixTimestamp = new Date(value);
                    return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
                }
            },
            {field: 'Status', title: '状态', width: '7%', fitColumns:true, align:'center', sortable:'true',
                formatter:function(value,row,index){
                    if(value==0){
                        return "未启用";
                    }
                    else{
                        return "已启用";
                    }
                }
            },
            {field: 'SupportPaymentWay', title: '支持的支付方式', width: '13%' , fitColumns:true, align:'center'},
            {field: 'Authcode', title: '商户秘钥', width: '13%' , fitColumns:true, align:'center'}
        ]],
                fit:true
        });


        $('#CreateDate').datebox({
            required:false
        });
        $('#merStatus').combobox({
            valueField:'id',
            textField:'text',
            width:'106',
            data: [{
                id: '2',
                text: '全部'
            },{
                id: '1',
                text: '已启用'
            },{
                id: '0',
                text: '未启用'
            }]
        });
    })
    //查询订单列表
    function merSearch() {
        var param = {
            'Status': $("#merStatus").combobox('getValue'),
            'CreateDate': $("#CreateDate").datetimebox('getValue'),
            'MerchantName': $("#merName").val()
        };
        $('#merlistTable').datagrid('options').queryParams = param;
        $('#merlistTable').datagrid('reload');
    }
    /**
     *添加角色
     */
    function merAdd(){
        var $win;
        $win = $('#meraddWin').window({
            title:'新增商户信息',
            href:"/merchant/showmeradd",
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
    function merupdate(){
       var row= $('#merlistTable').datagrid("getSelections");
        var array="{\"array\":[";
        for(var i=0;i<row.length;i++ ){
            array+="{\"id\":\""+row[i].ID+"\",\"status\":\""+row[i].Status+"\"},";
        }
        array=  array.substring(0,array.length-1)+"]}";

        if(row.length>0){
            $.ajax({
                url:'/merchant/updatestat',
                type:'post',
                data:"array="+array,
                success: function(data){
                    if(data==1){
                        alert('修改'+row.length+'条成功');
                        merSearch();
                    }else{
                        alert(data+'修改失败');
                    }

                }
            })
        }
    };
    function toupdate(){
        var row= $('#merlistTable').datagrid("getSelections");
        var $win;
        $win = $('#meraddWin').window({
            title:'修改商户信息',
            href:"/merchant/showmerupd",
            width: 620,
            height: 350,
            shadow: true,
            modal: true,
            iconCls: 'icon-edit',
            closed: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
        if(row.length>0) {
            $win.window('open');
        }else{
            alert("请选择")
        }

    }
</script>
</html>
