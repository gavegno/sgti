<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
            <h2 class="container text-center">Gestión de Usuarios</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/CounterWebApp/desktop/tecnicos/ingresarUsuario" method="POST">
                
                	<div class="form-group container">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" class="form-control" name="id" id="inputId" placeholder="Id" required autofocus>        
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputContrasena" class="control-label">Contraseña:</label>
                        <input type="text" class="form-control" name="contrasena" id="inputContrasena" placeholder="Contraseña" required autofocus>        
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="text" class="form-control" name="nombre" id="inputNombre" placeholder="Nombre" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputApellido" class="control-label">Apellido:</label>
                        <input type="text" class="form-control" name="apellido" id="inputApellido" placeholder="Apellido" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputEmail" class="control-label">Email:</label>
                        <input type="email" class="form-control" name="email" id="inputEmail" placeholder="Email" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputTelefono" class="control-label">Teléfono:</label>
                        <input type="number" class="form-control" name="telefono" id="inputTelefono" placeholder="Teléfono" required autofocus>        
                    </div>

                    <div class="form-group container">
                        <label for="inputTipo" class="control-label">Tipo de usuario:</label>
                        <select class="form-control" name="tipo" id="inputTipo" onChange="document.getElementById('inputTipoHora').disabled = (this.value=='CONTRAPARTE')">
                            <option value="SOCIO">SOCIO</option>
                            <option value="TECNICO">TECNICO</option>
                            <option value="CONTRAPARTE">CONTRAPARTE</option>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <label for="inputTipoHora" class="control-label">Tipo de hora:</label>
                        <select multiple class="form-control" name="tipoHora" id="inputTipoHora">
                        	<c:forEach items="${tipos}" var="tipo" >
                        		<option value="${tipo.tipo}"><c:out value="${tipo.tipo}" /></option>
                        	</c:forEach>
                        </select>
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