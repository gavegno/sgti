<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />">
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet">
    <%@ include file="header.jsp" %>
    <%
		String usuarioSesion=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuarioSesion==null)
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
            <h2 class="text-center container">Usuarios</h2>
            
            <div>
                    <div class="form-group container">
                        <input class="form-control" id="system-search" name="q" placeholder="Buscar..." required>
                        <table class="table table-list-search">

                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Email</th>
                                    <th>Teléfono</th>  
                                    <th>Tipo</th>
                                </tr>
                            </thead>
                            
                            <tbody class="searchable">

                            <c:forEach items="${usuarios}" var="usuario" >
                            <tr>
                                <form class="form-control" action="/Sgti/desktop/tecnicos/editar" method="POST">
                                
                                    <td><input class="form-control" size="20" type="text" name="id" value="${usuario.id}" disabled="disabled" value="${usuario.id}"></td>
                                    <td hidden="hidden">${usuario.id}</td>
                                    <td><input class="form-control" size="20" type="text" name="nombre" value="${usuario.nombre}" disabled="disabled" ></td>
                                    <td hidden="hidden">${usuario.nombre}</td>
                                    <td><input class="form-control" size="20" type="text" name="apellido" value="${usuario.apellido}" disabled="disabled"></td>
                                    <td hidden="hidden">${usuario.apellido}</td>
                                    <td><input class="form-control" size="20" type="text" name="email" value="${usuario.email}" disabled="disabled" ></td>
                                    <td hidden="hidden">${usuario.email}</td>
                                    <td><input class="form-control" size="20" type="text" name="telefono" value="${usuario.telefono}" disabled="disabled" ></td>
                                    <td hidden="hidden">${usuario.telefono}</td>
                                    <td><input class="form-control" size="20" type="text" name="tipo" value="${usuario.tipo}" disabled="disabled" ></td>
                                    <td hidden="hidden">${usuario.tipo}</td>
                                    <td class="vert-align"><button class="btn btn-success" type="submit" title="Editar usuario"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>
                                
                                 <input class="form-control" type="hidden" name="id" value="${usuario.id}" />
                                </form>

                                <c:if test="${usuario.tipo == 'TECNICO'}">
                                    <form class="form-horizontal" action="/Sgti/desktop/tiposDeHora/tecnicoTipoHoras" method="POST">
                                        <input class="form-control" type="hidden" name="id" value="${usuario.id}" />
                                        <td class="vert-align"><button class="btn btn-primary glyphicon glyphicon-time" title="Gestionar tipos de hora del técnico"></td>
                                    </form>
                                </c:if>
                            </tr>
                            </c:forEach>
                        
                           </tbody>
                        </table>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
</body>