<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<c:choose>
    <c:when test="${actionBean.filter eq 'PENDING'}">
        <h2><span>PENDING</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'AWAITED'}">
        <h2><span>AWAITED</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'FLIGHT'}">
        <h2><span>FLIGHT PLAN</span> tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'REDLINE'}">
        <h2><span>BROKEN</span> red line tasks</h2>
    </c:when>
    <c:when test="${actionBean.filter eq 'DEADLINE'}">
        <h2><span>BROKEN</span> dead line tasks</h2>
    </c:when>
    <c:otherwise>
        <h2><span>????</span> tasks</h2>
    </c:otherwise>
</c:choose>

<br/>
<div align="center">
    <h4><a href="<c:url value="/actions/ViewContext">
        <c:param name="contextId" value="${actionBean.contextResult.id}" />
    </c:url>"><c:out value="${actionBean.contextResult.name}"/></a></h4>
</div>

<c:forEach items="${actionBean.tasks}" var="task">
    <p><span><c:choose>
                <c:when test="${not task.delegated}">
                    <img src="<c:url value="/images/loe${task.effort}.png"/>"/>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value="/actions/ResolveTask" >
                        <c:param name="returnPageToken" value="${returnPageToken}" />
                        <c:param name="taskId" value="${task.id}" />
                        <c:param name="action" value="UNDELEGATE" />
                    </c:url>" title="Not delegated"><img border="0" src="<c:url value="/images/delegated.png"/>"/></a>
                </c:otherwise>
            </c:choose><c:out value="${task.list.name}" />:</span><br/>
        <div align="center">
            <a href="<c:url value="/actions/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="returnPageToken" value="${returnPageToken}" />
                    </c:url>" title="Expand"><c:out value="${task.summary}" /></a>
        </div>
        <c:if test="${not empty task.details}" >
             <c:out escapeXml="false" value="${task.detailsHtmlEscaped}"/>
        </c:if>
</p>
<div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
    <stripes:form action="/actions/ResolveTask" >
        <stripes:hidden name="returnPageToken" value="${returnPageToken}" />
        <stripes:hidden name="taskId" value="${task.id}" />
        <stripes:hidden name="action" value="DELEGATE" />
        <table width="100%" style="background:#FFFFD0; border:1px dotted #DADADA;">
            <tr>
                <td width="80%" align="center"><stripes:text name="runtimeNote" id="rtn-${task.id}" style="width: 95%"/></td>
                <td width="10%" align="center"><stripes:submit name="s1" value="Delegate" style="width: 7em;" /></td>
                <td width="10%" align="center"><stripes:submit name="s1" value="Cancel" style="width: 7em;" onclick="hide('delegate-${task.id}'); return false" /></td>
            </tr>
        </table>
    </stripes:form>
</div>
<c:if test="${not empty task.runtimeNote}">
    <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
</c:if>
<table width="100%">
    <tr>
        <td width="80%" >
            <hr/>
        </td>
        <td width="20%" >
        <nobr>
            <c:if test="${not task.inFlightPlan}">
                    <a href="<c:url value="/actions/ResolveTask">
                                <c:param name="returnPageToken" value="${returnPageToken}" />
                                <c:param name="taskId" value="${task.id}" />
                                <c:param name="action" value="FLIGHT" />
                             </c:url>" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
            </c:if>
            <a href="<c:url value="/actions/ViewList" >
                <c:param name="listId" value="${task.list.id}" />
            </c:url>" title="Open affected list"><img alt="toList" border="0" src="<c:url value="/images/toList.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="FINISH" />
            </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveTask" >
                <c:param name="returnPageToken" value="${returnPageToken}" />
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="CANCEL" />
            </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
            <c:if test="${not task.delegated}">
                <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" border="0" src="<c:url value="/images/delegate.png"/>"/></a>
            </c:if>
        </nobr>
        </td>
    </tr>
</table>
</c:forEach>
<br/>


<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>
