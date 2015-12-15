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

        <form action="/Sgti/desktop/hora/tablaFiltrada" method="POST">
        <div class="row">
          <div class="col-lg-4">
            <input class="form-control" id="system-search" name="q" placeholder="Contrato, descripción, comentario...">
          </div>
          <div class="col-lg-2">
            <select class="form-control" name="filtroValidada" id="inputTipoHora">
                <c:if test="${filtroValidada == 'Todas'}">
                    <option selected="selected" value="Todas"><c:out value="Todas" /></option>
                    <option value="Validadas"><c:out value="Validadas" /></option>
                    <option value="No validadas"><c:out value="No validadas" /></option>
                </c:if>
                <c:if test="${filtroValidada == 'Validadas'}">
                    <option value="Todas"><c:out value="Todas" /></option>
                    <option selected="selected" value="Validadas"><c:out value="Validadas" /></option>
                    <option value="No validadas"><c:out value="No validadas" /></option>
                </c:if>
                <c:if test="${filtroValidada == 'No validadas'}">
                    <option value="Todas"><c:out value="Todas" /></option>
                    <option value="Validadas"><c:out value="Validadas" /></option>
                    <option selected="selected" value="No validadas"><c:out value="No validadas" /></option>
                </c:if>
            </select>
          </div>

          <c:choose>
            <c:when test="${tipoUsuario == 'SOCIO'}">
              <div class="col-lg-3">
                <input class="form-control" id="filtroUsuario" name="filtroUsuario" placeholder="Usuario..." value="${filtroUsuario}">
              </div>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
            </c:otherwise>
          </c:choose>
        
          <div class="col-lg-3">
            <div class="input-group">

              <fmt:formatDate value="${fechaDesdeFiltro}" var="formattedfechaDesdeFiltro" type="date" pattern="yyyy-MM-dd" />
              <input type="date" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}" />
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
        <th>Contrato</th>
        <th>Tipo_de_hora</th>
        <th>¿Remota?</th>
        <th>Actividad</th>
        <th>Descripción</th>
        <th>Comentario</th>
      </tr>
    </thead>
    <tbody>
        <form class="form-horizontal" action="/Sgti/desktop/hora/ingresarHora" method="POST">
            <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
            <input type="hidden" name="filtroValidada" value="${filtroValidada}">
            <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesde}" />
            
             <tr id="filaNueva" >
             	<fmt:formatDate value="${horaCopiada.fechaDesde}" var="formattedfechaDesde" type="date" pattern="dd-MM-yyyy HH:mm" />
      			<fmt:formatDate value="${horaCopiada.fechaHasta}" var="formattedfechaHasta" type="date" pattern="dd-MM-yyyy HH:mm" />
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechadesde" value="${formattedfechaDesde}" title="${formattedfechaDesde}"></td>
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechahasta" value="${formattedfechaHasta}" title="${formattedfechaHasta}"></td>
                <td>
                    <select class="form-control" id="contrato" name="contrato" onChange="document.getElementById('tipohora').disabled = false" required="required">
                        <option value="">S/D</option>
                            <c:forEach items="${contratos}" var="contratoItem">
                                <option value="${contratoItem.id}"><c:out value="${contratoItem.id}" /></option>
                            </c:forEach>
                    </select>
                </td>

                <td>
                    <select class="form-control" name="tipohora" id="tipohora" disabled="disabled" required="required">
                        <option value="">S/D</option>
                        <c:forEach items="${todosTiposHora}" var="cadaTipoHora" >
                            <option label="${cadaTipoHora.tipo}" value="${cadaTipoHora.tipo}" custom="${cadaTipoHora.idContrato}">  </option>
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

                <td class="vert-align">
                    <td class="vert-align"><button id="addRow" type="submit" class="btn btn-success" title="Guardar nueva hora"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span></a></td>
              </tr>
        </form>
    </tbody>
    <tbody class="searchable">
    <c:forEach items="${horasRegistradas}" var="horasRegistrada" >
      <tr>
      <form class="form-horizontal" action="/Sgti/desktop/hora/editarHora" method="POST">
      	<fmt:formatDate value="${horasRegistrada.fechaDesde}" var="formattedfechaDesde" type="date" pattern="dd-MM-yyyy HH:mm" />
      	<fmt:formatDate value="${horasRegistrada.fechaHasta}" var="formattedfechaHasta" type="date" pattern="dd-MM-yyyy HH:mm" />
        
        <c:choose>
            <c:when test="${horasRegistrada.validada}">
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechadesde" value="${formattedfechaDesde}" title="${formattedfechaDesde}" disabled="disabled"></td>
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechahasta" value="${formattedfechaHasta}" title="${formattedfechaHasta}" disabled="disabled"></td>
            </c:when>                    
            <c:otherwise>
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechadesde" title="${formattedfechaDesde}" value="${formattedfechaDesde}"></td>
                <td class="col-md-2"><input required="required" type="text" class="form-control text-center form_datetime2" name="fechahasta" title="${formattedfechaHasta}" value="${formattedfechaHasta}"></td>
            </c:otherwise>
        </c:choose>

        <td class="col-md-1"><input disabled="disabled" class="form-control" type="text" name="contrato" value="${horasRegistrada.idContrato}" title="Usuario: ${horasRegistrada.idUsuario}"></td>
        
        
                
        <td><input type="text" class="form-control" name="tipohora" id="idtipohora" value="${horasRegistrada.nombreTipoHora}" disabled="disabled" title="${horasRegistrada.nombreTipoHora}"></td>
                
            

        <c:choose>
            <c:when test="${horasRegistrada.validada}">
                <c:choose>
                    <c:when test="${horasRegistrada.remoto}">
                        <td><input type="text" class="form-control" name="remoto" id="idremoto" value="Si" disabled="disabled"></td>
                    </c:when>
                    <c:otherwise>
                        <td><input type="text" class="form-control" name="remoto" id="idremoto" value="No" disabled="disabled"></td>
                    </c:otherwise>
                </c:choose>
            </c:when>                    
            <c:otherwise>
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
            </c:otherwise>
        </c:choose>

        
        
        <td hidden="hidden">${horasRegistrada.idContrato}</td>
        
        
        <c:choose>
            <c:when test="${horasRegistrada.validada}">
                <c:choose>
                    <c:when test="${empty horasRegistrada.idActividad}">
                        <td><input type="text" class="form-control" name="actividad" id="idactividad" value="No especificado" title="No especificado" disabled="disabled"></td>
                    </c:when>
                    <c:otherwise>
                        <td><input type="text" class="form-control" name="actividad" id="idactividad" value="${horasRegistrada.idActividad}" disabled="disabled"></td>
                    </c:otherwise>
                </c:choose>
                
                <td><textarea class="form-control" name="descripcion" rows="2" disabled="disabled">${horasRegistrada.descripcion}</textarea></td>
                <td><textarea class="form-control" name="comentario" rows="2" disabled="disabled">${horasRegistrada.comentario}</textarea></td>
            </c:when>                    
            <c:otherwise>
                <c:choose>
                    <c:when test="${empty horasRegistrada.idActividad}">
                        <td><input type="text" class="form-control" name="actividad" id="idactividad" value="No especificado" title="No especificado" disabled="disabled"></td>
                    </c:when>
                    <c:otherwise>
                        <td><input type="text" class="form-control" name="actividad" id="idactividad" value="${horasRegistrada.idActividad}" disabled="disabled"></td>
                    </c:otherwise>
                </c:choose>
                
                <td><textarea class="form-control" name="descripcion" rows="2">${horasRegistrada.descripcion}</textarea></td>
                <td><textarea class="form-control" name="comentario" rows="2">${horasRegistrada.comentario}</textarea></td>

            </c:otherwise>
        </c:choose>

        <input class="form-control" type="hidden" name="contrato" value="${horasRegistrada.idContrato}" >
        <input class="form-control" type="hidden" name="tipohora" value="${horasRegistrada.nombreTipoHora}" >
        <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
        <input class="form-control" type="hidden" name="actividad" value="${horasRegistrada.idActividad}" title="No especificado">

        <input class="form-control" type="hidden" name="fechainformar" value="${horasRegistrada.fechaInformar}" >
        <input class="form-control" type="hidden" name="fechafacturar" value="${horasRegistrada.fechaFacturar}" >
        <input class="form-control" type="hidden" name="fechacomputar" value="${horasRegistrada.fechaComputar}" >

        <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
        <input type="hidden" name="filtroValidada" value="${filtroValidada}">
        <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}">

        <c:choose>
            <c:when test="${horasRegistrada.validada}">
                <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" title="La hora ya ha sido validada" disabled="disabled"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button></td>
            </c:when>                    
            <c:otherwise>
                <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" title="Guardar cambios"><span class="glyphicon glyphicon-floppy-saved" aria-hidden="true"></span></button></td>
            </c:otherwise>
        </c:choose>

        </form>

        <form class="form-horizontal" action="/Sgti/desktop/hora/copiarHora" method="POST">
            <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
            <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
            <input type="hidden" name="filtroValidada" value="${filtroValidada}">
            <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
            <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}" />

            <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" title="Copiar hora"><span class="glyphicon glyphicon-copy" aria-hidden="true"></span></button></td>

         </form>

        <c:if test="${tipoUsuario == 'SOCIO'}">
	        <form class="form-horizontal" action="/Sgti/desktop/hora/detalleHora" method="POST">
	            <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
                <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
                <input type="hidden" name="filtroValidada" value="${filtroValidada}">
                <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}" />

	           <td class="vert-align"><button id="plus" class="btn btn-primary glyphicon glyphicon-plus" onclick="document.getElementById('filaNueva').hidden = false" title="Opciones avanzadas"></td>
	         </form>

            <form class="form-horizontal" action="/Sgti/desktop/hora/validarHora" method="POST">
                <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}" />
                <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
                <input type="hidden" name="filtroValidada" value="${filtroValidada}">
                <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >

                <c:choose>
                    <c:when test="${horasRegistrada.validada}">
                        <td class="vert-align"><button id="plus" class="btn btn-warning glyphicon glyphicon-thumbs-down" onclick="document.getElementById('filaNueva').hidden = false" title="Sacar validación de hora"></td>
                    </c:when>                    
                    <c:otherwise>
                        <td class="vert-align"><button id="plus" class="btn btn-success glyphicon glyphicon-thumbs-up" onclick="document.getElementById('filaNueva').hidden = false" title="Validar hora"></td>
                    </c:otherwise>
                </c:choose>
            </form>
        </c:if>

        <c:if test="${tipoUsuario == 'TECNICO'}">
            <form class="form-horizontal" action="/Sgti/desktop/hora/borrar" method="POST">
                <input class="form-control" type="hidden" name="id" value="${horasRegistrada.id}" >
                <input type="hidden" name="filtroUsuario" value="${filtroUsuario}">
                <input type="hidden" name="filtroValidada" value="${filtroValidada}">
                <input type="hidden" class="form-control form_datetime" name="fechaDesdeFiltro" value="${formattedfechaDesdeFiltro}" />

                    <c:if test="${not horasRegistrada.validada}">
                        <td class="vert-align"><button id="plus" class="btn btn-danger glyphicon glyphicon-trash" onclick="document.getElementById('filaNueva').hidden = false" title="Eliminar esta hora"></td>
                    </c:if>                 
            </form>
        </c:if>
        
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
    minuteStep: 5,
    autoclose: true,
    todayHighlight: true,
    language: 'es'
});
    </script> 
    <script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
    <script>
            $("#contrato").change(function() { 
        if($(this).data('options') == undefined){
            /*Taking an array of all options-2 and kind of embedding it on the contrato*/
            $(this).data('options',$('#tipohora option').clone());
            } 
        var id = $(this).val();
        var options = $(this).data('options').filter('[custom=' + id + ']');
        $('#tipohora').html(options);
        });
    </script>
</body>
</html>