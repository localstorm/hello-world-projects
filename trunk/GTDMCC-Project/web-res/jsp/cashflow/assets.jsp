<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<div align="center">

<c:forEach items="${actionBean.assets}" var="asset">
<table width="80%" border="0px" >
    <tr><th colspan="2" align="center"><a hre="#"><c:out value="${asset.name}"/></a></th></tr>
    <tr bgcolor="#FBFFBD">
        <td width="50%" align="right" >Buy cost (1 piece):</td>
        <td width="50%" align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.buy}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right">Sell cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.sell}"/></td>
    </tr>
    <c:if test="${(not empty asset.currentCost.exchangeBuy) and (not empty asset.currentCost.exchangeSell)}">
    <tr bgcolor="#FBFFBD">
        <td align="right">Buy for exchange cost (1 piece):</td>
        <td align="right"><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.currentCost.exchangeBuy}"/></td>
    </tr>
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
        <td align="right" >Investments cost:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.investmentsCost}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Net profit:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${asset.netWealth-asset.investmentsCost}"/></td>
    </tr>
    <tr bgcolor="#FFEABD">
        <td colspan="2">
        <table width="100%" border="0px">
            <tr>
                <td align="left" border="0px">
                    <img src="<c:url value="/images/buy.png"/>"/>
                    <c:if test="${not empty asset.currentCost.exchangeBuy}">
                        <img src="<c:url value="/images/exchange_buy.png"/>"/>
                    </c:if>
                    <img src="<c:url value="/images/sell.png"/>"/>
                    <c:if test="${not empty asset.currentCost.exchangeSell}">
                        <img src="<c:url value="/images/exchange_sell.png"/>"/>
                    </c:if>
                </td>
                <td align="right"  border="0px">
                    <img src="<c:url value="/images/history.png"/>"/>
                </td>
            </tr>
        </table>
        </td>
    </tr>
</table>
</c:forEach>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>