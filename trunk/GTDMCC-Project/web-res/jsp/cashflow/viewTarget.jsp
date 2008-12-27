<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/cashflow/hdr.jsp" %>

<h2><span>TARGET</span> details</h2>
<br/>
<div align="center">
<fmt:formatNumber var="buyCost" maxFractionDigits="2" minFractionDigits="2" value="${actionBean.targetResult.currentCost.buy}"/>

<stripes:form action="/actions/UpdateVOPrices">
    <stripes:errors/>
    <stripes:hidden name="valuableId" value="${actionBean.targetResult.valuable.id}" />

<table width="80%" border="0px" >
    <tr><th colspan="2" align="center"><c:out value="${actionBean.targetResult.name}"/></th></tr>
    <tr bgcolor="#FBFFBD">
        <td width="50%" align="right" >Buy cost (1 piece):</td>
        <td width="50%" align="right" ><stripes:text name="buy" id="buy-id" value="${buyCost}" style="width: 95%"/></td>
    </tr>
    <tr bgColor="#FBFFBD">
        <td colspan="2" align="center"><stripes:submit name="upd" value="Update"/>&nbsp;<stripes:reset name="rst" /></td>
    </tr>
    
    <tr bgcolor="#FFEABD">
        <td colspan="2">
        <table width="100%" border="0px">
            <tr>
                <td align="right"  border="0px">
                    <a href="<c:url value="/actions/ViewTargetCostHistory">
                                <c:param name="assetId" value="${actionBean.targetResult.id}"/>
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