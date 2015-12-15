<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
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
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger">
			${errorMessage}
		</div>
	</c:if>
<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Informar Horas Por Técnico</h1>
            <div class="account-wall">
                <form class="form-signin" action="/Sgti/desktop/informe/informarHorasTecnico" method="POST">
                    
                     <div class="form-group">
	                    <label for="inputUsuario" class="control-label">Técnico:</label>
	                    <select class="form-control" id="inputUsuario" name="usuario">
	                    	<c:forEach items="${usuarios}" var="usuario" >
	                        	<option value="${usuario.id}"><c:out value="${usuario.nombre} ${usuario.apellido}" /></option>
	                        </c:forEach>
	            		</select>
	            	</div> 
                    
                    <div class="form-group">
                        <label for="inputFecha" class="control-label">Fecha:</label>
                        <select class="form-control" id="fecha" name="fecha" required="required">
                            <c:forEach items="${fechas}" var="fecha">
                                <option value="${fecha}"><c:out value="${fecha}" /></option>
                            </c:forEach>
                    	</select>        
                    </div>
                    
                    </div>
                
                
                <button class="btn btn-lg btn-primary btn-block" type="submit">Generar Informe</button>
                </form>
            </div>
        </div>
    </div>
</div>