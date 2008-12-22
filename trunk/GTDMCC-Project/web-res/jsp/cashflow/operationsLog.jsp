<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>OPERATIONS</span> history</h2>
<div align="right" width="80%">(<a href="<c:url value="/actions/ViewAsset">
                                            <c:param name="assetId" value="${actionBean.asset.id}"/>
                                         </c:url>"><c:out value="${actionBean.asset.name}"/></a>)</div>
<br/>
<div align="center">
<table width="90%" border="0px" >
    <tr bgcolor="#DFFFBF">
        <th>Type</th>
        <th>Amount</th>
        <th width="50%">Comment</th>
        <th>Date</th>
        <th>Action</th>
    </tr>
<c:forEach items="${actionBean.operations}" var="op">
    <tr bgcolor="#FBFFBD">
        <td width="5%" align="center" ><img src="<c:url value="/images/op_${op.type}.png"/>"/></td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${op.amount}"/></td>
        <td align="right" ><c:out value="${op.comment}" /></td>
        <td align="right" ><c:out value="${op.operationDate}" /></td>
        <td align="center" ><a title="Revoke operation" href="<c:url value="/actions/RevokeOperation">
                                        <c:param name="operationId" value="${op.id}" />
                                     </c:url>"><img border="0" src="<c:url value="/images/revoke.png"/>" /></a></td>
    </tr>
</c:forEach>
</table>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>