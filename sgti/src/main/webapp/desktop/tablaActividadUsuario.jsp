<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet">
    <%@ include file="header.jsp" %>
    <%
		String usuarioSesion=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuarioSesion==null)
		{
			response.sendRedirect("/CounterWebApp/desktop/login2.jsp");
		}
	%>
      
</head>
<body>
     <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        
        </div>
    
    </header>   
</head>
<body>
    <div class="container">
  <table id="mt" class="table table-hover">
    <thead>
      <tr>
      	<th>Id</th>
        <th>Tipo</th>
        <th>período</th>
        <th>A realizar el</th>
        <th>Contrato</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${actividades}" var="actividad" >
      <tr>
      <form class="form-control" action="/CounterWebApp/desktop/actividad/editarActividad" method="POST">
      	<td><input class="form-control" size="16" type="text" id="id" name="id" value="${actividad.id}" disabled></td>
        <td><input class="form-control" size="16" type="text" name="tipo" value="${actividad.tipo}" disabled></td>
        <td><input class="form-control" size="2" type="number" name="periodo" value="${actividad.periodo}" disabled></td>
        <td><input class="form-control" size="10" type="date" class="form_datetime" name="fecha" value="${actividad.fechaActividad}" disabled></td>
        </td>
        <td><input class="form-control" size="16" type="text" name="contrato" value="${actividad.idContrato}" disabled></td>
      </form>
      </tr>
     </c:forEach>
    </tbody>
  </table>
<!--
  <br>
  <button id="addRow" class="btn btn-primary">
    Add row
  </div> -->
</div>
</body>
</html>