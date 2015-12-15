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
            <h2 class="container text-center">Paso 1 de 6: Gestión de contratos</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/Sgti/desktop/contrato/ingresarContrato" method="POST">
                    
                    <div class="form-group container">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" class="form-control" name="id" id="inputId" placeholder="Id" required autofocus>        
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputCliente" class="control-label">Seleccione el cliente:</label>
                        <select class="form-control" id="inputCliente" name="cliente">
                            <c:forEach items="${clientes}" var="cliente" >
                        		<option value="${cliente.nombre}"><c:out value="${cliente.nombre}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputContraparte" class="control-label">Seleccione contraparte:</label>
                        <select class="form-control" id="inputContraparte" name="contraparte">
                            <c:forEach items="${contrapartes}" var="contraparte" >
                        		<option value="${contraparte.id}"><c:out value="${contraparte.nombre} ${contraparte.apellido}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Pasar al paso 2 </button>
                    
                </div>       
                </form>
            </div>
        </div>
    </div>
</div>
</body>