<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/people/hdr.jsp" %>

<h2><span>PERSON</span> view</h2>
<div align="right" >
    <a href="<c:url value="/actions/ViewPersonGroup">
        <c:param name="groupId" value="${actionBean.groupId}" />
    </c:url>" title="Go to parent"><img src="<c:url value="/images/parent.png" />" border="0" /></a>&nbsp;<a href="#" onclick="show('editPersonDiv', 'name-id'); return false">Edit person</a>
</div>

    <div align="center">

    <div id="editPersonDiv" width="80%" style="display: <c:choose>
             <c:when test="${not empty actionBean.context.validationErrors}">inline</c:when>
             <c:otherwise>none</c:otherwise>
    </c:choose>;">
        <stripes:form action="/actions/UpdatePerson" >
        <stripes:errors/>
        <stripes:hidden name="personId" value="${actionBean.person.id}" />
        <table style="background:#FFFFD0; border:1px dotted #DADADA;" >
            <tr>
                <td>&nbsp;</td>
                <td>First Name: </td>
                <td><stripes:text name="firstName" id="name-id" style="width: 100%;" value="${actionBean.person.name}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Last Name: </td>
                <td><stripes:text name="lastName" id="lname-id" style="width: 100%;" value="${actionBean.person.lastName}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Patronymic Name: </td>
                <td><stripes:text name="patronymicName" id="pname-id" style="width: 100%;" value="${actionBean.person.patronymicName}" /></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>Birth date: </td>
                <td>--</td>
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
                    <stripes:submit name="submit" value="Ok" style="width: 7em;"/>&nbsp;
                    <stripes:submit name="cancel" value="Cancel" style="width: 7em;" onclick="hide('editPersonDiv'); return false" />
                </td>
                <td>&nbsp;</td>
            </tr>
        </table>
        </stripes:form>
</div>

</div>
<br/><br/>
<c:out value="${actionBean.person.fullName}" />

<%@ include file="/WEB-INF/jsp/includes/people/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>