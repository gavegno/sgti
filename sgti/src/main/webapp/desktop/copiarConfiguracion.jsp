<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />">
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

<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="container text-center">Agregar nueva configuración</h2>

            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido}
                </div>


                <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/copiarConfigOk" method="POST">
                    
                    <input type="text" name="idContrato" value="${idContrato}" hidden></input>
                    <input type="text" name="idConfiguracion" value="${config.id}" hidden></input>

                    <div class="form-group container">
                        <label for="inputFechaDesde" class="control-label">Inicio de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Desde"  name="fechaDesde" value="${config.fechaInicio}"required>
                    </div>
                    

                    <div class="form-group container">
                        <label for="inputFechaHasta" class="control-label">Fin de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Hasta"  name="fechaHasta" value="${config.fechaFin}"required>
                    </div>

                    <div class="form-group container">
                        <label for="inputTipoContrato" class="control-label">Tipo de contrato:</label>
                        <select class="form-control" name="tipoContrato" required onfocus="document.getElementById('inputPeriodoRenovacion').disabled = document.getElementById('computos').disabled = document.getElementById('inputPeriodoValidez').disabled = document.getElementById('inputUnidadValidez').disabled = document.getElementById('inputAcumulacion').disabled = document.getElementById('inputPeriodoAcumulacion').disabled = document.getElementById('inputFrecuenciaFacturacion').disabled = (this.value=='PRECIO FIJO')" onchange="document.getElementById('inputPeriodoRenovacion').disabled = document.getElementById('computos').disabled = document.getElementById('inputPeriodoValidez').disabled = document.getElementById('inputUnidadValidez').disabled = document.getElementById('inputAcumulacion').disabled = document.getElementById('inputPeriodoAcumulacion').disabled = document.getElementById('inputFrecuenciaFacturacion').disabled = (this.value=='PRECIO FIJO')">
                            <c:choose>

                                <c:when test="${config.tipoContrato eq 'PRECIO FIJO'}">
                                    <option selected="selected" value="${config.tipoContrato}" ><c:out value="${config.tipoContrato}" /></option>
                                    <option value="PAQUETE DE COMPUTOS"><c:out value="PAQUETE DE COMPUTOS" /></option>
                                    <option value="HORAS LIBRES"><c:out value="HORAS LIBRES" /></option>
                                    
                                </c:when>

                                <c:when test="${config.tipoContrato eq 'HORAS LIBRES'}">
                                    <option selected="selected" value="${config.tipoContrato}" ><c:out value="${config.tipoContrato}" /></option>
                                    <option value="PAQUETE DE COMPUTOS"><c:out value="PAQUETE DE COMPUTOS" /></option>
                                    <option value="PRECIO FIJO"><c:out value="PRECIO FIJO" /></option>
                                </c:when>

                                <c:otherwise>
                                    <option selected="selected" value="${config.tipoContrato}"><c:out value="${config.tipoContrato}" /></option>
                                    <option value="PRECIO FIJO"><c:out value="PRECIO FIJO" /></option>
                                    <option value="HORAS LIBRES"><c:out value="HORAS LIBRES" /></option>
                                </c:otherwise>
                            </c:choose>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputTipoRenovacion" class="control-label">Tipo de renovación:</label>
                        <select class="form-control" name="tipoRenovacion" required>
                            <c:choose>

                                <c:when test="${config.renovacion eq 'Manual'}">
                                    <option selected="selected" value="${config.renovacion}"><c:out value="${config.renovacion}" /></option>
                                    <option value="Automatica"><c:out value="Automatica" /></option>
                                </c:when>

                                <c:otherwise>
                                    <option selected="selected" value="${config.renovacion}"><c:out value="${config.renovacion}" /></option>
                                    <option value="Manual"><c:out value="Manual" /></option>
                                </c:otherwise>
                            </c:choose>
                        </select>    
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputPeriodoRenovacion" class="control-label">Periodo de renovación (meses): </label>
                        <input type="number" class="form-control" name="periodoRenovacion" id="inputPeriodoRenovacion" placeholder="Periodo de renovación" value="${config.periodoRenovacion}" required>
                    </div> 
                    
                    
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Cantidad de cómputos:</label>
                        <input type="number" class="form-control" name="computos" id="computos" value="${config.computosPaquete}" required>
                    </div>

                    
                    <!--  Cargar lo que corresponda, eligiendo el lapso de tiempo ${config.periodoValidezMes} y ${config.periodoValidezDia}-->
                    <div class="form-group container">
                        <label for="inputPeriodoValidez" class="control-label">Periodo de validez: </label>
                        <c:choose>
                                <c:when test="${config.periodoValidezMes > 0}">
                                    <input type="number" class="form-control" name="periodoValidez" id="inputPeriodoValidez" placeholder="Periodo de validez" value="${config.periodoValidezMes}" required>
                                </c:when>
                                <c:otherwise>
                                    <input type="number" class="form-control" name="periodoValidez" id="inputPeriodoValidez" placeholder="Periodo de validez" value="${config.periodoValidezDia}" required>
                                </c:otherwise>
                            </c:choose>
                        
                        <select class="form-control" name="unidadValidez" id="inputUnidadValidez" required>
                            <c:choose>
                                <c:when test="${config.periodoValidezMes > 0}">
                                    <option selected="selected" value="Mes"><c:out value="Meses" /></option>
                                    <option value="Dia">Dias</option>
                                </c:when>

                                <c:otherwise>
                                    <option selected="selected" value="Dia"><c:out value="Dias" /></option>
                                    <option value="Mes">Meses</option>
                                </c:otherwise>
                            </c:choose>
                        </select>  
                    </div> 
                    
                    <!--  Hacer que la seleccionada sea la actual ${config.acumulacion} -->
                    <div class="form-group container">
                        <label for="inputAcumulacion" class="control-label">¿Acumulación de cómputos?:</label>
                        <Select class="form-control" name="acumulacion" id="inputAcumulacion" required onChange="document.getElementById('inputPeriodoAcumulacion').disabled = (this.value=='false')">
                            <c:choose>
                                <c:when test="${config.acumulacion}">
                                    <option value="false">No</option>
                                    <option selected="selected" value="true">Sí</option>
                                </c:when>
                                <c:otherwise>
                                    <option selected="selected" value="false">No</option>
                                    <option value="true">Sí</option>
                                </c:otherwise>
                            </c:choose>
                        </select> 
                    </div>

                    <div class="form-group container">
                        <label for="inputPeriodoAcumulacion" class="control-label">Periodo de acumulación (meses): </label>
                        <input type="number" class="form-control" id="inputPeriodoAcumulacion" name="periodoAcumulacion" placeholder="Cantidad" value="${config.periodoAcumulacion}">
                    </div> 

                    <div class="form-group container">
                        <label for="inputFrecuenciaInforme" class="control-label">Frecuencia de informe (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaInforme" placeholder="Cantidad" value="${config.frecuenciaInforme}"required>
                    </div>

                    <div class="form-group container">
                        <label for="inputFrecuenciaFacturacion" class="control-label">Frecuencia de facturación (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaFacturacion" id="inputFrecuenciaFacturacion" placeholder="Cantidad" value="${config.frecuenciaFacturacion}" required>
                    </div>
                    
                    <!--<input type="hidden" class="form-control" name="frecuenciaExtra" id="inputFrecuenciaExtras" placeholder="Cantidad"> -->


                    <div class="form-group container">
                            <label for="inputHorarioLaboral" class="control-label">Horario laboral:</label>
                            <select class="form-control" name="horarioLaboral" required>
                                <c:forEach items="${horariosLaborales}" var="horarioLaboral" >
                                    <c:choose>
                                        <c:when test="${horarioLaboral.id eq idHorarioLaboralActual}">
                                            <option selected="selected" value="${horarioLaboral.id}"><c:out value="${horarioLaboral.id}" /></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${horarioLaboral.id}"><c:out value="${horarioLaboral.id}" /></option>
                                        </c:otherwise>
                                    </c:choose>
                            	</c:forEach>   
                            </select>  
                        </div>
                        
                        <div class="form-group container">
                            <label for="inputRespuesta" class="control-label">Tiempo de respuesta (SLA):</label>
                            <textarea id="inputRespuesta" name="respuesta" class="form-control" rows="3">${config.tiempoRespuesta}</textarea>   
                        </div>
                        

                    <div class="form-group container">
                        <button class="btn btn-success" id="botonGuardar" type="submit"> <span class="glyphicon glyphicon-ok"></span> Agregar </button>

                    </div>       

                </form>

                <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/tablaConfiguraciones" method="POST">
                    <input type="text" name="id" value="${idContrato}" hidden></input>
                    
                        <button class="btn btn-default" id="botonVolver" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                        <br><br>
                    
                </form>

            </div>
        </div>
    </div>
</div>
</body>