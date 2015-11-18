<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <%
		String usuario=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuario==null)
		{
			response.sendRedirect("/CounterWebApp/desktop/login2.jsp");
		}
	%>
</head>
<body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>

    <header>
        <div class="container">
            <h1 class="text-center">Sistema de Gestión de Técnicos e Incidentes</h1>
        </div>
    </header>
    
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
            <h2 class="container text-center">Paso 6 de 6: Gestión de configuración</h2>
            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido} <br>
                    
                    <c:forEach items="${precios}" var="precio" >

                        Precio: fecha desde: ${precio.fechaDesde} --- fecha hasta: ${precio.fechaHasta} --- valor de precio: ${precio.precio} --- valor precio extra: ${precio.precioExtra} <br>
                    </c:forEach> 

                    <c:forEach items="${tiposDeHoraAsignados}" var="tipoAsignado" >

                        Tipo de hora: ${tipoAsignado.tipoHora.tipo} --- factor de cómputo: ${tipoAsignado.computo} <br>
                    </c:forEach> 

                </div>

                <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/ingresarConfiguracion" method="POST">

                    <div class="form-group container">
                        <label for="inputFechaDesde" class="control-label">Inicio de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Desde"  name="fechaDesde" id="FechaDesde" required>
                    </div>
                    

                    <div class="form-group container">
                        <label for="inputFechaHasta" class="control-label">Fin de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Hasta"  name="fechaHasta" id="FechaHasta">
                    </div>
                    <div class="form-group container">
                        <label for="inputTipoContrato" class="control-label">Tipo de contrato:</label>
                        <select class="form-control" name="tipoContrato" id="tipoContrato" required onChange="document.getElementById('inputPeriodoRenovacion').disabled = document.getElementById('computos').disabled = document.getElementById('inputPeriodoValidez').disabled = document.getElementById('inputUnidadValidez').disabled = document.getElementById('inputAcumulacion').disabled = document.getElementById('inputPeriodoAcumulacion').disabled = document.getElementById('inputFrecuenciaFacturacion').disabled = (this.value=='PRECIO FIJO')">
                            <option value="PRECIO FIJO"><c:out value="PRECIO FIJO" /></option>
                            <option value="HORAS LIBRES"><c:out value="HORAS LIBRES" /></option>
                            <option selected="selected" value="PAQUETE DE COMPUTOS"><c:out value="PAQUETE DE COMPUTOS" /></option>    
                        </select>    
                    </div>
                    <div class="form-group container">
                        <label for="inputTipoRenovacion" class="control-label">Tipo de renovación:</label>
                        <select class="form-control" name="tipoRenovacion" id="inputTipoRenovacion" required>
                            <option value="Automatica">Automática</option>
                            <option value="Manual">Manual</option>
                        </select>    
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputPeriodoRenovacion" class="control-label">Periodo de renovación (meses): </label>
                           <input type="number" class="form-control" name="periodoRenovacion" id="inputPeriodoRenovacion" placeholder="Periodo de renovación" required value="0">
                    </div> 
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Cantidad de cómputos:</label>
                        <input type="number" class="form-control" name="computos" id="computos" placeholder="Cantidad" required value="0">
                    </div>

                    <div class="form-group container">
                        <label for="inputPeriodoValidez" class="control-label">Periodo de validez: </label>
                        <input type="number" class="form-control" name="periodoValidez" id="inputPeriodoValidez" placeholder="Periodo de validez" required value="0">
                        <select class="form-control" name="unidadValidez" id="inputUnidadValidez" required>
                            <option value="Dias">Dia(s)</option>
                            <option value="Meses">Mes(es)</option>
                        </select>  
                    </div> 
                    

                    <div class="form-group container">
                        <label for="inputAcumulacion" class="control-label">¿Acumulación de cómputos?:</label>
                        <Select class="form-control" name="acumulacion" id="inputAcumulacion" required onChange="document.getElementById('inputPeriodoAcumulacion').disabled = (this.value=='false')">
                        	<option value="true">SI</option>
                            <option value="false">NO</option>
                        </select> 
                    </div>

                    <div class="form-group container">
                        <label for="inputPeriodoAcumulacion" class="control-label">Periodo de acumulación (meses): </label>
                        <input type="number" class="form-control" name="periodoAcumulacion" id="inputPeriodoAcumulacion" placeholder="Cantidad" value="0">
                    </div> 

                    <div class="form-group container">
                        <label for="inputFrecuenciaInforme" class="control-label">Frecuencia de informe (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaInforme" id="inputFrecuenciaInforme" placeholder="Cantidad" required value="0">
                    </div>

                    <div class="form-group container">
                        <label for="inputFrecuenciaFacturacion" class="control-label">Frecuencia de facturación (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaFacturacion" id="inputFrecuenciaFacturacion" placeholder="Cantidad" required value="0">
                    </div>

                    
                    <input type="hidden" class="form-control" name="frecuenciaExtra" id="inputFrecuenciaExtras" placeholder="Cantidad" value="0">

                 <div class="form-group container">
                        <label for="inputHorarioLaboral" class="control-label">Horario laboral:</label>
                        <select class="form-control" name="horarioLaboral" id="inputHorarioLaboral" required>
                            <c:forEach items="${horariosLaborales}" var="horarioLaboral" >
                                <c:choose>
                                    <c:when test="${idHorarioLaboralNuevo eq horarioLaboral.id}">
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
                        <textarea id="inputRespuesta" name="respuesta" class="form-control" rows="3"></textarea>   
                    </div>
                    
                <input type="hidden" name="idContrato" value="${idContrato}"/>

                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Finalizar </button>
                </div>       

                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
    <script type="text/javascript">
        $().ready(function() {


    $('#tipoContrato').onChange(function() {
        $('input').each(function() {
            if ($(this).attr('disabled')) {
                $(this).removeAttr('disabled');
            }
            else {
                $(this).attr({
                    'disabled': 'disabled'
                });
            }
        });
    });
});
    </script>
</body>