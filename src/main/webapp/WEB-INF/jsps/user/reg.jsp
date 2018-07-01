<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@ include file="../common/head.jsp" %>
        <title>${sitetitle}用户注册</title>
    </head>
    <body>
        <div class="row">
            <form  class="col-lg-offset-3 col-lg-3" action="<%=basePath%>user/regconfirm" method='POST'>
                <h2>注册用户</h2>
                <hr/>
                <div class="form-group"  >
                    <label>用户名</label>
                    <input text="text" name="name" id='username' class="form-control"/>
                    <a class="btn btn-default" id="btnCheckUserName">检查可用</a>
                    <span id='userCheckResult'></span>
                </div>
                <div class="form-group"  >
                    <label>年龄</label>
                    <input text="text" name="age" class="form-control"/>
                </div>
                <div class="form-group"  >
                    <input type="submit" class="btn btn-primary " value="提交注册">
                </div>
                </from>
        </div>
        <script>
            $(function () {
                $('#btnCheckUserName').click(function () {
                    $.post(basePath + 'user/checkusername/', {name: $('#username').val()},
                            function (result) {
                                if (result.issuccess) {
                                    $('#userCheckResult').attr('class','text-success')
                                } else {
                                    $('#userCheckResult').attr('class','text-danger')
                                }
                                $('#userCheckResult').text(result.appmsg)
                            });
                }
                );
            });



        </script>
    </body>
</html>
