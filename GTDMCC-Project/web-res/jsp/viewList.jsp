<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<%@ include file="clipboard.jsp" %>
<h2><span>LIST</span> tasks</h2>
<div align="right" ><a href="<c:url value="/actions/ViewContext" >
                               <c:param name="contextId" value="${actionBean.listResult.context.id}" />
                             </c:url>" title="Go to parent"><img src="<c:url value="/images/parent.png" />" border="0" /></a>&nbsp;<a href="#" onclick="show('addTaskDiv', 'summary-id'); hide('renameDiv'); return false">Add task</a>
                            (<a href="#" onclick="show('renameDiv', 'newname-id'); hide('addTaskDiv'); return false"><c:out value="${actionBean.listResult.name}"/></a>)</div> 


    <div align="center">
    
    <div id="addTaskDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/AddTask" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="1" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Summary: </td>
                <td><stripes:text name="summary" id="summary-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Effort: </td>
                <td>
                    <stripes:select name="effort" value="3" style="width: 100%">
                        <c:forEach items="${actionBean.efforts}" var="effort">
                            <stripes:option value="${effort.effort}"><c:out value="${effort.latinName}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Flight: </td>
                <td>
                    <stripes:checkbox name="flight" checked="false" />
                </td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addTaskDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
<div id="renameDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and not empty renameForm}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/RenameList" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="1" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" id="newname-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Rename" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('renameDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>
</div>

<table width="100%">
<c:if test="${not empty actionBean.tasks}" >
    <tr>
        <th colspan="2">Operative</th>
    </tr>
    <c:forEach items="${actionBean.tasks}" var="task" varStatus="status">
    <tr> 
        <td>
            <p><img src="<c:url value="/images/loe${task.effort}.png"/>"/>&nbsp;<a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}" /></a></p>
        <div id="<c:out value="delegate-${task.id}" />" style="display: none;" >
            <stripes:form action="/actions/ResolveTask" >
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
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="COPY" />
                                 </c:url>" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                        <c:if test="${not task.inFlightPlan}">
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="FLIGHT" />
                                 </c:url>" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
                        </c:if>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="FINISH" />
                                 </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                        <a href="<c:url value="/actions/ResolveTask">
                                    <c:param name="taskId" value="${task.id}" />
                                    <c:param name="action" value="CANCEL" />
                                 </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                        <a href="#" onclick="show('<c:out value="delegate-${task.id}" />', '<c:out value="rtn-${task.id}" />'); return false" title="Delegate"><img alt="delegate" border="0" src="<c:url value="/images/delegate.png"/>"/></a>
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
                <a href="<c:url value="/actions/ResolveTask">
                            <c:param name="taskId" value="${task.id}" />
                            <c:param name="action" value="REMOVE" />
                        </c:url>" title="Erase">
                        <img border="0" src="<c:url value="/images/erase.png"/>" /></a>
              <a href="<c:url value="/actions/ViewTask">
                            <c:param name="id" value="${task.id}" />
                        </c:url>"><c:out value="${task.summary}"/></a><hr/></p>
        </td>
    </tr>
</c:forEach>
</table>
</c:if>	
</table>
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>