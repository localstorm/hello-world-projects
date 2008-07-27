<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>LIST</span> tasks</h2>
<div align="right" ><a href="#" title="Paste task"><img src="<c:url value="/images/paste.png" />" border="0" /></a>&nbsp;(My mega cool and very long list name)</div> 

<table width="100%">
<tr>
    <th colspan="2">Operative</th>
</tr>
<tr> 
    <td>
        <p>diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef</p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewTask">
                                <c:param name="id" value="1" />
                             </c:url>" title="Expand"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="#" title="Append To Flight Plan"><img alt="flight" border="0" src="<c:url value="/images/flight.png"/>"/></a>
                    <a href="#" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="#" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                    <a href="#" title="Delegate"><img border="0" src="<c:url value="/images/delegate.png"/>"/></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr> 
    <td>
        <p>diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef</p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewList">
                                <c:param name="id" value="1" />
                             </c:url>" title="Expand"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="#" title="Finish"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="#" title="Cancel"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                    <a href="#" title="Delegate"><img border="0" src="<c:url value="/images/delegate.png"/>"/></a>
                </nobr>
                </td>
            </tr>
        </table>
        <p class="more"><a href="#">PRINT</a></p>
    </td>
</tr>
<tr>
   <td colspan="2" align="center">
    <br/>
        <stripes:form action="/actions/AddTask" focus="name" >
        <stripes:errors/>
        <stripes:hidden name="listId" value="1" />
            Summary: <stripes:text name="summary"/>&nbsp;<stripes:submit name="submit" value="Create" />
        </stripes:form>
     <br/>
     <hr/>
     
    </td>
</tr>
<tr>
    <th colspan="2">Archive</th>
</tr>
<tr> 
    <td>
	<p><a href="#" title="Not done"><img border="0" src="<c:url value="/images/done.png"/>" /></a>diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

	<p><a href="#" title="Not cancelled"><img border="0" src="<c:url value="/images/cancelled.png"/>" /></a>diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

	<p><a href="#" title="Not delegated"><img border="0" src="<c:url value="/images/delegated.png"/>" /></a>diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>
	
    </td>
</tr> 
</table>
	

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>