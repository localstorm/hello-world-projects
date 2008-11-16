<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<%@ include file="clipboard.jsp" %>
<h2><span>EASY TASKS</span> report</h2>
    <table width="100%">
        <tr>
            <td align="left">
                <stripes:form action="/actions/SetEasyFilter" >
                    <stripes:select name="contextId" value="${filterCtx}" onchange="submit();">
                        <stripes:option value="-1" >[Show all]</stripes:option>
                        <c:forEach items="${contexts}" var="ctx" >            
                            <stripes:option value="${ctx.id}" ><c:out value="${ctx.name}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </stripes:form>
            </td>
        </tr>
    </table>
<br/>
<c:if test="${not empty actionBean.tasks}">
<table width="100%"><tr><th>Operative</th></tr></table> 
<c:forEach items="${actionBean.tasks}" var="task">
    <p><span><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                        <c:param name="returnPage" value="EASY_REPORT" />
                </c:url>" title="Expand"><c:out value="${task.summary}" /></a></p>
</c:forEach>
</c:if>

 
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>