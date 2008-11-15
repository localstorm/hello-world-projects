<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<%@ include file="clipboard.jsp" %>
<h2><span>FLIGHT</span> plan</h2>
    <table width="100%">
        <tr>
            <td align="left">
                <stripes:form action="/actions/SetIndexFilter" >
                    <stripes:select name="contextId" value="${fpFilterCtx}" onchange="submit();">
                        <stripes:option value="-1" >[Show all]</stripes:option>
                        <c:forEach items="${contexts}" var="ctx" >            
                            <stripes:option value="${ctx.id}" ><c:out value="${ctx.name}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </stripes:form>
            </td>
                
            <td align="right">
                <c:if test="${actionBean.redlinesBroken}">
                    <a href="<c:url value="/actions/DeadlinesReport" />" title="Redlines are broken!"><img src="<c:url value="/images/redline.png"/>" border="0" /></a>
                </c:if>
                <c:if test="${actionBean.deadlinesBroken}">
                    <c:if test="${actionBean.redlinesBroken}">|</c:if>
                    <a href="<c:url value="/actions/DeadlinesReport" />" title="Deadlines are broken!"><img src="<c:url value="/images/deadline.png"/>" border="0" /></a>
                </c:if>
                <c:if test="${(not empty actionBean.flightPlanTasks) or (not empty actionBean.awaitedFlightPlanTasks) or (not empty actionBean.archiveFlightPlanTasks)}">
                    <c:if test="${actionBean.redlinesBroken or actionBean.deadlinesBroken}">|</c:if>
                    <a href="<c:url value="/actions/UtilizeFlightPlan" />" title="Utilize &amp; build new"><img src="<c:url value="/images/utilize.png"/>" border="0" /></a>
                </c:if>
            </td>
            
        </tr>
    </table>
<br/>
<c:if test="${not empty actionBean.flightPlanTasks}">
<table width="100%"><tr><th>Operative</th></tr></table> 
<c:forEach items="${actionBean.flightPlanTasks}" var="task">
    <p><span><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                        <c:param name="returnPage" value="IDX" />
                </c:url>" title="Expand"><c:out value="${task.summary}" /></a></p>
<div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
    <stripes:form action="/actions/ResolveFlightTask" >
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
<table width="100%">
    <tr>
        <td width="80%" ><hr/></td>
        <td width="20%" >
        <nobr>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNFLIGHT" />
            </c:url>" title="Remove from flight plan"><img alt="unflight" border="0" src="<c:url value="/images/unflight.png"/>"/></a>
            <a href="<c:url value="/actions/ViewList" >
                <c:param name="listId" value="${task.list.id}" />
            </c:url>" title="Open affected list"><img alt="toList" border="0" src="<c:url value="/images/toList.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="FINISH" />
            </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="CANCEL" />
            </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
            <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" border="0" src="<c:url value="/images/delegate.png"/>"/></a>
        </nobr>
        </td>
    </tr>
</table>
</c:forEach>
</c:if>

<c:if test="${not empty actionBean.awaitedFlightPlanTasks}">
<table width="100%"><tr><th>Awaited</th></tr></table> 
<c:forEach items="${actionBean.awaitedFlightPlanTasks}" var="task">

    <p><a href="<c:url value="/actions/ResolveFlightTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNDELEGATE" />
                        </c:url>" title="Not delegated"><img border="0" src="<c:url value="/images/delegated.png"/>" /></a>
                        <a href="<c:url value="/actions/ResolveFlightTask" >
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNFLIGHT" />
                        </c:url>" title="Remove from flight plan"><img alt="unflight" border="0" src="<c:url value="/images/unflight.png"/>"/></a>
                        <span><c:out value="${task.list.context.name}" />:</span>&nbsp;
                        <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                            <c:param name="returnPage" value="IDX" />
                        </c:url>"><c:out value="${task.summary}"/></a></p>
<hr/>
</c:forEach>
</c:if>

<c:if test="${(not empty actionBean.flightPlanTasks) or (not empty actionBean.awaitedFlightPlanTasks)}">
    <p class="more"><a href="#">PRINT</a></p>
</c:if>

<c:if test="${not empty actionBean.archiveFlightPlanTasks}">
<table width="100%"><tr><th>Archive</th></tr></table> 
<c:forEach items="${actionBean.archiveFlightPlanTasks}" var="task" >
<p>
    <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNRESOLVE" />
            </c:url>" title="Unresolve"><img border="0" src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>" /></a>
    <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNFLIGHT" />
            </c:url>" title="Remove from flight plan"><img alt="unflight" border="0" src="<c:url value="/images/unflight.png"/>"/></a>            
    <span><c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                        <c:param name="returnPage" value="IDX" />
                </c:url>"><c:out value="${task.summary} "/></a>
    <hr/>
</p>
</c:forEach>
</c:if>
	
 
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>