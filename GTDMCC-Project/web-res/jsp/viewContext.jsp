<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<%@ include file="clipboard.jsp" %>
<h2><span>CONTEXT</span> details</h2>
<div align="right" width="80%"><a href="#" onclick="show('addLISTDiv'); return false">Add list</a> (<c:out value="${actionBean.contextResult.name}"/>)</div>
<div align="center">
    
    <div id="addLISTDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
    <stripes:form action="/actions/AddList" focus="name" >
        <stripes:hidden name="contextId" value="${actionBean.contextResult.id}" />
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Name: </td>
                <td><stripes:text name="name" style="width: 100%;" /></td>
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
                    <stripes:submit name="submit" value="Add" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('addLISTDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </stripes:form>
    </div>
</div>

<c:if test="${not empty actionBean.pinnedLists}">
<br/>
<div width="100%" style="border:1px dotted #DADADA; padding:0 3px 6px;" >
<c:forEach items="${actionBean.pinnedLists}" var="list" >
    <p><a title="Pin/Unpin" href="<c:url value="/actions/ResolveList">
                    <c:param name="listId" value="${list.id}" />
                    <c:param name="action" value="PIN" />
                 </c:url>"><img border="0" src="<c:url value="/images/pin.png"/>"/></a><span><a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                 </c:url>"><c:out value="${list.name}"/></a></span></p>
</c:forEach>    
</div>
</c:if>

<table width="100%">
<c:if test="${not empty actionBean.contextLists}">
<tr>
    <th colspan="2">Operative</th>
</tr>
<c:forEach items="${actionBean.contextLists}" var="list" >
<tr> 
    <td>
        <p><a href="<c:url value="/actions/ResolveList">
                        <c:param name="listId" value="${list.id}" />
                        <c:param name="action" value="PIN" />
                    </c:url>"><img border="0" src="<c:url value="/images/pin.png"/>"/></a><span><a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                    </c:url>"><c:out value="${list.name}"/></a></span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="20%" >
                <nobr>
                    <a href="<c:url value="/actions/ResolveList">
                                <c:param name="listId" value="${list.id}" />
                                <c:param name="action" value="COPY" />
                             </c:url>" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
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
</c:if>

<c:if test="${not empty actionBean.archivedLists}">
<tr>
    <th colspan="2">Archive</th>
</tr>
<c:forEach items="${actionBean.archivedLists}" var="list" >
<tr> 
    <td>
        <p><a href="<c:url value="/actions/ResolveList">
                        <c:param name="listId" value="${list.id}" />
                        <c:param name="action" value="UNRESOLVE" />
                    </c:url>" title="Undo"><img alt="Undo" border="0" src="<c:url value="/images/deleted.png"/>"/></a>
                    <span><a href="<c:url value="/images/partially-finished.png"/>" />&nbsp;<a href="<c:url value="/actions/ViewList">
                                <c:param name="listId" value="${list.id}" />
                    </c:url>"><c:out value="${list.name}"/></a></span></p>
        <table width="100%">
            <tr>
                <td width="80%" ><hr/></td>
                <td width="10%" >
                <nobr>
                    <a href="#" title="Cut"><img alt="cut" src="<c:url value="/images/cut.png"/>" border="0" /></a>
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
</c:if>
</table>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>