<%--
  Created by IntelliJ IDEA.
  User: ZHUKE
  Date: 2016/4/25
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>picture share upload</title>
    <script src="js/jquery-2.2.2.min.js"></script>
    <script src="js/public.js"></script>
</head>
<body>
<form id="pictureshareform" method="post" enctype="multipart/form-data">
    <textarea name="remark"></textarea><br>
    <input type="file" name="pictures"/><br>
    <input type="file" name="pictures"/><br>
    <input type="hidden" id="accessToken" name="accessToken">
    <input type="submit">
</form>
<script>
    var accessToken = sessionStorage.getItem('accessToken');
    $("#accessToken").val(accessToken);
    $("#pictureshareform").attr('action', server_host + "/pictureshare/picture_share?accessToken=" + accessToken);
</script>
</body>
</html>
