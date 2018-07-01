<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <%@ include file="../common/head.jsp" %>
        <title>wing-Admin detail${user.name}</title>
    </head>
    <body>
        <h2>${user.name}</h2>
        <hr/>
        <div class="pannel">
            <p class="text-success">
                编号: ${user.id}

            </p>
            <p class="text-success">
                登录名: ${user.name}
            </p>
        </div>
    </body>
</html>
