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
            <h2 class="container text-center">Paso 3 de 6: Gestión de tipo de hora</h2>
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
                <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/ingresarComputo" method="POST">
                    <input type="hidden" class="form-control" name="idContrato" value="${idContrato}">

                     <div class="form-group container">
                        <label for="inputTipoHora" class="control-label">Tipo de hora:</label>
                        <select class="form-control" name="tipoHora" id="inputTipoHora">
                        	<c:forEach items="${tiposDeHora}" var="tipo" >
                        		<option value="${tipo.tipo}">${tipo.tipo}</option>
                        	</c:forEach>
                        </select>
                    </div>
        
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Factor multiplicador de cómputo</label>
                        <input  type="number" step="any" name="computos" class="form-control" placeholder="Cómputos" id="computos">
                    </div>
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Agregar </button>

                </div>      

                </form>



                <form class="form-horizontal" action="/CounterWebApp/desktop/dias/ingresar" method="POST">
                <input type="hidden" class="form-control" name="idContrato" value="${idContrato}">
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Pasar al paso 4 </button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
</body>