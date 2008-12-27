<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>ASSET</span> details</h2>
<br/>
<div align="center">
<fmt:formatNumber var="buyCost" maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.currentCost.buy}"/>
<fmt:formatNumber var="sellCost" maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.currentCost.sell}"/>
<fmt:formatNumber var="buyFxCost" maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.currentCost.exchangeBuy}"/>
<fmt:formatNumber var="sellFxCost" maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.currentCost.exchangeSell}"/>

<div align="center">
    <div id="buyDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and actionBean.operationName eq 'BUY'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="BUY" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</td>
                <th colspan="2">Buying asset</td>
                <th>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="buy-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="buy-comment-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Buy" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('buyDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="sellDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and actionBean.operationName eq 'SELL'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="SELL" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</td>
                <th colspan="2">Selling asset</td>
                <th>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="sell-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="sell-comment-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Sell" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('sellDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="sellFxDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and actionBean.operationName eq 'SELL_FX'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="SELL_FX" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</td>
                <th colspan="2">Selling asset for exchange</td>
                <th>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="sellFx-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="sellFx-comment-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Sell" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('sellFxDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="buyFxDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors and actionBean.operationName eq 'BUY_FX'}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/OperateAsset">
        <stripes:errors/>
        <stripes:hidden name="operationName" value="BUY_FX" />
        <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <th>&nbsp;</td>
                <th colspan="2">Buying asset for exchange</td>
                <th>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Amount: </td>
                <td><stripes:text name="amount" id="buyFx-amount-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Comment: </td>
                <td><stripes:text name="comment" id="buyFx-comment-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Buy" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('buyFxDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
</div>

<stripes:form action="/actions/UpdateAssetPrices">
    <stripes:errors/>
    <stripes:hidden name="assetId" value="${actionBean.assetResult.id}" />

<table width="80%" border="0px" >
    <tr><th colspan="2" align="center"><c:out value="${actionBean.assetResult.name}"/> (<a href="<c:url value="/actions/ViewOperations">
                                                                                            <c:param name="assetId" value="${actionBean.assetResult.id}" />
                                                                                        </c:url>">Operations</a>)</th></tr>
    <tr bgcolor="#FBFFBD">
        <td width="50%" align="right" >Buy cost (1 piece):</td>
        <td width="50%" align="right" ><stripes:text name="buy" id="buy-id" value="${buyCost}" style="width: 95%"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right">Sell cost (1 piece):</td>
        <td align="right"><stripes:text name="sell" id="sell-id" value="${sellCost}" style="width: 95%"/></td>
    </tr>
    <tr bgcolor="#FBFFBD">
        <td align="right">Buy for exchange cost (1 piece):</td>
        <td align="right"><stripes:text name="buyFx" id="buy-fx-id" value="${buyFxCost}" style="width: 95%"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Sell for exchange cost (1 piece):</td>
        <td align="right" ><stripes:text name="sellFx" id="sell-fx-id" value="${sellFxCost}" style="width: 95%"/></td>
    </tr>
    <tr bgcolor="#FBFFBD">
        <td align="right" >Total amount (pieces):</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="5" minFractionDigits="2" value="${actionBean.assetResult.amount}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Net wealth:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.netWealth}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Investment amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.investmentsCost}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Revenu amount:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.revenuAmount}"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td align="right" >Balance:</td>
        <td align="right" ><fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${actionBean.assetResult.balance}"/></td>
    </tr>
    <tr bgColor="#DFFFBF">
        <td align="right" >Use in total balance:</td>
        <td align="right" ><stripes:checkbox name="usedInBalance" checked="${actionBean.assetResult.valuable.usedInBalance}" /></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td colspan="2" align="center"><stripes:submit name="upd" value="Update"/>&nbsp;<stripes:reset name="rst" /></td>
    </tr>
    
    <tr bgcolor="#FFEABD">
        <td colspan="2">
        <table width="100%" border="0px">
            <tr>
                <td align="left" border="0px">
                    <a href="#" onclick="show('buyDiv', 'buy-amount-id'); hide('buyFxDiv'); hide('sellFxDiv'); hide('sellDiv'); return false"><img border="0" src="<c:url value="/images/buy.png"/>"/></a>
                    <c:if test="${not empty actionBean.assetResult.currentCost.exchangeBuy}">
                        <a href="#" onclick="show('buyFxDiv', 'buyFx-amount-id'); hide('buyDiv'); hide('sellFxDiv'); hide('sellDiv'); return false"><img border="0" src="<c:url value="/images/exchange_buy.png"/>"/></a>
                    </c:if>
                    <a href="#" onclick="show('sellDiv', 'sell-amount-id'); hide('buyFxDiv'); hide('sellFxDiv'); hide('buyDiv');  return false"><img border="0"src="<c:url value="/images/sell.png"/>"/></a>
                    <c:if test="${not empty actionBean.assetResult.currentCost.exchangeSell}">
                        <a href="#" onclick="show('sellFxDiv', 'sellFx-amount-id'); hide('buyFxDiv'); hide('buyDiv'); hide('sellDiv'); return false"><img border="0" src="<c:url value="/images/exchange_sell.png"/>"/></a>
                    </c:if>
                </td>
                <td align="right"  border="0px">
                    <a href="<c:url value="/actions/ViewAssetCostHistory">
                                <c:param name="assetId" value="${actionBean.assetResult.id}"/>
                        </c:url>"><img border="0" src="<c:url value="/images/history.png"/>"/></a>
                </td>
            </tr>
        </table>
        </td>
    </tr>
</table>
</stripes:form>
</div>

<%@ include file="/WEB-INF/jsp/includes/cashflow/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>