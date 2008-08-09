<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>LIST</span> tasks</h2>
<div align="right" ><a href="#" title="Paste task"><img src="<c:url value="/images/paste.png" />" border="0" /></a>&nbsp;(<c:out value="${actionBean.listResult.name}"/>)</div> 

<table width="100%">
<c:if test="${not empty actionBean.tasks}" >
    <tr>
        <th colspan="2">Operative</th>
    </tr>
    <c:forEach items="${actionBean.tasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
            <table width="100%">
                <tr>
                    <td width="80%" ><hr/></td>
                    <td width="20%" >
                    <nobr>
                        <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="FLIGHT" />
                                 </c:url>" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="FINISH" />
                                 </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="CANCEL" />
                                 </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="DELEGATE" />
                                 </c:url>" title="Delegate"><img border="0" src="<c:url value="/images/delegate.png"/>"/></a>
                    </nobr>
                    </td>
                </tr>

            </table>
            <c:if test="${empty actionBean.awaitedTasks}">
                <c:if test="${status.last}">
                    <p class="more"><a href="#">PRINT</a></p>
                </c:if>
            </c:if>
        </td>
    </tr>
    </c:forEach>
</c:if>
<c:if test="${not empty actionBean.awaitedTasks}">
    <tr>
        <th colspan="2">Awaited</th>
    </tr>
    <c:forEach items="${actionBean.awaitedTasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><a href="<c:url value="/actions/ResolveTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNDELEGATE" />
                        </c:url>" title="Not delegated"><img border="0" src="<c:url value="/images/delegated.png"/>" /></a>
              <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}"/></a><hr/></p>
            <c:if test="${status.last}">
                <p class="more"><a href="#">PRINT</a></p>
            </c:if>
        </td>
    </tr>
    </c:forEach>
</c:if>
<tr>
   <td colspan="2" align="center">
    <br/>
        <stripes:form action="/actions/AddTask" focus="summary" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="1" />
            Summary: <stripes:text name="summary"/>&nbsp;<stripes:submit name="submit" value="Create" />
        </stripes:form>
     <br/>
     <hr/>
     
    </td>
</tr>
<c:if test="${not empty actionBean.archiveTasks}" >
<tr>
    <th colspan="2">Archive</th>
</tr>

<c:forEach items="${actionBean.archiveTasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><a href="<c:url value="/actions/ResolveTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="UNRESOLVE" />
                        </c:url>" title="<c:choose><c:when test="${task.finished}">Not finished</c:when><c:when test="${task.cancelled}">Not cancelled</c:when><c:otherwise>Unresolve</c:otherwise></c:choose>">
                        <img border="0" src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>" /></a>
              <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}"/></a><hr/></p>
        </td>
    </tr>
</c:forEach>
</table>
</c:if>	

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>