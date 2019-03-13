var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj) {
    $.ajax({
        type: "GET",
        url: path + "/bill/billDel.action?billid=" + obj.attr("billid"),
        dataType: "json",
        success: function (data) {
            if (data == true) {//删除成功：移除删除行
                cancleBtn();
                obj.parents("tr").remove();
                window.location.href = path + "/bill/allBills.action";
            } else if (data == false) {//删除失败
                //alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                changeDLGContent("对不起，删除订单【" + obj.attr("billcc") + "】失败");
            } else if (data == notexist) {
                //alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                changeDLGContent("对不起，订单【" + obj.attr("billcc") + "】不存在");
            }
        },
        error: function (data) {
            alert("对不起，删除失败");
        }
    });
}

function openYesOrNoDLG() {
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
}

function cancleBtn() {
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}

function changeDLGContent(contentStr) {
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function () {
    $(".viewBill").on("click", function () {
        var obj = $(this);
        window.location.href = path + "/bill/billView.action?billId=" + obj.attr("billid");
        // 将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
    });

    $(".modifyBill").bind("click", function () {
        var obj = $(this);
        window.location.href = path + "/bill/billDetail.action?billId=" + obj.attr("billid");
    });
    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteBill(billObj);
    });

    $(".deleteBill").on("click", function () {
        billObj = $(this);
        changeDLGContent("你确定要删除订单【" + billObj.attr("billcc") + "】吗？");
        openYesOrNoDLG();
    });

});