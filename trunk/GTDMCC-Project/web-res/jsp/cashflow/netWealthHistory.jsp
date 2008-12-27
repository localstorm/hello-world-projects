<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>NET WEALTH</span> history</h2>

<div align="center">
    <br/>
    <img src="<c:url value="/chart/netWealthHistory.png">
                    <c:param name="showTargets" value="false" />
              </c:url>"/>
    <br/><br/>
    <img src="<c:url value="/chart/netWealthHistory.png">
                    <c:param name="showTargets" value="true" />
              </c:url>"/>
</div>


<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>