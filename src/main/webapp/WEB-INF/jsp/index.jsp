<%@page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<h2>单个文件上传:</h2>
<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="提交上传"/>
</form>
</body>
</html>
