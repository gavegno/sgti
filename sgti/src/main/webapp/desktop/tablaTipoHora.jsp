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
            <h2 class="text-center">Tipos de hora</h2>
            <input class="form-control" id="system-search" name="q" placeholder="Buscar..." required>
           <table id="mt" class="table table-list-search">
    <thead>
      <tr>
        <th>Id</th>
        <th>Tipo de hora</th>
      </tr>
    </thead>
    <tbody class="searchable">

        <c:forEach items="${tipos}" var="tipohora" >
        <tr>


                <form class="form-horizontal" action="/Sgti/desktop/tiposDeHora/editar" method="POST">
                    <input type="text" name="id" value="${tipohora.id}" hidden></input>
                    <td><input class="vert-align form-control" size="6" name="id" value="${tipohora.id}" disabled="disabled"></td>
                    <td><input class="vert-align form-control" name="tipo" value="${tipohora.tipo}"></td>
                    <td hidden="hidden">${tipohora.tipo}</td>
                    <td class="vert-align"><button class="btn btn-success" type="submit"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Editar </button></td>
                </form>

                <form class="form-horizontal" action="/Sgti/desktop/tiposDeHora/borrar" method="POST">
                    <input type="text" name="id" value="${tipohora.id}" hidden></input>
                    <td class="vert-align"><button class="btn btn-danger" type="submit"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Borrar </button></td>
                </form>

        </tr>
        </c:forEach>
    
       </tbody>
    </table>




                </form>
            </div>
        </div>
</div>
<script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
</body>