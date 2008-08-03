<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>CONTEXT</span> list</h2>
	<br/>
	<table width="100%">
	<tr>
		<th colspan="2">Operative</th>
	</tr>
        <c:forEach items="${contexts}" var="ctx">
	<tr>
		<td width="95%">
			<p><img border="0" src="<c:url value="/images/arrow.gif" />"/>
                        <span><a href="<c:url value="/actions/ViewContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                       </c:url>"><c:out value="${ctx.name}"/></a></span>
		</td>
		<td width="5%"> <a href="<c:url value="/actions/ToggleStateContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                       </c:url>" title="Archive"><img border="0" src="<c:url value="/images/trash.png"/>" /></a></p></td>
	</tr>
        </c:forEach>
        <tr >
		<td colspan="2" align="center">
                    <br/>
                    <stripes:form action="/actions/AddContext" focus="name" >
                        <stripes:errors/>
                        Name: <stripes:text name="name"/>&nbsp;<stripes:submit name="submit" value="Create" />
                    </stripes:form>
                    <br/>
                    <hr/>
                </td>
	</tr>
        <c:if test="${not empty actionBean.archiveContexts}">
            <tr >
                    <th colspan="2">Archived</th>
            </tr>
            <c:forEach items="${actionBean.archiveContexts}" var="ctx" >
            <tr>
                    <td width="95%">
                            <p><img border="0" src="<c:url value="/images/arrow.gif"/>"/> <span><a href="<c:url 
                                        value="/actions/ViewContext">
                                            <c:param name="contextId" value="${ctx.id}" />
                                        </c:url>"><c:out value="${ctx.name}"/></a></span>
                    </td>
                    <td width="5%"><nobr><a href="<c:url value="/actions/ToggleStateContext">
                                                <c:param name="contextId" value="${ctx.id}" />
                                           </c:url>" title="Unarchive"><img border="0" src="<c:url value="/images/deleted.png"/>" /></a>
                                           <a href="<c:url value="/actions/EraseContext">
                                                <c:param name="contextId" value="${ctx.id}" />
                                           </c:url>" title="Delete permanently"><img border="0" src="<c:url value="/images/erase.png"/>" /></a>
                    </nobr>
                    </td>
            </tr>
            </c:forEach>
        </c:if>
	</table>
    	<br/><br/>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>