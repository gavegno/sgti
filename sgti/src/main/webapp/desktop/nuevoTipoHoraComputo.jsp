<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
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
            <h2 class="container text-center">Agregar nuevo Tipo de hora y Cómputo para Contrato</h2>
            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.id}
                </div>

                <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/agregarContratoTipoHoraOk" method="POST">
                    
                     <input type="hidden" name="id" value="${idContrato}"/>

                     <div class="form-group container">
                        <label for="inputTipoHora" class="control-label">Tipo de hora:</label>
                        <select class="form-control" name="tipoHora" id="inputTipoHora">
                        	<c:forEach items="${tiposDeHora}" var="tipo" >
                        		<option value="${tipo.tipo}">${tipo.tipo}</option>
                        	</c:forEach>
                        </select>
                    </div>
        
                    <div class="form-group container">
                        <label for="inputComputos" class="control-label">Factor de cómputo</label>
                        <input  type="number" step="any" name="computos" class="form-control" placeholder="Factor" id="computos">
                    </div>
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Guardar </button>
                </div>      
                </form>

                <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/tablaHorasContrato" method="POST">
                    <input type="hidden" name="id" value="${idContrato}"/>
                    <div class="form-group container">  
                        <button class="btn btn-default" id="boton" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                    </div>      
                    
                </form>

            </div>
        </div>
    </div>
</div>
</body>