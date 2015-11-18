<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
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
 
<c:if test="${not empty message}">
  <div class="alert alert-success">
    ${message}
  </div>
</c:if>

<c:if test="${not empty errorMessage}">
  <div class="alert alert-danger">
    ${errorMessage}
  </div>
</c:if>

<div class="container">
  <br>
  <form action="/CounterWebApp/desktop/actividad/tablaFiltradaTecnico" method="POST">
    <div class="row">
      <div class="col-lg-8">
        <input class="form-control" id="system-search" name="q" placeholder="Buscar...">
      </div>
    
      <div class="col-lg-4">
        <div class="input-group">
          <fmt:formatDate value="${fechaDesde}" var="formattedfechaDesde" type="date" pattern="yyyy-MM-dd" />
          <input type="date" class="form-control form_datetime" name="fechaDesde" value="${formattedfechaDesde}" />
          <span class="input-group-btn">
              <button class="btn btn-primary glyphicon glyphicon-search" type="submit"></button>
          </span>
        </div>  
      </div>
      <br>
    </div>
</form>
      
  <table id="datatable" class="table table-hover table-list-search">
    <thead>
      <tr>
        <th>Id</th>
        <th>Tipo</th>
        <th>período</th>
        <th>A realizar el</th>
        <th>Responsable</th>
        <th>Contrato</th>
        <th>Descripción</th>
        <th>Estado</th>
      </tr>
    </thead>
    <tbody class="searchable">
    <c:forEach items="${actividades}" var="actividad" >
      <tr>
      <form class="form-control" action="/CounterWebApp/desktop/actividad/autoasignarTecnico" method="POST">
        <td><input class="form-control" size="16" type="text" id="id" name="id" value="${actividad.id}" disabled="disabled"></td>
        <td name="id" hidden="hidden">${actividad.id}</td>
        <td><input class="form-control" size="16" type="text" name="tipo" value="${actividad.tipo}" disabled="disabled"></td>
        <td hidden="hidden">${actividad.tipo}</td>
        
        <td><input class="form-control" size="2" type="number" name="periodo" value="${actividad.periodo}" disabled="disabled"></td>
        
        <td hidden="hidden">${actividad.periodo}</td>
        <td hidden="hidden">${actividad.idUsuario}</td>

        <td><input class="form-control" size="10" type="date" class="form_datetime" name="fecha" value="${actividad.fechaActividad}" disabled="disabled"></td>
        
        <td><input class="form-control" size="10" type="text" name="responsable" value="${actividad.idUsuario}" disabled="disabled"></td>

        <td><input class="form-control" size="16" type="text" name="contrato" value="${actividad.idContrato}" disabled="disabled"></td>
        <td hidden="hidden">${actividad.idContrato}</td>

        <td><textarea class="form-control" name="descripcion" rows="2" disabled="disabled">${actividad.descripcion}</textarea></td>
        <td><input class="form-control" size="16" type="text" name="estado" value="${actividad.estado}" disabled="disabled"></td>
        
        <input class="form-control" size="16" type="hidden" id="id" name="id" value="${actividad.id}" >
        <input class="form-control" size="16" type="hidden" name="tipo" value="${actividad.tipo}" >
        <input class="form-control" size="16" type="hidden" name="contrato" value="${actividad.idContrato}" >
        <input type="hidden" class="form-control form_datetime" name="fechaDesde" value="${formattedfechaDesde}" />
        
        <c:if test="${empty actividad.idUsuario}">
            <c:if test="${actividad.estado == 'PENDIENTE'}">
              <td class="vert-align"><button class="btn btn-primary" type="submit"> <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span> Tomar </button></td>
            </c:if>
        </c:if>

      </form>

      <c:if test="${actividad.estado == 'PENDIENTE'}">
        <form class="form-control" action="/CounterWebApp/desktop/actividad/cambiarEstado" method="POST">
          <input class="form-control" size="16" type="hidden" id="id" name="id" value="${actividad.id}" >
          <input type="hidden" class="form-control form_datetime" name="fechaDesde" value="${formattedfechaDesde}" />

          <td class="vert-align"><button class="btn btn-primary" type="submit" title="Cambiar estado"> <span class="glyphicon glyphicon-tags" aria-hidden="true"></span></button></td>
        </form>
      </c:if>

      </tr>
     </c:forEach>
    </tbody>
  </table>
</div>

<script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
</body>
</html>