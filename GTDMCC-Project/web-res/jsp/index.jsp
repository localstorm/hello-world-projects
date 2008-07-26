<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>

<%@ include file="/WEB-INF/jsp/includes/hdr.jsp" %>

<h2><span>FILGHT</span> plan</h2>
<div align="right" ><a href="#" title="Utilize &amp; build new"><img src="images/utilize.png" border="0" /></a><a href="#" title="Day before"><img src="images/backward.png" border="0" /></a>(25 oct 2008)<a href="#" title="Next day"><img src="images/forward.png" border="0"/></a></div> 
<p><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>
<div align="right"><a href="#" title="Expand"><img src="images/expand.png" border="0" /></a><a href="#" title="Finish"><img border="0" src="images/finish.png"/></a><a href="#" title="Cancel"><img border="0" src="images/cancel.png"/></a><a href="#" title="Delegate"><img border="0" src="images/delegate.png"/></a><a href="#" title="Delete"><img border="0" src="images/trash.png"/></a></div>

<p><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>
<div align="right"><a href="#" title="Expand"><img src="images/expand.png" border="0" /></a><a href="#" title="Finish"><img border="0" src="images/finish.png"/></a><a href="#" title="Cancel"><img border="0" src="images/cancel.png"/></a><a href="#" title="Delegate"><img border="0" src="images/delegate.png"/></a><a href="#" title="Delete"><img border="0" src="images/trash.png"/></a></div>

<p><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>
<div align="right"><a href="#" title="Expand"><img src="images/expand.png" border="0" /></a><a href="#" title="Finish"><img border="0" src="images/finish.png"/></a><a href="#" title="Cancel"><img border="0" src="images/cancel.png"/></a><a href="#" title="Delegate"><img border="0" src="images/delegate.png"/></a><a href="#" title="Delete"><img border="0" src="images/trash.png"/></a></div>

<p><a href="#" title="Not done"><img border="0" src="images/done.png" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

<p><a href="#" title="Not cancelled"><img border="0" src="images/cancelled.png" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

<p><a href="#" title="Not delegated"><img border="0" src="images/delegated.png" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>
	
<p><a href="#" title="Not deleted"><img border="0" src="images/deleted.png" /></a><span>@work:</span>&nbsp;diansduian au nd ue wne wenwen uqwen fnwqe fwenf uwe finwe fwqe ifniwen fweq nfiwqen iwne fiunwef inwef<hr/></p>

<p class="more"><a href="#">PRINT</a></p>
	

<h2><span>AFFECTED</span> lists</h2>
<div id="bookcatagories">
    <div id="nameonePan">
        <ul>
            <li>Operative TOBUY</li>
            <li>Work TODO</li>
            <li>Home TODO</li>
        </ul>   
    </div>
		
    <div id="priceonePan">
        <ul>
            <li>shops</li>
            <li>work</li>
            <li>home</li>
	</ul>
    </div>
	  
    <div id="discountonePan">
        <ul>
            <li><font color="black"><img src="images/active.png"/></font></li>
            <li><font color="darkgrey"><img src="images/inactive.png"/></font></li>
            <li><font color="black"><img src="images/active.png"/></font></li>
	</ul>
    </div>
</div>
  
<%@ include file="/WEB-INF/jsp/includes/rightpan.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/foot.jsp" %>

<%-- STRIPES --%>

<%-- Hello, World!

<jsp:useBean id="date" class="java.util.Date" />

Date: ${date} --%>

<%--!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
  <head><title>My First Stripe</title></head>
  <body>
    <h1>Stripes Calculator</h1>

    Hi, I'm the Stripes Calculator. I can only do addition. Maybe, some day, a nice programmer
    will come along and teach me how to do other things?

    
    
    
    <stripes:form action="/actions/Index" focus="">
        <table>
            <tr>
                <td>Number 1:</td>
                <td><stripes:text name="numberOne"/></td>
            </tr>
            <tr>
                <td>Number 2:</td>
                <td><stripes:text name="numberTwo"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <stripes:submit name="addition" value="Add"/>                    
                </td>
            </tr>
            <tr>
                <td>Result:</td>
                <td>${actionBean.result}</td>
            </tr>
        </table>
    </stripes:form>
  </body>
</html--%>




