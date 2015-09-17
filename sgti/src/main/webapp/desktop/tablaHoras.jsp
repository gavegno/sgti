<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
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
        <h2 class="text-center">Tabla de horas</h2>
  <table id="mt" class="table table-hover">
    <thead>
      <tr>
        <th>Inicio</th>
        <th>Fin</th>
        <th>Tipo_de_hora</th>
        <th>¿Remota?</th>
        <th>Contrato</th>
        <th>Actividad</th>
        <th>Descripción</th>
      </tr>
    </thead>
    <tbody>
        <form class="form-horizontal" action="/CounterWebApp/desktop/hora/ingresarHora" method="POST">
             <tr id="filaNueva" >
             	<fmt:formatDate value="${horaCopiada.fechaDesde}" var="formattedfechaDesde" type="date" pattern="dd-MM-yyyy HH:mm" />
      			<fmt:formatDate value="${horaCopiada.fechaHasta}" var="formattedfechaHasta" type="date" pattern="dd-MM-yyyy HH:mm" />
                <td><input required="required" size="24" type="text" class="form-control form_datetime" name="fechadesde" value="${formattedfechaDesde}"></td>
                <td><input required="required" size="24" type="text" class="form-control form_datetime" name="fechahasta" value="${formattedfechaHasta}"></td>
                <td>
                	<select class="form-control" name="tipohora" id="inputTipoHora">
                        	<c:forEach items="${tipoHoras}" var="tipo" >
                        		<c:choose>
                        			<c:when test="${horaCopiada.nombreTipoHora eq tipo}">
                        				<option selected="selected" value="${tipo.tipo}"><c:out value="${tipo.tipo}" /></option>
                        			</c:when>
                        			<c:otherwise>
                        				<option value="${tipo.tipo}"><c:out value="${tipo.tipo}" /></option>
                        			</c:otherwise>
                        		</c:choose>
                        	</c:forEach>
                   </select>
                </td>
                <td>
                	<select class="form-control" name="remoto">
        			<c:choose>
        				<c:when test="${horaCopiada.remoto}">
                            <option value="false">No</option>
                            <option selected="selected" value="true">Si</option>
                        </c:when>
        				<c:otherwise>
        					<option selected="selected" value="false">No</option>
        					<option value="true">Si</option>
        				</c:otherwise>
        			</c:choose>
            		</select>
            </td>
                <td>
                	<select class="form-control" id="inputContrato" name="contrato">
                            <c:forEach items="${contratos}" var="contrato" >
                            	<c:choose>
                            		<c:when test="${contrato.id eq horaCopiada.idContrato}">
                            			<option selected="selected" value="${contrato.id}"><c:out value="${contrato.id}" /></option>
                            		</c:when>
                            		<c:otherwise>
                            			<option value="${contrato.id}"><c:out value="${contrato.id}" /></option>
                            		</c:otherwise>
                            	</c:choose>
                        	</c:forEach>
                    </select>
                </td>
                <td>
                	<select class="form-control" name="actividad">
                            <option selected="selected" value="nulo"><c:out value="No especificado" /></option>
                        	<c:forEach items="${actividades}" var="actividad" >
                        	<c:choose>

        						<c:when test="${horaCopiada.idActividad eq actividad.id}">
                        			<option selected="selected" value="${actividad.id}"><c:out value="${actividad.id}" /></option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="${actividad.id}"><c:out value="${actividad.id}" /></option>
                        		</c:otherwise>
                        	</c:choose>
                        	</c:forEach>
            </select>
                </td>
                <td><textarea class="form-control" rows="2" name="descripcion" placeholder="Descripción">${horaCopiada.descripcion}</textarea></td>
                <td><textarea class="form-control" rows="2" name="comentario" placeholder="Comentario">${horaCopiada.comentario}</textarea></td>
                <td class="vert-align"><button id="addRow" class="btn btn-success">Confirmar</a></td>
              </tr>

        </form>
    </tbody>
    <tbody>
    <c:forEach items="${horasRegistradas}" var="horasRegistrada" >
      <tr>
      <form class="form-horizontal" action="/CounterWebApp/desktop/hora/editarHora" method="POST">
      	<fmt:formatDate value="${horasRegistrada.fechaDesde}" var="formattedfechaDesde" type="date" pattern="dd-MM-yyyy HH:mm" />
      	<fmt:formatDate value="${horasRegistrada.fechaHasta}" var="formattedfechaHasta" type="date" pattern="dd-MM-yyyy HH:mm" />
        <td><input required="required" size="24" type="text" class="form-control form_datetime" name="fechadesde" value="${formattedfechaDesde}" ></td>
        <td><input required="required" size="24" type="text" class="form-control form_datetime" name="fechahasta" value="${formattedfechaHasta}" ></td>
        <td>
        	<select class="form-control" name="tipohora" id="inputTipoHora">
                <c:forEach items="${tipoHoras}" var="tipo" >
                <c:choose>
        	        <c:when test="${horasRegistrada.nombreTipoHora eq tipo.tipo}">
                        <option selected="selected" value="${tipo.tipo}"><c:out value="${tipo.tipo}" /></option>
                    </c:when>
                    <c:otherwise>
                        <option value="${tipo.tipo}"><c:out value="${tipo.tipo}" /></option>
            		</c:otherwise>
               	</c:choose>
               	</c:forEach>
            </select>
        </td>
        <td>
        	<select class="form-control" name="remoto">
        			<c:choose>
        				<c:when test="${horasRegistrada.remoto}">
                            <option value="false">No</option>
                            <option selected="selected" value="true">Si</option>
                        </c:when>
        				<c:otherwise>
        					<option selected="selected" value="false">No</option>
        					<option value="true">Si</option>
        				</c:otherwise>
        			</c:choose>
            </select>
        </td>
        <td><input disabled="disabled" class="form-control" type="text" name="contrato" value="${horasRegistrada.idContrato}" ></td>
        <td>
        	<select class="form-control" name="actividad">
                <option selected="selected" value="nulo"><c:out value="No especificado" /></option>
                <c:forEach items="${actividades}" var="actividad" >
                	<c:choose>

    					<c:when test="${horasRegistrada.idActividad eq actividad.id}">
                			<option selected="selected" value="${actividad.id}"><c:out value="${actividad.id}" /></option>
                		</c:when>

                		<c:otherwise>
                			<option value="${actividad.id}"><c:out value="${actividad.id}" /></option>
                		</c:otherwise>
                	</c:choose>
            	</c:forEach>
            </select>
        </td>
        <td><textarea class="form-control" name="descripcion" rows="2" >${horasRegistrada.descripcion}</textarea></td>
        <td><textarea class="form-control" name="comentario" rows="2" >${horasRegistrada.comentario}</textarea></td>
        <input class="form-control" type="hidden" name="contrato" value="${horasRegistrada.idContrato}" ></td>
        <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
        <input class="form-control" type="hidden" name="fechainformar" value="${horasRegistrada.fechaInformar}" >
        <input class="form-control" type="hidden" name="fechafacturar" value="${horasRegistrada.fechaFacturar}" >
        <input class="form-control" type="hidden" name="fechacomputar" value="${horasRegistrada.fechaComputar}" >
        <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Editar</button></td>
        </form>
        <c:if test="${tipoUsuario == 'SOCIO'}">
	        <form class="form-horizontal" action="/CounterWebApp/desktop/hora/detalleHora" method="POST">
	            <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
	        <td class="vert-align"><button id="plus" class="btn btn-primary glyphicon glyphicon-plus" onclick="document.getElementById('filaNueva').hidden = false" ></td>
	         </form>
        </c:if>
        <form class="form-horizontal" action="/CounterWebApp/desktop/hora/copiarHora" method="POST">
        <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
        <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" ><span class="glyphicon glyphicon-copy" aria-hidden="true"></span> Copiar</button></td>

         </form>
      </tr>
     </c:forEach>
    </tbody>
  </table>

</div>
    <script type="text/javascript" src="<c:url value="/resources/js/bootstrap-datetimepicker.js" />"></script> 
    <script type="text/javascript">
        $(".form_datetime").datetimepicker({format: 'dd-mm-yyyy hh:ii',
    todayBtn: true,
    startDate: "2015-08-10 10:00",
    minuteStep: 15,
    autoclose: true,
    todayHighlight: true,
    language: 'es'
});
    </script> 
</body>
</html>