<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<jsp:include page="/WEB-INF/PAGES/common/header.jsp" />
<body>
<table id="testTable" style="width: 100%;height: 100%">
</table>

</body>

<script>

    $(document).ready(function () {

        //查询日志data数据
        var testUrl = "<%=request.getContextPath()%>/test/gettestlist";

        $('#testTable').datagrid({
            url: testUrl,
            //toolBar: 'logsToolbar',
            pageList: [5, 10, 20, 50, 100],//可以设置每页记录条数的列表
            singleSelect: true,//是否单选
            pagination: true,//分页控件
            /*   toolbar: [
             {
             id: 'logstDetailBtn',
             text: '查看详情',
             iconCls: 'icon-edit',
             handler: showLogsDetailWin
             }
             ],*/
            columns: [[
                {field: 'id', title: '编号', width: '4%', fitColumns: true,align:'center'},
                {field: 'area', title: '位置', width: '10%', fitColumns: true,align:'center'},
                {field: 'mer_name', title: '商户', width: '16%', fitColumns: true,align:'center'},
                {field: 'shop_counts', title: '商户总数', width: '10%', fitColumns: true,align:'center'},
                {
                    field: 'intime', title: '时间', width: '12%', fitColumns: true,align:'center',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
                    }
                }
            ]],
            fit: true
        });

    })




</script>

</html>
