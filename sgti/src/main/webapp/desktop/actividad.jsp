<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
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
			response.sendRedirect("/CounterWebApp/desktop/login2.jsp");
		}
	%>
</head>
<body>
    <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        	${message}
        </div>
    
    </header>   
<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Actividades</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/desktop/actividad/ingresarActividad" method="POST">
					<div class="form-group">
                        <label for="inputId" class="control-label">Identificador:</label>
                        <input type="text" class="form-control" id="inputid" name="id" required="required">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="inputTipoActividad" class="control-label">Seleccione el tipo de actividad:</label>
                        <select class="form-control" id="inputTipoActividad" name="tipo">
                            <option value="comun">COMÚN</option>
                            <option value="Periodica">PERIÓDICA</option>
                        </select>    
                    </div>

					<div class="form-group">
                        <label for="inputPeriodo" class="control-label">Período:</label>
                        <input type="number" class="form-control" id="inputperiodo" name="periodo">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="inputFechaHora" class="control-label">Fecha:</label>
                        <input type="date" class="form_datetime" id="inputFechaHora" name="fecha" required="required">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="inputUsuario" class="control-label">A ejecutar por:</label>
                        <select class="form-control" id="inputUsuario" name="usuario">
							<option value="">--Sin asignar--</option>
                            <c:forEach items="${usuarios}" var="usuario" >
                        		<option value="${usuario.id}"><c:out value="${usuario.nombre}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                    <div class="form-group">
                        <label for="inputContrato" class="control-label">Seleccione el contrato:</label>
                        <select class="form-control" id="inputContrato" name="contrato">
                            <c:forEach items="${contratos}" var="contrato" >
                        		<option value="${contrato.id}"><c:out value="${contrato.id}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                <div class="form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                    <button class="btn btn-default" type="button"> Cancelar </button>
                </div>       
                </form>
            </div>
        </div>
    </div>
</div>
</body>