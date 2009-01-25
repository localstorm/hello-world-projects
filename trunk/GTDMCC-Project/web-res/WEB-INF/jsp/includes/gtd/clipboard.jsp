<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<c:if test="${(not empty clipboard) and 
            ((not empty currContext and (not empty clipboard.lists)) or
            (not empty currList and (not empty clipboard.tasks)))}">
<div id="clipboard">
    <c:if test="${(not empty currContext) and (empty currList)}">
        <c:forEach items="${clipboard.lists}" var="list" >
            &nbsp;<a href="<c:url value="/actions/ResolveList" >
                <c:param name="listId" value="${list.id}" />
                <c:param name="action" value="PASTE" />
            </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a><c:out value="${list.name}"/><br/>
        </c:forEach>
        <c:if test="${(not empty clipboard.tasks) and (not empty currList and (not empty clipboard.lists))}"><hr/></c:if>
    </c:if>
    <c:if test="${not empty currList}">
        <c:forEach items="${clipboard.tasks}" var="task" >
            &nbsp;<a href="<c:url value="/actions/ResolveTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="PASTE" />
            </c:url>"><img src="<c:url value="/images/paste.png"/>" alt="paste"/></a><c:out value="${task.summary}"/><br/>
        </c:forEach>
    </c:if>
</div>
<br/>
</c:if>
