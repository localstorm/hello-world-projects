<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>DEADLINES</span> report</h2>
<br/>
<table width="100%">
<c:if test="${not empty actionBean.deadlinedTasks}">
    <tr>
        <th>Deadline broken:</th>
    </tr> 
</c:if>
<c:forEach items="${actionBean.deadlinedTasks}" var="task" >
<tr bgcolor="#A3A69C"> 
    <td>
        <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<span><c:out value="${task.list.context.name}/${task.list.name}"/>&nbsp;(Since: <fmt:formatDate value="${task.creation}"/>):</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                            <c:param name="returnPage" value="DL_REPORT" />
                    </c:url>"><c:out value="${task.summary}" /></a>
        </div>
        </p>
        <c:if test="${not empty task.details}" >
            <div align="center">
                <textarea readonly style="width: 90%; border: dotted 1px" rows="5"><c:out 
                 value="${task.details}"/></textarea>
            </div>
        </c:if>
        <c:if test="${not empty task.runtimeNote}" >
            <p><i>Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
        </c:if>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="1%" >
                <nobr>
                    <c:if test="${not task.inFlightPlan}">
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
                    </c:if>
                    <a href="<c:url value="/actions/ViewList" >
                        <c:param name="listId" value="${task.list.id}" />
                    </c:url>" title="Open affected list"><img alt="toList" border="0" src="<c:url value="/images/toList.png"/>"/></a>
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FINISH" />
                             </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="CANCEL" />
                             </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>

        </table>
    </td>
</tr>    
</c:forEach>
<c:if test="${not empty actionBean.redlinedTasks}">
    <tr>
        <th>Redline broken:</th>
    </tr> 
</c:if>
<c:forEach items="${actionBean.redlinedTasks}" var="task" >
<tr bgcolor="#FFCF91"> 
    <td>
        <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<span><c:out value="${task.list.context.name}/${task.list.name}"/>&nbsp;(Since: <fmt:formatDate value="${task.creation}"/>):</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                            <c:param name="returnPage" value="DL_REPORT" />
                    </c:url>"><c:out value="${task.summary}" /></a>
        </div>
        </p>
        <c:if test="${not empty task.details}" >
            <div align="center">
                <textarea readonly style="width: 90%; border: dotted 1px" rows="5"><c:out 
                 value="${task.details}"/></textarea>
            </div>
        </c:if>
        <c:if test="${not empty task.runtimeNote}" >
            <p><i>Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
        </c:if>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="1%" >
                <nobr>
                    <c:if test="${not task.inFlightPlan}">
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
                    </c:if>
                    <a href="<c:url value="/actions/ViewList" >
                        <c:param name="listId" value="${task.list.id}" />
                    </c:url>" title="Open affected list"><img alt="toList" border="0" src="<c:url value="/images/toList.png"/>"/></a>
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FINISH" />
                             </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="<c:url value="/actions/ResolveDeadlines">
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="CANCEL" />
                             </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>

        </table>
    </td>
</tr>    
</c:forEach>
</table>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>