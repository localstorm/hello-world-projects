<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>


<script language="javascript1.2">

var rlinec = new CodeThatCalendar(caldef1);
var dlinec = new CodeThatCalendar(caldef1);

</script>

<h2><span>TASK</span> details</h2>

<stripes:form action="/actions/UpdateTask" focus="summary" name="taskForm" >
<stripes:errors/>
<stripes:hidden name="taskId" value="${actionBean.taskResult.id}" />
<stripes:hidden name="returnPage" value="${actionBean.returnPage}" />
<table width="100%">

<c:if test="${not empty actionBean.taskResult.runtimeNote}"><tr>
    <th colspan="2"><font color="red"><c:out value="${actionBean.taskResult.runtimeNote}" /></font></th>
</tr>
</c:if>
<tr>
    <th colspan="2" align="left">
        <table width="100%">
            <tr align="left">
                <th>Summary:</th>
                <th width="20%" align="right">
                    <stripes:select name="effort" value="${actionBean.taskResult.effort}">
                        <stripes:option value="1">Elementary</stripes:option>
                        <stripes:option value="2">Easy</stripes:option>
                        <stripes:option value="3">Medium</stripes:option>
                        <stripes:option value="4">Difficult</stripes:option>
                        <stripes:option value="5">Very difficult</stripes:option>
                    </stripes:select>
                </th>
            </tr>
        </table>
    </th>
</tr>
<tr>
    <td colspan="2"> <stripes:textarea rows="4" style="width: 100%;" name="summary" value="${actionBean.taskResult.summary}"/></td>
</tr>
<tr>
    <th colspan="2" align="left">Details:</th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="10" style="width: 100%;" name="details" value="${actionBean.taskResult.details}"/></td>
</tr>
<tr>
    <th colspan="2" align="left">Dates:</th>
</tr>
<tr>
    <td>
        <table width="100%">
            <td bgcolor="red"><font color="yellow">Red&nbsp;Line:</font></td> 
            <td><stripes:text readonly="true" name="redline" size="10" value="${actionBean.redline}" /></td> 
            <td><nobr><img onclick="rlinec.popup('redline');" border="0px" src="<c:url value="/images/calendar.png"/>" /><img onclick="document.taskForm.redline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" /></nobr></td>
        </table>
    </td>
    <td>
        <table width="100%">
            <td bgcolor="black"><font color="white">Dead&nbsp;Line:</font></td> 
            <td><stripes:text readonly="true" name="deadline" size="10" value="${actionBean.deadline}" /></td> 
            <td><nobr><img onclick="dlinec.popup('deadline');" border="0px" src="<c:url value="/images/calendar.png"/>" /><img onclick="document.taskForm.deadline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" /></nobr></td>
        </table>
    </td>
</tr>
<tr>
    <td colspan="2" align="center"><stripes:submit name="submit" value="Apply" />&nbsp;<stripes:reset name="reset"/></td>
</tr>
</table>     
</stripes:form>
        

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>