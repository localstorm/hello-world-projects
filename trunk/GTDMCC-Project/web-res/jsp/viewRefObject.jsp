<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>OBJECT</span> details</h2>
    
    <div align="right" width="80%">Attach&nbsp;<a href="#" onclick="show('attachURLDiv', 'text-id'); hide('attachFileDiv'); return false">URL</a> |
    <a href="#" onclick="show('attachFileDiv', 'desc-id'); hide('attachURLDiv'); return false">File</a> (<c:out value="${actionBean.objectResult.name}"/>)</div>
    <div align="center">
    
    <div id="attachURLDiv" width="80%" style="display:
            <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
            </c:choose>;">
        <stripes:form action="/actions/AttachRefObj" >
            <stripes:hidden name="attachmentType" value="URL" />
            <stripes:hidden name="objectId" value="${actionBean.objectResult.id}" />
            <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Description: </td>
                <td><stripes:text name="description" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>URL: </td>
                <td><stripes:text name="text" id="text-id" style="width: 100%;" /></td>
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
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('attachURLDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    <div id="attachFileDiv" width="80%" style="display:
            <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
            </c:choose>;">
        <stripes:form action="/actions/AttachRefObj" >
            <stripes:hidden name="attachmentType" value="FILE" />
            <stripes:hidden name="objectId" value="${actionBean.objectResult.id}" />
            <stripes:errors/>
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>Description: </td>
                <td><stripes:text name="description" id="desc-id" style="width: 100%;" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>File: </td>
                <td><stripes:file name="file" style="width: 100%;" /></td>
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
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('attachFileDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
    </div>
    </div>
    
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>