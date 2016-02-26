<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp"/>
<body width="100%" height="100%">
<table id="orderListTable"  style="width: 100%;height: 100%">

</table>
<script>

    //查询订单列表
    function searchOrder() {
        var param = {
            'orderStatus': $("#orderStatus").combobox('getValue'),
            'startTime': $("#orderStartTime").datetimebox('getValue'),
            'endTime': $("#orderEndTime").datetimebox('getValue'),
            'orderNo': $("#orderNo").val(),
            'gatewayOrderNo':$("#gatewayOrderNo").val()
        };
        $('#orderListTable').datagrid('options').queryParams = param;
        $('#orderListTable').datagrid('reload');
    }


    //修改付款状态
    function changeOrderStatus(){
        var row = $('#orderListTable').datagrid('getSelected');
        if(row==null){
            $.messager.alert("提示信息","请选择一行");
        }
        else{
            var status = row.Status;
            var payID = row.PayID;
            var orderNO = row.OrderNO;
            var merchantID = row.MerchantID;
            var amount = row.Amount;
            if(status==1){
                $.messager.alert("提示信息","该笔交易已经结束,无需修改状态");
            }
            else{
                $.messager.confirm("提示信息","修改支付状态置为成功?", function (result) {
                    if(result==true){
                        //1.$.ajax带json数据的异步请求
                        var aj = $.ajax( {
                            url:'/order/modifyorder',// 跳转到 action
                            data:{
                                payID : payID,
                                orderNO : orderNO,
                                merchantID : merchantID,
                                amount : amount
                            },
                            type:'post',
                            cache:false,
                            dataType:'json',
                            success:function(data) {
                                if(data.is_cuccess=="T"){
                                    $.messager.alert("提示信息","修改成功");
                                }
                                else{
                                    $.messager.alert("提示信息","修改失败,支付宝错误码:"+data.error);
                                }
                            },
                            error : function() {
                                alert("网络连接异常");
                            }
                        });
                    }
                });
            }
        }
    }


    $(document).ready(function () {
        $('#orderListTable').datagrid({
            url: "/order/loadorderlist",
            striped:true,
            loadMsg:'数据加载中...请稍后......',
            pageList: [5,10,20,25,50,100],                      //可以设置每页记录条数的列表
            pageSize:25,
            singleSelect:true,                                     //是否单选
            pagination:true,                                        //分页控件
            rownumbers:true,                                 //行号
            toolbar: [
                {
                    text:"状态筛选: <input id='orderStatus'>"
                },
                {
                    text:"开始时间: <input id='orderStartTime'>"
                },
                {
                    text: "结束时间: <input id='orderEndTime'>"
                },
                {
                    text: "商户订单号: <input id='orderNo'>"
                },
                {
                    text: "网关订单号: <input id='gatewayOrderNo'>"
                },
                {
                    id: 'orderSearch',
                    text: '查询',
                    iconCls: 'icon-search',
                    handler:searchOrder
                },
                {
                    id: 'orderStatusChange',
                    text: '修改付款状态',
                    iconCls: 'icon-search',
                    handler:changeOrderStatus
                }
            ],
            columns: [[
                {field: 'PayID', title: '网关订单号', width: '13%' , fitColumns:true, align:'center',
                    /* formatter:function(value,rowData,rowIndex){
                     //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
                     return "<a href='javascript:showOrderDetailWin("+value+")' >"+value+"</a>";
                     }*/
                },
                {field: 'OrderNO', title: '商户订单号', width: '13%' , fitColumns:true, align:'center'},
                {field: 'Amount', title: '应付金额', width: '6%' , fitColumns:true, align:'right', sortable:'true',
                    formatter:function(value,row,index){
                        return fmoney(value,2);
                    }
                },
                {field: 'CreateDate', title: '下单时间', width: '14%' , fitColumns:true, align:'center', sortable:'true',
                    formatter:function(value,row,index){
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
                    }
                },
                {field: 'Status', title: '状态', width: '15%', fitColumns:true, align:'center', sortable:'true',
                    formatter:function(value,row,index){
                        if(value==0){
                            return "未付款";
                        }
                        else{
                            return "已付款";
                        }
                    }
                },
                {field: 'Paytime', title: '付款时间', width: '14%' , fitColumns:true, align:'center',sortable:'true',
                    formatter:function(value,row,index){
                        if(value!=null&&value!=""){
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
                        }
                        else{
                            return "尚未付款";
                        }
                    }
                }
            ]],
            fit:true
        });

        //作业状态下拉选择框
        $('#orderStatus').combobox({
            url:'/order/loadpayorderstatus',
            valueField:'id',
            textField:'text',
            width:'106',
            //value:"0"
        });

        //订单开始时间
        $('#orderStartTime').datebox({
            required:false
        });

        //订单结束时间
        $('#orderEndTime').datebox({
            required:false
        });

    })


</script>
</body>



