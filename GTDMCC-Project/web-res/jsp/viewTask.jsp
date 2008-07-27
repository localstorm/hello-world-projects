<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>TASK</span> details</h2>

<stripes:form action="/actions/AddTask" focus="summary" >
<stripes:errors/>
<table width="100%">
<tr>
    <th colspan="2"><font color="red">This task was delegated to Santa</font>&nbsp;<a href="#" title="Clear note">[x]</a></th>
</tr>
<tr>
    <th colspan="2">Summary:</th>
</tr>
<tr>
    <td colspan="2"> <stripes:textarea rows="4" style="width: 100%;" name="summary" /></td>
</tr>
<tr>
    <th colspan="2">Details:</th>
</tr>
<tr>
    <td colspan="2"><stripes:textarea rows="10" style="width: 100%;" name="summary" /></td>
</tr>
<tr>
    <th colspan="2">Dates:</th>
</tr>
<tr>
    <td>Red&nbsp;Line:&nbsp;<stripes:text style="width: 100%;" name="redline" /></td>
    <td>Dead&nbsp;Line:&nbsp;<stripes:text style="width: 100%;" name="deadline" /></td>
</tr>
<tr>
    <td colspan="2" align="center"><stripes:submit name="submit" value="Apply" /></td>
</tr>
</table>     
</stripes:form>
        

<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>