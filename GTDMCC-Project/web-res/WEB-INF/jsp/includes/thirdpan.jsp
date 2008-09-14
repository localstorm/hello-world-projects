<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="include.jsp" %>

<div id="bodyThirdPan">
       <h2><span>Reference</span> pane</h2>       
       <ul>
           <c:forEach items="${refObjects}" var="ro">
               <li><a href="<c:url value="/actions/ViewRefObj">
                                <c:param name="objectId" value="${ro.id}"/>
                            </c:url>"><c:out value="${ro.name}" /></a></li>
           </c:forEach>
       </ul>   
    
    <p class="more"><a href="<c:url value="/actions/EditRefObj"/>">EDIT</a></p>
    
</div>
