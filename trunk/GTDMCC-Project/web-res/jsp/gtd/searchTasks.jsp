<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>TASKS</span> search</h2>
    <div align="right" width="80%"><a href="#" onclick="show('tsDiv', 'text-id'); return false">Search again</a></div>
    <div align="center">

    <div id="tsDiv" width="80%" style="display: <c:choose>
             <c:when test="${not actionBean.found}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/SubmitTaskSearch">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Text: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" value="${actionBean.text}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2"><hr/></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td colspan="2" align="center">
                    <stripes:submit name="submit" value="Search" style="width: 7em;"/>
                    <c:if test="${actionBean.found}">
                        <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('tsDiv'); return false" />
                    </c:if>

                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    <br/>
    <c:if test="${actionBean.found}">
	<table width="100%">

    <c:if test="${not empty actionBean.operativeTasks}" >
    <tr>
        <th colspan="2">Operative</th>
    </tr>
    <c:forEach items="${actionBean.operativeTasks}" var="task" varStatus="status">
    <tr>
        <td>
            <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<a href="<c:url value="/actions/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
            <table width="100%">
                <tr>
                    <td width="90%" ><hr/></td>
                    <td width="10%" >
                    <nobr>
                        <a href="<c:url value="/actions/ViewList">
                                    <c:param name="listId" value="${task.list.id}" />
                                 </c:url>" title="Open affected list"><img alt="list" src="<c:url value="/images/toList.png"/>" border="0" /></a>
                    </nobr>
                    </td>
                </tr>

            </table>
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
            <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<a href="<c:url value="/actions/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
            <c:if test="${not empty task.runtimeNote}">
                <p><i>&nbsp;Responsibility:&nbsp;</i><c:out value="${task.runtimeNote}"/></p>
            </c:if>
            <table width="100%">
                <tr>
                    <td width="90%" ><hr/></td>
                    <td width="10%" >
                    <nobr>
                        <a href="<c:url value="/actions/ViewList">
                                    <c:param name="listId" value="${task.list.id}" />
                                 </c:url>" title="Open affected list"><img alt="list" src="<c:url value="/images/toList.png"/>" border="0" /></a>
                    </nobr>
                    </td>
                </tr>

            </table>
        </td>
    </tr>
    </c:forEach>
</c:if>

<c:if test="${not empty actionBean.archiveTasks}" >
<tr>
    <th colspan="2">Archive</th>
</tr>

<c:forEach items="${actionBean.archiveTasks}" var="task" varStatus="status">
    <tr>
        <td>
            <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<a href="<c:url value="/actions/ViewTask">
                            <c:param name="taskId" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
            <table width="100%">
                <tr>
                    <td width="90%" ><hr/></td>
                    <td width="10%" >
                    <nobr>
                        <a href="<c:url value="/actions/ViewList">
                                    <c:param name="listId" value="${task.list.id}" />
                                 </c:url>" title="Open affected list"><img alt="list" src="<c:url value="/images/toList.png"/>" border="0" /></a>
                    </nobr>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</c:forEach>
</c:if>
    </table>
    </c:if>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>