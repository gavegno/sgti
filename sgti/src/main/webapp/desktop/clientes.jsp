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
            <h2 class="container text-center">Gestión de Clientes</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/CounterWebApp/desktop/cliente/ingresar" method="POST">
                    
                    <div class="form-group container">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="nombre" name="clienteNombre" class="form-control" id="inputNombre" placeholder="Nombre" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputDireccion" class="control-label">Dirección:</label>
                        <input type="direccion" name="clienteDireccion" class="form-control" id="inputDireccion" placeholder="Direccion" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputTelefono" class="control-label">Teléfono:</label>
                        <input type="Telefono" name="clienteTelefono" class="form-control" id="inputTelefono" placeholder="Telefono" required autofocus>        
                    </div>
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                    <!-- <button class="btn btn-default" type="button"> Cancelar </button> -->
                </div>       
                </form>
            </div>
        </div>
    </div>
</div>
</body>