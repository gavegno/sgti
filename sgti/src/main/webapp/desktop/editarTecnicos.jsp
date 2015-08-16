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
        <div class="container col-sm-6 col-md-6 ">
            <h2 class="container text-center">Gestión de Usuarios</h2>
            <div class="form-group container">
                <form class="form-vertical" action="/CounterWebApp/desktop/tecnicos/editarUsuarioOk" method="POST">
                	<div class="container form-group ">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" class="container form-group" name="id" value="${usuario.id}" disabled="disabled"> </input> 
                        <input type="hidden" name="id" value="${usuario.id}" hidden> </input>        
                    </div>
                    <div class="container form-group ">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="text" class="form-control" name="nombre" value="${usuario.nombre}"></input>        
                    </div>
                    <div class="container form-group ">
                        <label for="inputApellido" class="control-label">Apellido:</label>
                        <input type="text" class="form-control" name="apellido" value="${usuario.apellido}"></input>        
                    </div>
                    <div class="container form-group ">
                        <label for="inputEmail" class="control-label">Email:</label>
                        <input type="text" class="form-control" name="email" value="${usuario.email}"></input>        
                    </div>
                    <div class="container form-group ">
                        <label for="inputTelefono" class="control-label">Teléfono:</label>
                        <input type="text" class="form-control" name="telefono" value="${usuario.telefono}"></input>        
                    </div>
                <div class="container form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                    <!-- <button class="btn btn-default" type="button"> Cancelar </button> -->

                </form>
            </div>
        </div>
    </div>
</div>
</body>