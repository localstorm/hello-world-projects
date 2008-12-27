<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>TASKS</span> search</h2>
    <div align="right" width="80%"><a href="#" onclick="show('tsDiv', 'text-id'); return false">Search again</a></div>
    <div align="center">

    <div id="tsDiv" width="80%" style="display: <c:choose>
             <c:when test="${not actionBean.found}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/SubmitTaskSearch">
        <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Text: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" value="${actionBean.text}" /></td>
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
                    <stripes:submit name="submit" value="Search" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('tsDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    <br/>
    <c:if test="${actionBean.found}">
	<table width="100%">
        <c:if test="${not empty actionBean.operativeTasks}">
            <tr>
                <th colspan="2">Operative</th>
            </tr>
        </c:if>
        <c:if test="${not empty actionBean.awaitedTasks}">
            <tr>
                <th colspan="2">Awaited</th>
            </tr>
        </c:if>
        <c:if test="${not empty actionBean.archiveTasks}">
            <tr>
                <th colspan="2">Archived</th>
            </tr>
        </c:if>
    </table>
    </c:if>

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>