<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
        <div class="container col-sm-6 col-md-6 ">
            <h2 class="container text-center">Gestión de usuarios</h2>
            <div class="form-group container">
                <form class="form-vertical" action="/CounterWebApp/desktop/tecnicos/editarUsuarioOk" method="POST">
                	<div class="container form-group ">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" class="form-control" name="id" value="${usuario.id}" disabled="disabled"> </input> 
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

                    <c:if test="${usuario.tipo eq 'TECNICO'}">
                        <div class="container form-group ">
                            <label for="inputContratos" class="control-label">Contratos que el técnico tiene asignado:</label>
                            <c:forEach items="${contratosAsignados}" var="contratoAsignado" >
                                <h5>   ${contratoAsignado.id} - ${contratoAsignado.cliente.nombre} - ${contratoAsignado.contraparte.nombre} ${contratoAsignado.contraparte.apellido}</h5>      
                            </c:forEach>
                        </div>
                    </c:if>

                <div class="container form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>

                </form>


                <form action="/CounterWebApp/desktop/tecnicos/borrar" method="POST" accept-charset="UTF-8" style="display:inline">
                    <input type="hidden" name="id" value="${usuario.id}" hidden> </input>        

                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Borrar </button>

                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Eliminar el usuario</h4>
                          </div>
                          <div class="modal-body">
                            ¿Realmente desea eliminar al usuario del sistema?
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"> Cancelar </button>
                            <button type="submit" class="btn btn-danger"> Confirmar </button>
                          </div>
                        </div>
                      </div>
                    </div>
                </form>

                <form class="form-horizontal" action="/CounterWebApp/desktop/tecnicos/tabla" method="GET">
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