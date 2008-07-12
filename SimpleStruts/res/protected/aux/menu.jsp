<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<table width="100%" bgcolor="#DDDDDD">
    <tr>
        <th>
            <div align="center"><h4>&nbsp;Available&nbsp;actions:&nbsp;</h4></div>
        </th>
    </tr>
    <tr>
       <td>
           <div align="center">
           		<html:link action="/Users">Registered&nbsp;users</html:link>
           </div>
       </td> 
    </tr>
    <tr>
       <td>
           <div align="center"><a href=".">Tweak</a></div>
       </td> 
    </tr>
    <tr>
       <td>
           <div align="center"><a href=".">Report&nbsp;bug</a></div>
       </td> 
    </tr>
    <tr>
       <td>
           <div align="center">
           		<html:link module="" action="/Login">Logout</html:link>
           </div>
       </td> 
    </tr>
    <tr>
       <td>
           &nbsp;
       </td> 
    </tr>

</table>
