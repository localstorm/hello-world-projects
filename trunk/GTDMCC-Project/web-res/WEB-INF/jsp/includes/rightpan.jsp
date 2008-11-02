<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="include.jsp" %>

</div>
<div id="bodyRightPan">
  	<h2><span>Contexts</span> pane</h2>
	<p class="more"><a href="<c:url value="/actions/EditContexts"/>">EDIT</a></p>
        <table class="contexts">
        <c:forEach items="${contexts}" var="ctx" >            
            <tr>
                <td width="18px" valign="top"><img src="<c:url value="/images/button.png"/>"/></td>
                <td><a href="<c:url value="/actions/ViewContext">
                       <c:param name="contextId" value="${ctx.id}" />
                     </c:url>"><c:out value="${ctx.name}"/></a></td>
            </tr>
        </c:forEach>
        </table>            
	
        <h2><span>Reports</span> pane</h2>
            <%--ul class="reportsUL">
                <li><a href="#">Отчёт по дедлайнам</a></li>
            </ul--%>
            <table class="reports">
                <tr>
                    <td width="18px" valign="top"><img src="<c:url value="/images/report.png"/>"/></td>
                    <td><a href="#">Отчёт по дедлайнам</a></td>
                </tr>
            </table>
                <%--img src="<c:url value="/images/report.png"/>"/--%>
                <%--p class="more"><a href="#">ALL</a></p--%>
</div>
