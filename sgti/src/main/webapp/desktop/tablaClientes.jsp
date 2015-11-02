<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />">
    <%@ include file="header.jsp" %>
    <%
		String usuarioSesion=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuarioSesion==null)
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
            <h2 class="text-center container">Clientes</h2>
            <div>
                    <div class="form-group container">
                        <input class="form-control" id="system-search" name="q" placeholder="Buscar..." required>
                        <table class="table table-hover table-list-search">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Dirección</th>
                                    <th>Teléfono</th>
                                </tr>
                            </thead>
                            <tbody class="searchable">
                            <c:forEach items="${clientes}" var="cliente" >
                            <tr>
                                <form class="form-control" action="/CounterWebApp/desktop/cliente/editar" method="POST">
                                
                                    <td><input class="form-control" type="text" name="nombre" value="${cliente.nombre}" disabled="disabled"></td>
                                    <td hidden="hidden">${cliente.nombre}</td>
                                    <td><input class="form-control" type="text" name="direccion" value="${cliente.direccion}" disabled="disabled"></td>
                                    <td hidden="hidden">${cliente.direccion}</td>
                                    <td><input class="form-control" type="text" name="telefono" value="${cliente.telefono}" disabled="disabled"></td>
                                    <td hidden="hidden">${cliente.telefono}</td>
                                    
                                    <td class="vert-align"><button class="btn btn-success" type="submit" title="Editar cliente"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button></td>

                                <input class="vert-align" name="nombre" type="hidden" value="${cliente.nombre}">
                                </form>
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