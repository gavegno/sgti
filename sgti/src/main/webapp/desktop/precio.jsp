<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
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
            <h2 class="container text-center">Paso 2 de 6: Gestión de Precios</h2>
            
            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido}
            </div>

                <form class="form-horizontal container" action="/CounterWebApp/desktop/precio/ingresarPrecio" method="POST">
                    
                    <div class="form-group container">
                        <label for="inputFechaDesde" class="control-label">Fecha Desde</label>
                        <input  type="date" name="fechaDesde" class="form-control" placeholder="Fecha Desde"  id="FechaDesde" required>
                    </div>
        
                    <div class="form-group container">
                        <label for="inputFechaHasta" class="control-label">Fecha Hasta</label>
                        <input  type="date" name="fechaHasta" class="form-control" placeholder="Fecha Hasta"  id="FechaHasta">
                    </div>

                    <div class="form-group container">
                        <label for="inputPrecio" class="control-label">Precio:</label><h6>(Si se trata de un contrato de precio fijo, indica ese precio)</h6>
                        <input type="number" step="any" name="precio" class="form-control" id="inputPrecio" placeholder="Precio" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="precio" class="control-label">Valor de precio de cómputo extra: </label><h6>(Si corresponde)</h6>
                        <input type="number" step="any" id="precio" class="form-control" name="precioExtra" value="0">
                    </div> 
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Pasar al paso 3 </button>
                   
                </div>       

				<input type="hidden" name="idContrato" value="${idContrato}"/>

                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
</body>