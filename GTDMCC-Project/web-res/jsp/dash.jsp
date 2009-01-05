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
		<table border="1" width="90%" class="dash">
			<tr class="dashHdr" ><th colspan="12">Consolidated Tasks Report</th></tr>
			<tr class="dashHdr">
                <th width="45%">Context name</th>
                <th width="5%"><img src="<c:url value="/images/loex.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/loe1.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/loe2.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/loe3.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/loe4.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/loe5.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/awaited_dash.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/flight_dash.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/redline.png"/>" /></th>
                <th width="5%"><img src="<c:url value="/images/deadline.png" />"/></th>
                <th width="5%"><img src="<c:url value="/images/check.png" />"/></th>
            </tr>
            <c:set var="color" value="#FFFFFF" />
            <c:forEach items="${gtdDashReport.rows}" var="row">
                <tr bgcolor="<c:out value="${color}" />">
                    <td onClick="document.location.href='<c:url value="/actions/ViewContext">
                                                <c:param name="contextId" value="${row.contextId}" />
                                            </c:url>';"><c:out value="${row.contextName}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="PENDING" />
                                            </c:url>';"><c:out value="${row.pending}" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="ELEMENTARY" />
                                            </c:url>';"><c:out value="--" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="EASY" />
                                            </c:url>';"><c:out value="--" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="MEDIUM" />
                                            </c:url>';"><c:out value="--" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="DIFFICULT" />
                                            </c:url>';"><c:out value="--" /></td>
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="VERY_DIFFICULT" />
                                            </c:url>';"><c:out value="--" /></td>
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
                    <td onClick="document.location.href='<c:url value="/actions/BattleMapSupport">
                                                <c:param name="contextId" value="${row.contextId}" />
                                                <c:param name="filter" value="FIN" />
                                            </c:url>';"><c:out value="${row.done}" /></td>
                </tr>
                <c:choose>
                    <c:when test="${color eq '#FFFFFF'}" >
                        <c:set var="color" value="#EEEEFF" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="color" value="#FFFFFF" />
                    </c:otherwise>
                </c:choose>
            </c:forEach>
			<tr class="dashHdr">
                <th>Total</th>
                <th><c:out value="${gtdDashReport.totals.pending}" /></th>
                <th><c:out value="--" /></th>
                <th><c:out value="--" /></th>
                <th><c:out value="--" /></th>
                <th><c:out value="--" /></th>
                <th><c:out value="--" /></th>
                <th><c:out value="${gtdDashReport.totals.awaited}" /></th>
                <th><c:out value="${gtdDashReport.totals.flightPlan}" /></th>
                <th><c:out value="${gtdDashReport.totals.red}" /></th>
                <th><c:out value="${gtdDashReport.totals.dead}" /></th>
                <th><c:out value="${gtdDashReport.totals.done}" /></th>
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


