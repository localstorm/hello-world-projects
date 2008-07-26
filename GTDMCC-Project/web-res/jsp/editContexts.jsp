<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>CONTEXT</span> list</h2>
	<br/>
	<table width="100%">
	<tr>
		<th colspan="2">Operative</th>
	</tr>
        <c:forEach items="${actionBean.operativeContexts}" var="ctx">
	<tr>
		<td width="95%">
			<p><img border="0" src="<c:url value="/images/arrow.gif" />"/>&nbsp;<span><a href="#">University</a></span>
		</td>
		<td width="5%"> <a href="#" title="Delete"><img border="0" src="<c:url value="/images/trash.png"/>" /></a></p></td>
	</tr>
        </c:forEach>
        <tr >
		<td colspan="2" align="center">
                    <br/>
                    <stripes:form action="/actions/EditContexts" focus="name" >
                        <stripes:errors/>
                        <stripes:text name="name"/>&nbsp;<stripes:submit name="submit" value="Create" />
                    </stripes:form>
                    <br/>
                    <hr/>
                </td>
	</tr>
	<tr >
		<th colspan="2">Archived</th>
	</tr>
        <c:forEach items="${actionBean.archiveContexts}" var="ctx" >
	<tr>
		<td width="95%">
			<p><img border="0" src="<c:url value="/images/arrow.gif"/>"/> <span><a href="#">Iraq</a></span>
		</td>
		<td width="5%"> <a href="#" title="Make Operative Again"><img border="0" src="<c:url value="/images/deleted.png"/>" /></a></p></td>
	</tr>
        </c:forEach>
	</table>
    	<br/><br/>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>