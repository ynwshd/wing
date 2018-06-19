
//?������֤��? 
function generateVCode(pageContext) {
    var html = '<img id="vCodeImg" src="' + pageContext + '/vd2img.jpg" onclick="javascript:refreshVCode(\'' + pageContext + '\');" />';
    $("#vCodeSpan").html(html);
    vdImg = false;
}


//?ˢ����֤��
function refreshVCode(pageContext) {
    $("#vCodeImg").attr("src", pageContext + '/vd2img.jpg?' + Math.random() + '"');
}

// �ж��ַ����Ƿ�Ϊ��
function isEmpty(variable) {

    return (variable == null || variable == undefined || variable == '');
}

// У���ֻ���
function validateCellPhoneNO(variable) {
    var reg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (isEmpty(variable))
        return false;
    return reg.test(variable);

}

// У�����֤��????
function validateSocialNO(variable) {
    var patrn = /^\s*\d{15}\s*$/;
    var patrn1 = /^\s*\d{16}[\dxX]{2}\s*$/;
    if (!patrn.exec(variable) && !patrn1.exec(variable)) {
        return false;
    }
    return true;
}

// ΢�ŷ�����ʾ��
function WxAlert(alertContent) {
    var $dialog = $('#dialog2');
    $('#alertContent').html(alertContent);
    $dialog.show();
    $dialog.find('.weui_btn_dialog').one('click', function () {
        $dialog.hide();
    });
}

// �Ƚ������ַ����Ƿ����
function equalsIgnoreCase(str1, str2) {
    if (str1.toUpperCase() == str2.toUpperCase()) {
        return true;
    }
    return false;
}

// ��������ͼ��
function loading() {
    var $loadingToast = $('#loadingToast');
    if ($loadingToast.css('display') != 'none') {
        return;
    }

    $loadingToast.show();
    setTimeout(function () {
        //functionName();

        $loadingToast.hide();
    }, 2000);
}
