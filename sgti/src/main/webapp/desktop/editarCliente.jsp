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
            <h2 class="container text-center">Gesti�n de clientes</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/Sgti/desktop/cliente/editarClienteOk" method="POST">
                    <div class="form-group container">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="text" class="form-control" name="clienteNombre" value="${cliente.nombre}" disabled="disabled"></input>
                        <input type="hidden" name="clienteNombre" value="${cliente.nombre}"></input>       
                    </div>

                    <div class="form-group container">
                        <label for="inputDireccion" class="control-label">Direcci�n:</label>
                        <input type="text" class="form-control" name="clienteDireccion" value="${cliente.direccion}"></input>
                    </div>

                    <div class="form-group container">
                        <label for="inputTelefono" class="control-label">Tel�fono:</label>
                        <input type="text" class="form-control" name="clienteTelefono" value="${cliente.telefono}"></input>
                    </div>
                    
                
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                </div>       

                </form>

                <form class="form-horizontal" action="/Sgti/desktop/cliente/tabla" method="GET">
                    <div class="form-group container">
                        <br>
                        <button class="btn btn-default" id="boton" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                        <br><br>
                    </div>       
                </form>
            </div>
        </div>
    </div>
</div>
</body>