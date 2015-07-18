<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    


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
        <div class="col-sm-6 col-md-4 col-md-offset-4">
            <h1 class="text-center login-title">Iniciar sesi�n</h1>
            <div class="account-wall">
                <form class="form-signin" action="http://localhost:9080/CounterWebApp/login2/loguearse" method="POST">
                    
                    <div class="form-group">
                        <label for="inputUsuario" class="control-label">Usuario:</label>
                        <input type="usuario" name="idUsuario" class="form-control" id="inputUsuario" placeholder="Usuario" required autofocus>        
                    </div>

                    <div class="form-group">
                        <label for="inputPassword" class="control-label">Contraseña:</label>
                        <input type="password" name="passwordUsuario" class="form-control" id="inputPassword" placeholder="Contraseña" required>        
                    </div>
                
                
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Iniciar sesión</button>
                <!--    
                <label class="checkbox pull-left">
                    <input type="checkbox" value="remember-me">
                    Remember me
                </label>
            -->
                <a href="#" class="pull-right need-help">¿Ayuda? </a><span class="clearfix"></span>
                </form>
            </div>
        </div>
    </div>
</div>