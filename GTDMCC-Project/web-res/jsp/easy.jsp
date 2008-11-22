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
<c:forEach items="${actionBean.tasks}" var="task">
    <p><span><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                        <c:param name="returnPage" value="EASY_REPORT" />
                </c:url>" title="Expand"><c:out value="${task.summary}" /></a></p>
    <c:if test="${not empty task.details}" >
        <div align="center">
            <c:out escapeXml="false" value="${task.detailsHtmlEscaped}"/>
        </div>
    </c:if>    
<div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
    <stripes:form action="/actions/ResolveEasyTask" >
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
            <a href="<c:url value="/actions/ViewList" >
                <c:param name="listId" value="${task.list.id}" />
            </c:url>" title="Open affected list"><img alt="toList" border="0" src="<c:url value="/images/toList.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveEasyTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="FINISH" />
            </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveEasyTask" >
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

 
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>