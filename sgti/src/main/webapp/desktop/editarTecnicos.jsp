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
    <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        
        </div>
    
    </header>   
<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Usuarios</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/desktop/tecnicos/editarUsuarioOk" method="POST">
                
                	<div class="form-group">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" name="id" value="${usuario.id}" hidden><c:out value="${usuario.id}" /></input>        
                    </div>
                    
                    <div class="form-group">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="text" name="nombre" value="${usuario.nombre}"></input>        
                    </div>

                    <div class="form-group">
                        <label for="inputApellido" class="control-label">Apellido:</label>
                        <input type="text" name="apellido" value="${usuario.apellido}"></input>        
                    </div>

                    <div class="form-group">
                        <label for="inputEmail" class="control-label">Email:</label>
                        <input type="text" name="email" value="${usuario.email}"></input>        
                    </div>

                    <div class="form-group">
                        <label for="inputTelefono" class="control-label">Teléfono:</label>
                        <input type="text" name="telefono" value="${usuario.telefono}"></input>        
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