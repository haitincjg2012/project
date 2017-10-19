<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">

<title>Insert title here</title>
<style>
 img,p{
width:100%;
max-width:100%; 
} 
*{
padding:0px;
margin:0px;
/* text-align:center; */
}
</style>
</head>
<body>
   <div>
    <c:forEach items="${textDetails}" var="c">
		            <c:if test="${c.content!=null}">
		            <p style="text-align:left;color:#123456;font-size:18px;">${c.content}</p>
		            </c:if>
		            <c:if test="${c.address!=null}">
		            <img src="${ctx_ali}/${c.address}">
		            </c:if>
		       
   </c:forEach>
   </div>
</body>
</html>