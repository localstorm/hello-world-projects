<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>CONTEXT</span> details</h2>
<div align="right" ><a href="#" title="Paste list"><img src="<c:url value="/images/paste.png"/>" border="0" /></a> (<a href="<c:url value="/actions/ViewList">
                                <c:param name="id" value="1" />
                        </c:url>">University</a>)</div> 

<table width="100%">
<tr>
    <th colspan="2">Operative</th>
</tr>
<tr> 
    <td>
        <p><span>What to buy at the shop</span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewList">
                                <c:param name="id" value="1" />
                             </c:url>" title="View tasks"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="#" title="Finish all tasks"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="#" title="Cancel all tasks"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
<tr> 
    <td>
        <p><span>What to buy at the shop</span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewList">
                                <c:param name="id" value="1" />
                             </c:url>" title="View tasks"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="#" title="Finish all tasks"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="#" title="Cancel all tasks"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
<tr>
    <td colspan="2" align="center">
    <br/>
        <stripes:form action="/actions/AddList" focus="name" >
        <stripes:errors/>
        <stripes:hidden name="contextId" value="1" />
            Name: <stripes:text name="name"/>&nbsp;<stripes:submit name="submit" value="Create" />
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
        <p><a href="#" title="Undo"><img alt="Undo" border="0" src="<c:url value="/images/cancelled.png"/>"/></a><span>What to buy at the shop</span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="10%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="#" title="Expand"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
<tr> 
    <td>
        <p><a href="#" title="Undo"><img alt="Undo" border="0" src="<c:url value="/images/deleted.png"/>"/></a> <span>What to buy at the shop</span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="10%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="#" title="Expand"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
</table>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>