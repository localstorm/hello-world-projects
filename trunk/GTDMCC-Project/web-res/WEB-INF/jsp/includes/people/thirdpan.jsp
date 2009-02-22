<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<div id="bodyThirdPan">
    <h2><span>Mail</span> lists</h2>
    <p class="more"><a href="<c:url value="/actions/EditMailLists"/>">EDIT</a></p>
    <table class="refobjs">
        <%--<c:forEach items="${targets}" var="target">--%>
            <jsp:include page="/WEB-INF/jsp/includes/people/ml.jsp">
                <jsp:param name="id"   value="1"   />
                <jsp:param name="name" value="La la" />
            </jsp:include>
        <%--</c:forEach>--%>
    </table>
</div>
