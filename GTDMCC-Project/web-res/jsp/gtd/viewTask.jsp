<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>TASK</span> details</h2>

<script type="text/javascript">
    function hints()
    {
        show('hintsDiv');
        hide('datesDiv');
        /*hide('dates2Div');*/
    }

    function dates()
    {
        hide('hintsDiv');
        show('datesDiv');
        /*show('dates2Div');*/
    }
</script>
<stripes:form action="/actions/UpdateTask" focus="summary" name="taskForm" >
<stripes:errors/>
<stripes:hidden name="taskId" value="${actionBean.taskResult.id}" />
<stripes:hidden name="returnPageToken" value="${param.returnPageToken}" />
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
                        <c:forEach items="${actionBean.efforts}" var="effort">
                            <stripes:option value="${effort.effort}"><c:out value="${effort.latinName}"/></stripes:option>
                        </c:forEach>
                    </stripes:select>
                </th>
            </tr>
        </table>
    </th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="4" style="width: 100%;" name="summary" value="${actionBean.taskResult.summary}"/></td>
</tr>
<tr>
    <th colspan="2" align="left">Details:</th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="6" style="width: 100%;" name="details" value="${actionBean.taskResult.details}"/></td>
</tr>
<tr>
    <th colspan="2" align="left">
        Dates (<a href="#">Hints</a>):
    </th>
</tr>
<%--tr <c:if test="${not actionBean.taskResult.hinted}">style="display: none;"</c:if> id="hintsTR">
    <td colspan="2">
        <table width="100%" border="1px">
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every year</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Sunday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every month</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Monday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every week</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Tuesday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every day</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Wednesday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint after the weekend</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Thursday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint at the weekend</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Friday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint at Sun,Sat.</td>
                <td><stripes:checkbox name="hints" />&nbsp;Hint every Saturday</td>
            </tr>
            <tr>
                <td><stripes:checkbox name="hints" />&nbsp;Hint at Mon-Fri</td>
                <td>&nbsp;</td>
            </tr>
        </table>
    </td>
</tr--%>
<tr>
    <td>
        <table width="100%">
            <tr>
                <td bgcolor="red"><font color="yellow">Red&nbsp;Line:</font></td>
                <td>
                    <stripes:text readonly="true" name="redline" id="r1" size="10" value="${actionBean.redline}"/><img onclick="showCalendar('r1', '%d.%m.%Y', false, true);" border="0px" src="<c:url value="/images/calendar.png"/>" />&nbsp;<img onclick="document.taskForm.redline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" />
                </td>
            </tr>
        </table>
    </td>
    <td>
        <table width="100%">
            <tr>
                <td bgcolor="black"><font color="white">Dead&nbsp;Line:</font></td>
                <td>
                    <stripes:text readonly="true" name="deadline" id="d1" size="10" value="${actionBean.deadline}"/><img onclick="showCalendar('d1', '%d.%m.%Y', false, true);" border="0px" src="<c:url value="/images/calendar.png"/>" />&nbsp;<img onclick="document.taskForm.deadline.value = '';" border="0px" src="<c:url value="/images/nocalendar.png"/>" />
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td colspan="2" align="center"><stripes:submit name="submit" value="Apply" />&nbsp;<stripes:reset name="reset"/></td>
</tr>
</table>
</stripes:form>



<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>