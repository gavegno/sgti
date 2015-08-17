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
            <h2 class="container text-center">Gestión de Tipo de hora y cómputo</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/ingresarComputo" method="POST">
                    
                     <div class="form-group container">
                        <label for="inputTipoHora" class="control-label">Tipo de hora:</label>
                        <select class="form-control" name="tipoHora" id="inputTipoHora">
                        	<c:forEach items="${tiposDeHora}" var="tipo" >
                        		<option value="${tipo}">${tipo}</option>
                        	</c:forEach>
                        </select>
                    </div>
        
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Cómputos a consumir</label>
                        <input  type="number" name="computos" class="form-control" placeholder="Cómputos" id="computos">
                    </div>
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Agregar </button>
                   <!-- <button class="btn btn-default" type="button"> Cancelar </button> -->
                </div>       

				<input type="hidden" name="idContrato" value="${idContrato}"/>

                </form>
                <form class="form-horizontal" action="/CounterWebApp/desktop/dias/ingresar" method="POST">
                <input type="hidden" class="form-control" name="idContrato" id="inputHoraHasta" value="${idContrato}">
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Siguiente </button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
</body>