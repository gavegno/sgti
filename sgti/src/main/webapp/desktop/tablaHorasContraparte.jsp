<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <style type="text/css">
        input, tbody, select,textarea{
           font-size: 12px !important;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />"> 
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery.dataTables.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/datepicker.css" />"> 
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-datetimepicker.css" />">
    <%@ include file="header.jsp" %>
    <%
		String usuarioSesion=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuarioSesion==null)
		{
			response.sendRedirect("/Sgti/desktop/login2.jsp");
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
        <h2 class="text-center">Tabla de horas</h2>

        <form action="/Sgti/desktop/hora/tablaFiltradaContraparte" method="POST">
        <div class="row">

          <div class="col-lg-4">
            <input class="form-control" id="system-search" name="q" placeholder="Contrato, descripción...">
          </div>
        
          <div class="col-lg-3">
            <div class="input-group">
                <input type="text" name="id" value="${id}" hidden></input>

              <fmt:formatDate value="${fechaDesdeFiltro}" var="formattedfechaDesde" type="date" pattern="yyyy-MM-dd" />
              <input type="date" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesde}" />
              <span class="input-group-btn">
                  <button class="btn btn-primary glyphicon glyphicon-search" type="submit"></button>
              </span>
            </div>  
          </div>


          <br>
        </div>
    </form>
  <table id="mt" class="table table-hover table-list-search">
    <thead>
      <tr>
        <th>Inicio</th>
        <th>Fin</th>
        <th>Tipo_de_hora</th>
        <th>Descripción</th>
      </tr>
    </thead>
    <tbody class="searchable">

    <c:forEach items="${horasRegistradas}" var="horasRegistrada" >
      <tr>

      	<fmt:formatDate value="${horasRegistrada.fechaDesde}" var="formattedfechaDesde" type="date" pattern="dd-MM-yyyy hh:mm" />
      	<fmt:formatDate value="${horasRegistrada.fechaHasta}" var="formattedfechaHasta" type="date" pattern="dd-MM-yyyy hh:mm" />
        <td class="col-md-2"><input required="required" size="24" type="text" class="form-control text-center form_datetime2" name="fechadesde" value="${formattedfechaDesde}" title="${formattedfechaDesde}" disabled="disabled"></td>
        <td class="col-md-2"><input required="required" size="24" type="text" class="form-control text-center form_datetime2" name="fechahasta" value="${formattedfechaHasta}" title="${formattedfechaHasta}" disabled="disabled"></td>
        <td class="col-md-1">
        	<input type="text" class="form-control" name="tipohora" id="idtipohora" value="${horasRegistrada.nombreTipoHora}" disabled="disabled" title="${horasRegistrada.nombreTipoHora}">
        </td>

        <input disabled="disabled" class="form-control" type="hidden" name="contrato" value="${horasRegistrada.idContrato}" >
        <td hidden="hidden">${horasRegistrada.idContrato}</td>

        <td class="col-md-6"><textarea class="form-control" name="descripcion" rows="3" disabled="disabled">${horasRegistrada.descripcion}</textarea></td>

        <c:choose>
            <c:when test="${horasRegistrada.informada}">
                <td class="vert-align"><button id="plus" class="btn btn-success glyphicon glyphicon-ok" title="Esta hora ya ha sido informada"></td>
            </c:when>                    
            <c:otherwise>
                <td class="vert-align"><button id="plus" class="btn btn-primary glyphicon glyphicon-time" title="Esta hora aún no ha sido informada"></td>
            </c:otherwise>
        </c:choose>

        <input class="form-control" type="hidden" name="contrato" value="${horasRegistrada.idContrato}" ></td>
        <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
        <input class="form-control" type="hidden" name="fechainformar" value="${horasRegistrada.fechaInformar}" >
        <input class="form-control" type="hidden" name="fechafacturar" value="${horasRegistrada.fechaFacturar}" >
        <input class="form-control" type="hidden" name="fechacomputar" value="${horasRegistrada.fechaComputar}" >
      </tr>
     </c:forEach>
    </tbody>
  </table>

</div>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-datetimepicker.js" />"></script> 
    <script type="text/javascript">
        $(".form_datetime2").datetimepicker({format: 'dd-mm-yyyy hh:ii',
    todayBtn: true,
    startDate: "2015-08-10 10:00",
    minuteStep: 15,
    autoclose: true,
    todayHighlight: true,
    language: 'es'
});
    </script> 
    <script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
</body>
</html>