<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

</div>
<div id="bodyRightPan">
  	<h2><span>Contexts</span> pane</h2>
	<p class="more"><a href="<c:url value="/actions/gtd/EditContexts"/>">EDIT</a></p>
        <table class="contexts">
        <c:forEach items="${contexts}" var="ctx" >            
            <tr>
                <td width="18px" valign="top"><img src="<c:url value="/images/button.png"/>"/></td>
                <td><a href="<c:url value="/actions/gtd/ctx/ViewContext">
                       <c:param name="contextId" value="${ctx.id}" />
                     </c:url>"><c:out value="${ctx.name}"/></a></td>
            </tr>
        </c:forEach>
        </table>            
	
        <h2><span>Reports</span> pane</h2>
            <table class="reports">
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/TaskStructureReport" />">Tasks structure</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/DeadlineLookupReport" />">Task deadlines</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/OldTasksReport" />">Old tasks</a></td>
                </tr>
            </table>

        <h2><span>Search</span> pane</h2>
            <table class="reports">
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/search.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/SearchTasks"/>">Search tasks</a></td>
                </tr>
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/search.png"/>"/></td>
                    <td><a href="<c:url value="/actions/gtd/SearchRefObjAttach"/>">Search reference</a></td>
                </tr>
            </table>
</div>
