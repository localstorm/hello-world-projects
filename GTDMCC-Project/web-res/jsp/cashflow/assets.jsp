<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSETS</span> list</h2>
<br/>
<div align="center">
<table width="80%" border="0px" >
    <tr><th colspan="2" align="center">Totals:</th></tr>
    <tr bgcolor="#FBFFBD">
        <td width="50%" align="right" >Net wealth:</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.netWealth}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td width="50%" align="right" >Balance:</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.balance}"/></td>
    </tr>
</table>
<hr/>
<c:forEach items="${actionBean.assets}" var="asset">
<table width="80%" border="0px" >
    <tr><th colspan="2" align="center"><a href="<c:url value="/actions/ViewAsset">
                                                    <c:param name="assetId" value="${asset.id}" />
                                                </c:url>"><c:out value="${asset.name}"/></a></th></tr>
    <tr bgcolor="#FBFFBD">
        <td width="50%" align="right" >Buy cost (1 piece):</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.buy}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right">Sell cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.sell}"/></td>
    </tr>
    <c:if test="${not empty asset.currentCost.exchangeBuy}">
    <tr bgcolor="#FBFFBD">
        <td align="right">Buy for exchange cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.exchangeBuy}"/></td>
    </tr>
    </c:if>
    <c:if test="${not empty asset.currentCost.exchangeSell}">
    <tr bgColor="#DFFFBF">
        <td align="right" >Sell for exchange cost (1 piece):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.exchangeSell}"/></td>
    </tr>
    </c:if>
    <tr bgcolor="#FBFFBD">
        <td align="right" >Total amount (pieces):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="5" minFractionDigits="2" value="${asset.amount}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Net wealth:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.netWealth}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Investment amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.investmentsCost}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Revenu amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.revenuAmount}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Balance:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.balance}"/></td>
    </tr>
</table>
</c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>