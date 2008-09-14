<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>CONFIGURE</span> world</h2>
  
<br/><br/><br/>
<stripes:form action="/actions/UpdateConfig" focus="oldPassword" name="changePassForm" >
<stripes:errors/>
<div align="center">
<table width="80%" bgColor="#CCCCEE" >
	<tr>
		<th colspan="2" bgColor="#7777EE"><font color="white">Change password</font></th>
	</tr>
	<tr>
		<th colspan="2" >&nbsp;</th>
	</tr>
	<tr>
		<td align="right">Old:</td>
		<td><stripes:password style="width: 80%;" name="oldPassword" /></td>
	</tr>
	<tr>
		<td align="right">New:</td>
		<td><stripes:password style="width: 80%;" name="password"/></td>
	</tr>
	<tr>
		<td align="right">New again:</td>
		<td><stripes:password style="width: 80%;" name="password2"/></td>
	</tr>
        <tr>
		<th colspan="2" >&nbsp;</th>
	</tr>
	<tr>
		<td colspan="2"><hr/></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><stripes:submit name="changePass" value="Change"/></td>
	</tr>
</table>
</div>
</stripes:form>
  
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>