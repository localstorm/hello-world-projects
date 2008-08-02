<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="include.jsp" %>

</div>
<div id="bodyRightPan">
  	<h2><span>Contexts</span> pane</h2>
	<ul>
            <c:forEach items="${contexts}" var="ctx" >
                <li><a href="<c:url value="/actions/ViewContext">
                           <c:param name="id" value="${ctx.id}" />
                         </c:url>">${ctx.name}</a>
                </li>
            </c:forEach>
	</ul>
		<p class="more"><a href="<c:url value="/actions/EditContexts"/>">EDIT</a></p>
		<h3><span>Reference</span> pane</h3>
		<p class="boldtext">03 oct 2006</p>
		<p><a href="#">Экзамен по Кожанову</a></p>
		
	
		<p class="boldtext">03 oct 2006</p>
		<p><a href="#">Очередное скотомудилище</a></p>
		

		<p class="boldtext">03 oct 2006</p>
		<p><a href="#">Очередное скотомудилище</a></p>
		

		<p class="boldtext">03 oct 2006</p>
		<p><a href="#">Очередное скотомудилище</a></p>
		<p class="more"><a href="#">ALL</a></p>
</div>
