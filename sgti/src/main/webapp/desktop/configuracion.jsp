<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
</head>
<body>
    <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        
        </div>
    
    </header>   
<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Configuración</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/ingresarConfiguracion" method="POST">

                    <div class="form-group">
                        <label for="inputFechaDesde" class="control-label">Inicio de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Desde"  name="fechaDesde" id="FechaDesde" required>
                    </div>
                    

                    <div class="form-group">
                        <label for="inputFechaHasta" class="control-label">Fin de vigencia</label>
                        <input  type="date" class="form-control" placeholder="Fecha Hasta"  name="fechaHasta" id="FechaHasta">
                    </div>
                    
                    <div class="form-group">
                        <label for="inputPeriodoRenovacion" class="control-label">Periodo de renovación (meses): </label>
                        <input type="number" class="form-control" name="periodoRenovacion" id="inputPeriodoRenovacion" placeholder="Periodo de renovación" required>
                    </div> 
                    
                    <div class="form-group">
                        <label for="inputTipoRenovacion" class="control-label">Tipo de renovación:</label>
                        <select class="form-control" name="tipoRenovacion" id="inputTipoRenovacion" required>
                            <option value="Automatica">Automática</option>
                            <option value="Manual">Manual</option>
                        </select>    
                    </div>

                    <div class="form-group">
                        <label for="inputTipoContrato" class="control-label">Tipo de contrato:</label>
                        <select class="form-control" name="tipoContrato" id="inputTipoContrato" required>
                            <option value="PrecioFijo">Precio fijo</option>
                            <option value="Tipo2">Tipo2</option>
                            <option value="Tipo3">Tipo3</option>    
                        </select>    
                    </div>

                    <div class="form-group">
                        <label for="inputComputos" class="control-label">Cantidad de cÃ³mputos:</label>
                        <input type="number" class="form-control" name="computos" id="inputComputos" placeholder="Cantidad" required>
                    </div>



                    <div class="form-group">
                        <label for="inputPeriodoValidez" class="control-label">Periodo de validez: </label>
                        <input type="number" class="form-control" name="periodoValidez" id="inputPeriodoValidez" placeholder="Periodo de validez" required>
                        <select class="form-control" name="unidadValidez" id="inputUnidadValidez" required>
                            <option value="Dias">Dia(s)</option>
                            <option value="Meses">Mes(es)</option>
                        </select>  
                    </div> 
                    

                    <div class="form-group">
                        <label for="inputAcumulacion" class="control-label">¿Acumulación de cómputos?:</label>
                        <Select class="form-control" name="acumulacion" id="inputAcumulacion" required>
                        	<option value="true">SI</option>
                            <option value="false">NO</option>
                        </select> 
                    </div>

                    <div class="form-group">
                        <label for="inputPeriodoAcumulacion" class="control-label">Periodo de acumulación (meses): </label>
                        <input type="number" class="form-control" name="periodoAcumulacion" id="inputPeriodoAcumulacion" placeholder="Cantidad">
                    </div> 

                    <div class="form-group">
                        <label for="inputFrecuenciaInforme" class="control-label">Frecuencia de informe (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaInforme" id="inputFrecuenciaInforme" placeholder="Cantidad" required>
                    </div>

                    <div class="form-group">
                        <label for="inputFrecuenciaFacturacion" class="control-label">Frecuencia de facturación (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaFacturacion" id="inputFrecuenciaFacturacion" placeholder="Cantidad" required>
                    </div>

                    <div class="form-group">
                        <label for="inputFrecuenciaExtras" class="control-label">Frecuencia de cómputos extra (meses): </label>
                        <input type="number" class="form-control" name="frecuenciaExtra" id="inputFrecuenciaExtras" placeholder="Cantidad" required>
                    </div>

                 <div class="form-group">
                        <label for="inputHorarioLaboral" class="control-label">HorarioLaboral:</label>
                        <select class="form-control" name="horarioLaboral" id="inputHorarioLaboral" required>
                            <c:forEach items="${horariosLaborales}" var="horarioLaboral" >
                        		<option value="${horarioLaboral.id}"><c:out value="${horarioLaboral.id}" /></option>
                        	</c:forEach>   
                        </select>  
                    </div>
                    
                    <div class="form-group">
                        <label for="inputRespuesta" class="control-label">Tiempo de respuesta:</label>
                        <textarea id="inputRespuesta" name="respuesta" class="form-control" rows="3"></textarea>   
                    </div>
                    
                <input type="hidden" name="idContrato" value="${idContrato}"/>

                <div class="form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Siguiente </button>
                    <button class="btn btn-default" type="button"> Cancelar </button>
                </div>       

                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
</body>