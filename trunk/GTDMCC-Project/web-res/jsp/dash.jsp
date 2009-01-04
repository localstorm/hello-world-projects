<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/dashhdr.jsp" %>

<div id="dashBirthdaysPan">
    <h2><span>Birthdays</span> pane</h2>
	<div id="redDashPane">
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+2)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+3)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+7)</p>
	</div>
	<div id="yellowDashPane">
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+8)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+14)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+21)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+8)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+14)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+21)</p>
	</div>
	<div id="greenDashPane">
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+22)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+54)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+90)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+22)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+54)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+90)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+22)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+54)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+90)</p>
	</div>
</div>

<div id="dashGTDPan">
    <h2><span>GTD Battle Map</span> pane</h2>
	<div align="center">
		<table border="1" width="80%" class="dash">
			<tr class="dashHdr" ><th colspan="6">Consolidated Tasks Report</th></tr>
			<tr class="dashHdr">
                <th width="40%">Context name</th>
                <th width="10%">Pending</th>
                <th width="10%">Awaited</th>
                <th width="10%">Flight plan</th>
                <th width="10%"><img src="<c:url value="/images/redline.png"/>" /></th>
                <th width="10%"><img src="<c:url value="/images/deadline.png" />"/></th></tr>
                <c:forEach items="${gtdDashReport.rows}" var="row">
                    <tr>
                        <td onClick="document.location.href='<c:url value="/actions/ViewContext">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                </c:url>';"><c:out value="${row.contextName}" /></td>
                        <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                    <c:param name="filter" value="PENDING" />
                                                </c:url>';"><c:out value="${row.pending}" /></td>
                        <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                    <c:param name="filter" value="AWAITED" />
                                                </c:url>';"><c:out value="${row.awaited}" /></td>
                        <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                    <c:param name="filter" value="FLIGHT" />
                                                </c:url>';"><c:out value="${row.flightPlan}" /></td>
                        <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                    <c:param name="filter" value="REDLINE" />
                                                </c:url>';"><c:out value="${row.red}" /></td>
                        <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                    <c:param name="contextId" value="${row.contextId}" />
                                                    <c:param name="filter" value="DEADLINE" />
                                                </c:url>';"><c:out value="${row.dead}" /></td>
                    </tr>
                </c:forEach>
			<tr class="dashHdr">
                <th>Total</th>
                <th><c:out value="${gtdDashReport.totals.pending}" /></th>
                <th><c:out value="${gtdDashReport.totals.awaited}" /></th>
                <th><c:out value="${gtdDashReport.totals.flightPlan}" /></th>
                <th><c:out value="${gtdDashReport.totals.red}" /></th>
                <th><c:out value="${gtdDashReport.totals.dead}" /></th>
            </tr>
		</table>
 		</div>

</div>

<%--div id="dashGTD2Pan">
    <h2><span>Daily Tasks</span> pane</h2>
	<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+22)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+54)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+90)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+22)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Пафнутьев Ш. И.</a> (+54)</p>
		<p><img src="<c:url value="/images/person.png" />" /> <a href="#">Ваня Пупкин</a> (+90)</p>
</div--%>

<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>


