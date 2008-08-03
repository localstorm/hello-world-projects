<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>CONTEXT</span> details</h2>

<div align="right" ><a href="#" title="Paste list"><img src="<c:url value="/images/paste.png"/>" border="0" /></a> (<a href="<c:url value="/actions/ViewContext">
                                <c:param name="contextId" value="${actionBean.contextResult.id}" />
                        </c:url>">${actionBean.contextResult.name}</a>)</div> 

<table width="100%">
<tr>
    <th colspan="2">Operative</th>
</tr>
<c:forEach items="${actionBean.contextLists}" var="list" >
<tr> 
    <td>
        <p><span><a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                          </c:url>">${list.name}</a></span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                             </c:url>" title="View tasks"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ResolveList">
                                <c:param name="listId" value="${list.id}" />
                                <c:param name="action" value="FINISH" />
                             </c:url>" title="Finish all tasks"><img alt="finish" border="0" src="<c:url value="/images/finish.png"/>"/></a>
                    <a href="<c:url value="/actions/ResolveList">
                                <c:param name="listId" value="${list.id}" />
                                <c:param name="action" value="CANCEL" />
                             </c:url>" title="Cancel all tasks"><img alt="cancel" border="0" src="<c:url value="/images/cancel.png"/>"/></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
</c:forEach>
<tr>
    <td colspan="2" align="center">
    <br/>
        <stripes:form action="/actions/AddList" focus="name" >
        <stripes:errors/>
        <stripes:hidden name="contextId" value="${actionBean.contextResult.id}" />
            Name: <stripes:text name="name"/>&nbsp;<stripes:submit name="submit" value="Create" />
        </stripes:form>
     <br/>
     <hr/>
     </td>
</tr>     
<tr>
    <th colspan="2">Archive</th>
</tr>
<c:forEach items="${actionBean.archivedLists}" var="list" >
<tr> 
    <td>
        <p><a href="<c:url value="/actions/ResolveList">
                        <c:param name="listId" value="${list.id}" />
                        <c:param name="action" value="UNRESOLVE" />
                    </c:url>" title="Undo"><img alt="Undo" border="0" src="<c:url value="/images/deleted.png"/>"/></a> <span>${list.name}</span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="10%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                             </c:url>" title="Expand"><img alt="expand" src="<c:url value="/images/expand.png"/>" border="0" /></a>
                    <a href="<c:url value="/actions/ResolveList">
                                <c:param name="listId" value="${list.id}" />
                                <c:param name="action" value="ERASE" />
                            </c:url>" title="Delete Permanently"><img alt="delete" src="<c:url value="/images/erase.png"/>" border="0" /></a>
                </nobr>
                </td>
            </tr>
        </table>
    </td>
</tr>    
</c:forEach> 
</table>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>