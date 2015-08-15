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
        
            <h1>Sistema de Gesti�n de T�cnicos e Incidentes</h1>
        	${message}
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
        <th>per�odo</th>
        <th>A realizar el</th>
        <th>Responsable</th>
        <th>Contrato</th>
      </tr>
    </thead>
    <tbody>
    <c:forEach items="${actividades}" var="actividad" >
      <tr>
      <form class="form-control" action="/CounterWebApp/desktop/actividad/editarActividad" method="POST">
      	<td><input class="form-control" size="16" type="text" id="id" name="id" value="${actividad.id}" disabled></td>
        <td><input class="form-control" size="16" type="text" name="tipo" value="${actividad.tipo}" disabled></td>
        <c:choose>
        	<c:when test="${actividad.tipo == 'Periodica'}">
        		<td><input class="form-control" size="2" type="number" name="periodo" value="${actividad.periodo}" ></td>
        	</c:when>
        	<c:otherwise>
        		<td><input class="form-control" size="2" type="number" name="periodo" value="" disabled></td>
        			<input class="form-control" size="2" type="hidden" name="periodo" value="${actividad.periodo}" >
        	</c:otherwise>
        </c:choose>
        <td><input class="form-control" size="10" type="date" class="form_datetime" name="fecha" value="${actividad.fechaActividad}" ></td>
        <td>
        	<select class="form-control" id="inputUsuario" name="usuario">
							<option value="">S/A</option>
                            <c:forEach items="${usuarios}" var="usuario" >
                            	<c:choose>
        							<c:when test="${actividad.idUsuario == usuario.id}">
                        				<option selected="selected" value="${usuario.id}"><c:out value="${usuario.nombre}" /></option>
                        			</c:when>
        							<c:otherwise>
        								<option value="${usuario.id}"><c:out value="${usuario.nombre}" /></option>
        							</c:otherwise>
        					</c:choose>
                        	</c:forEach>
            </select>
        </td>
        <td><input class="form-control" size="16" type="text" name="contrato" value="${actividad.idContrato}" disabled></td>
        
        <input class="form-control" size="16" type="hidden" id="id" name="id" value="${actividad.id}" >
        <input class="form-control" size="16" type="hidden" name="tipo" value="${actividad.tipo}" >
        <input class="form-control" size="16" type="hidden" name="contrato" value="${actividad.idContrato}" >
        <td class="vert-align"><button class="btn " id="boton" type="submit"> <span class="glyphicon "></span>Editar</button> </td>
      </form>
      <form class="form-control" action="/CounterWebApp/desktop/actividad/eliminarActividad" method="POST">
       	<input class="form-control" size="16" type="hidden" id="id" name="id" value="${actividad.id}" >
      	<td class="vert-align"><button class="btn " id="boton" type="submit"> <span class="glyphicon "></span>Eliminar</button> </td>
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