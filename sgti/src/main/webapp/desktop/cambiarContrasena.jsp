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
        String usuario=(String)session.getAttribute("usuario");
        request.removeAttribute("usuario");
        if(usuario==null)
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
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Cambio de contraseņa</h1>
            <div class="account-wall">
                <form class="form-signin" action="/Sgti/desktop/tecnicos/cambiarContrasena" method="POST">
                    

                    <div class="form-group">
                        <label for="inputPasswordActual" class="control-label">Contraseņa actual:</label>
                        <input type="password" name="passwordActual" class="form-control" id="passwordActual" placeholder="Contraseņa actual" required>        
                    </div>

                    <div class="form-group">
                        <label for="inputPassword" class="control-label">Contraseņa nueva:</label>
                        <input type="password" name="passwordNueva1" class="form-control" id="passwordNueva1" placeholder="Contraseņa nueva" required>        
                    </div>

                    <div class="form-group">
                        <label for="inputPassword" class="control-label">Repetir contraseņa nueva:</label>
                        <input type="password" name="passwordNueva2" class="form-control" id="passwordNueva2" placeholder="Repetir contraseņa nueva" required>        
                    </div>
                
                
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Enviar datos</button>
                </form>
            </div>
        </div>
    </div>
</div>