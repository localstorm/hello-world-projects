<%@ page pageEncoding="UTF-8" language="java" %>

<%@ include file="include.jsp" %>

<stripes:form action="/actions/SetContextFilter" >
    <stripes:hidden name="returnPage" value="${param.returnPage}" />
    <stripes:select name="contextId" value="${filterCtx}" onchange="submit();">
        <stripes:option value="-1" >[Show all]</stripes:option>
        <c:forEach items="${contexts}" var="ctx" >
            <stripes:option value="${ctx.id}" ><c:out value="${ctx.name}"/></stripes:option>
        </c:forEach>
    </stripes:select>
</stripes:form>