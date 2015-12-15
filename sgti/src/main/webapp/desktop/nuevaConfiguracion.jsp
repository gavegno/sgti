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

<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="container text-center">Agregar nueva configuración</h2>

            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido}
                </div>


                <form class="form-horizontal" action="/Sgti/desktop/configuracion/agregarConfigOk" method="POST">
                    
                    <input type="text" name="idContrato" value="${idContrato}" hidden></input>
                    

                    <div class="form-group container">
                        <label for="inputFechaDesde" class="control-label">Inicio de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Desde"  name="fechaDesde" required>
                    </div>
                    

                    <div class="form-group container">
                        <label for="inputFechaHasta" class="control-label">Fin de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Hasta"  name="fechaHasta" required>
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputPeriodoRenovacion" class="control-label">Periodo de renovación (meses): </label>
                        <input type="number" class="form-control" name="periodoRenovacion" placeholder="Periodo de renovación" required>
                    </div> 
                    
                    <div class="form-group container">
                        <label for="inputTipoRenovacion" class="control-label">Tipo de renovación:</label>
                        <select class="form-control" name="tipoRenovacion" required>
                            <option value="Manual"><c:out value="Manual" /></option>
                            <option value="Automatica"><c:out value="Automatica" /></option>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputTipoContrato" class="control-label">Tipo de contrato:</label>
                        <select class="form-control" name="tipoContrato" required>
                            <option value="PRECIO FIJO"><c:out value="PRECIO FIJO"/></option>
                            <option value="HORAS LIBRES"><c:out value="HORAS LIBRES"/></option>
                            <option value="PAQUETE DE COMPUTOS"><c:out value="PAQUETE DE COMPUTOS" /></option>
                        </select>    
                    </div>

                    
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Cantidad de cómputos:</label>
                        <input type="number" class="form-control" name="computos" required>
                    </div>

                   
                    <div class="form-group container">
                        <label for="inputPeriodoValidez" class="control-label">Periodo de validez: </label>
                            <input type="number" class="form-control" name="periodoValidez" placeholder="Periodo de validez" required>

                        <select class="form-control" name="unidadValidez" required>
                            <option value="Mes">Meses</option>
                            <option value="Dia">Dias</option>
                        </select>  
                    </div> 
                    
                    
                    <div class="form-group container">
                        <label for="inputAcumulacion" class="control-label">¿Acumulación de cómputos?:</label>
                        <Select class="form-control" name="acumulacion" required> 
                            <option selected="selected" value="true">Sí</option>
                            <option value="false">No</option>
                        </select> 
                    </div>

                    <div class="form-group container">
                        <label for="inputPeriodoAcumulacion" class="control-label">Periodo de acumulación (meses): </label>
                        <input type="number" class="form-control" id="inputPeriodoAcumulacion" name="periodoAcumulacion" placeholder="Cantidad">
                    </div> 

                    <div class="form-group container">
                        <label for="inputFrecuenciaInforme" class="control-label">Frecuencia de informe (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaInforme" placeholder="Cantidad" required>
                    </div>

                    <div class="form-group container">
                        <label for="inputFrecuenciaFacturacion" class="control-label">Frecuencia de facturación (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaFacturacion" placeholder="Cantidad" required>
                    </div>


                    <div class="form-group container">
                        <label for="inputHorarioLaboral" class="control-label">Horario laboral:</label>
                        <select class="form-control" name="horarioLaboral" id="inputHorarioLaboral" required>
                            <c:forEach items="${horariosLaborales}" var="horarioLaboral" >
                                <option value="${horarioLaboral.id}"><c:out value="${horarioLaboral.id}" /></option>
                            </c:forEach>   
                        </select>  
                    </div>
                        
                        <div class="form-group container">
                            <label for="inputRespuesta" class="control-label">Tiempo de respuesta (SLA):</label>
                            <textarea id="inputRespuesta" name="respuesta" class="form-control" rows="3"></textarea>   
                        </div>
                        

                    <div class="form-group container">
                        <button class="btn btn-success" id="botonGuardar" type="submit"> <span class="glyphicon glyphicon-ok"></span> Agregar </button>

                    </div>       

                </form>

                <form class="form-horizontal" action="/Sgti/desktop/configuracion/tablaConfiguraciones" method="POST">
                    <input type="text" name="id" value="${idContrato}" hidden></input>
                    
                        <button class="btn btn-default" id="botonVolver" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Cancelar y volver </button>
                        <br><br>
                    
                </form>

            </div>
        </div>
    </div>
</div>
</body>