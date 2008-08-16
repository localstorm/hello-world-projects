<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>FILGHT</span> plan</h2>
<div align="right" >
    <a href="#" title="Utilize &amp; build new"><img src="<c:url value="/images/utilize.png"/>" border="0" /></a>
    <a href="#" title="Day before"><img src="<c:url value="/images/backward.png"/>" border="0" /></a>
    (25 oct 2008)
    <a href="#" title="Next day"><img src="<c:url value="/images/forward.png"/>" border="0"/></a></div> 

<c:forEach items="${actionBean.flightPlanTasks}" var="task">
    <p><span><c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                </c:url>" title="Expand"><c:out value="${task.summary}" /></a></p>
<table width="100%">
    <tr>
        <td width="80%" ><hr/></td>
        <td width="20%" >
        <nobr>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="FINISH" />
            </c:url>" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="CANCEL" />
            </c:url>" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
            <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="DELEGATE" />
            </c:url>" title="Delegate"><img alt="delegate" border="0" src="<c:url value="/images/delegate.png"/>"/></a>
        </nobr>
        </td>
    </tr>
</table>
</c:forEach>
<c:if test="${not empty actionBean.flightPlanTasks}">
    <p class="more"><a href="#">PRINT</a></p>
</c:if>

<c:forEach items="${actionBean.archiveFlightPlanTasks}" var="task" >
<p>
    <a href="<c:url value="/actions/ResolveFlightTask" >
                <c:param name="taskId" value="${task.id}" />
                <c:param name="action" value="UNRESOLVE" />
            </c:url>" title="Unresolve"><img border="0" src="<c:choose><c:when test="${task.finished}"><c:url value="/images/done.png"/></c:when><c:when test="${task.cancelled}"><c:url value="/images/cancelled.png"/></c:when></c:choose>" /></a>
    <span><c:out value="${task.list.context.name}" />:</span>&nbsp;<a href="<c:url value="/actions/ViewTask">
                        <c:param name="id" value="${task.id}" />
                </c:url>"><c:out value="${task.summary} "/></a>
    <hr/>
</p>
</c:forEach>

<%--p><a href="#" title="Not cancelled"><img border="0" src="<c:url value="/images/cancelled.png"/>" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

<p><a href="#" title="Not delegated"><img border="0" src="<c:url value="/images/delegated.png"/>" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p--%>
	
<br/><br/>

<h2><span>AFFECTED</span> lists</h2>
<div id="bookcatagories">
    <div id="nameonePan">
        <ul>
            <li>Operative TOBUY</li>
            <li>Work TODO</li>
            <li>Home TODO</li>
        </ul>   
    </div>
		
    <div id="priceonePan">
        <ul>
            <li>shops</li>
            <li>work</li>
            <li>home</li>
	</ul>
    </div>
	  
    <div id="discountonePan">
        <ul>
            <li><font color="black"><img src="<c:url value="/images/active.png"/>"/></font></li>
            <li><font color="darkgrey"><img src="<c:url value="/images/inactive.png"/>"/></font></li>
            <li><font color="black"><img src="<c:url value="/images/active.png"/>"/></font></li>
	</ul>
    </div>
</div>
  
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>