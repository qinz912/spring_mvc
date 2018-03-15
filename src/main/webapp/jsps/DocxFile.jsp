<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="/js/jquery1.8.js" ></script>
<script src="/js/jQuery.Form.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>上传文件</title>
</head>
<body>
	<div align="center">
		<form id='add' method='post' action='/spring_mvc/courses/doUploadDoc' enctype='multipart/form-data'>
			<input type='file' id='file' name='file' accept='.docx'/>
			<br>
			<br>
			<br>
			<input type='submit'/>&nbsp;&nbsp;
		</form>
	</div>
</body>
<script>
$(function(){  
    $("#add").ajaxForm(function(data){    
    	alert(data.msg);
    }); 
    
});
function doingAlert(){
	var file=$("#file").val();  
    if(file == ""){    
        alert("请选择文件!");  
        return false;    
    }
}
</script>
</html>