<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%--<jsp:include page="head.jsp"/>--%>
<%@ include file="common/head.jsp" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JAVA学习</title>
    </head>

    <body>
        <h3>首页</h3>
        <p>
            <a href="<%=basePath%>user/list/" class='btn btn-primary glyphicon glyphicon-user'>用户列表</a></p>
        <a href="<%=basePath%>user/reg/" class='btn btn-success glyphicon glyphicon-plus '>注册用户</a></p>

  
</body>
</html>
