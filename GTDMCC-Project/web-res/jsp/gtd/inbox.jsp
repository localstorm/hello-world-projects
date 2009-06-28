<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/gtd/hdr.jsp" %>

<h2><span>INBOX</span></h2>
<br/>
<c:if test="${not empty actionBean.inbox}">
<c:forEach items="${actionBean.inbox}" var="entry">
    <p><c:out value="${entry.content}"/></p>
    <hr/>
</c:forEach>
</c:if>
 
<%@ include file="/WEB-INF/jsp/includes/gtd/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>