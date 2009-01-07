<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSETS</span> list</h2>
 <table width="100%">
    <tr>
        <td align="right">
            <c:if test="${actionBean.checkpointUpdateNeeded}">
                <a href="<c:url value="/actions/MakeCheckpoint" />" title="Net wealth or balance changed. Checkpoint needed."><img src="<c:url value="/images/check.png"/>" border="0" /></a>
            </c:if>
        </td>
    </tr>
</table>
<br/>
<div align="center">
<table width="80%" border="0px" >
    <tr><th colspan="2" align="center">Totals:</th></tr>
    <tr bgcolor="#F3F3F3">
        <td width="50%" align="right" >Net wealth:</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.netWealth}"/></td>
    </tr>
    <tr bgColor="#E4F1F3">
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
    <tr bgcolor="#F3F3F3">
        <td width="50%" align="right" >Buy cost (1 piece):</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.buy}"/></td>
    </tr>
    <tr bgColor="#E4F1F3">
        <td align="right">Sell cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.sell}"/></td>
    </tr>
    <c:if test="${not empty asset.currentCost.exchangeBuy}">
    <tr bgcolor="#F3F3F3">
        <td align="right">Buy for exchange cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.exchangeBuy}"/></td>
    </tr>
    </c:if>
    <c:if test="${not empty asset.currentCost.exchangeSell}">
    <tr bgColor="#E4F1F3">
        <td align="right" >Sell for exchange cost (1 piece):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.exchangeSell}"/></td>
    </tr>
    </c:if>
    <tr bgcolor="#F3F3F3">
        <td align="right" >Total amount (pieces):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="5" minFractionDigits="2" value="${asset.amount}"/></td>
    </tr>
    <tr bgColor="#E4F1F3">
        <td align="right" >Net wealth:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.netWealth}"/></td>
    </tr>
    <tr bgColor="#F3F3F3">
        <td align="right" >Investment amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.investmentsCost}"/></td>
    </tr>
    <tr bgColor="#E4F1F3">
        <td align="right" >Revenu amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.revenuAmount}"/></td>
    </tr>
    <tr bgColor="#F3F3F3">
        <td align="right" ><c:choose>
                <c:when test="${not asset.valuable.usedInBalance}"><s>Balance</s></c:when><c:otherwise>Balance</c:otherwise></c:choose>:
        </td>
        <td align="right" >
            <fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.balance}"/>
        </td>
    </tr>
</table>
</c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>