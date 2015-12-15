<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />">
    <%@ include file="header.jsp" %>
	    <%
		String usuario=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuario==null)
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
            <h2 class="container text-center">Gestión de actividades</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/Sgti/desktop/actividad/ingresarActividad" method="POST">
					
					<div class="form-group container">
                        <label for="inputId" class="control-label">Identificador:</label>
                        <input type="text" class="form-control" id="inputid" name="id" required="required">
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputTipoActividad" class="control-label">Seleccione el tipo de actividad:</label>
                        <select class="form-control" id="inputTipoActividad" name="tipo" onChange="document.getElementById('inputperiodo').disabled = (this.value=='comun')">
                            <option value="Periodica">PERIÓDICA</option>
                            <option value="comun">COMÚN</option>
                        </select>    
                    </div>

					<div class="form-group container">
                        <label for="inputPeriodo" class="control-label">Período (Día(s)):</label>
                        <input type="number" class="form-control" id="inputperiodo" value="0" name="periodo">
                    </div>
					
                    <div class="form-group container">
                        <label for="inputFechaHora" class="control-label">Fecha:</label>
                        <input type="date" class="form-control" id="inputFechaHora" name="fecha" required="required">
                        </div>

                    <div class="form-group container">
                        <label for="inputUsuario" class="control-label">A ejecutar por:</label>
                        <select class="form-control" id="inputUsuario" name="usuario">
							<option value="">--Sin asignar--</option>
                            <c:forEach items="${usuarios}" var="usuario" >
                        		<option value="${usuario.id}"><c:out value="${usuario.nombre}"/></option>
                        	</c:forEach>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputContrato" class="control-label">Seleccione el contrato:</label>
                        <select class="form-control" id="inputContrato" name="contrato">
                            <c:forEach items="${contratos}" var="contrato" >
                        		<option value="${contrato.id}"><c:out value="${contrato.id}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputDescripcion" class="control-label">Ingrese la descripción:</label>
                        <td><textarea id="inputDescripcion" class="form-control" name="descripcion" rows="2" ></textarea></td>
                    </div>

                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>

                </div>       
                </form>
            </div>
        </div>
    </div>
</div>
</body>