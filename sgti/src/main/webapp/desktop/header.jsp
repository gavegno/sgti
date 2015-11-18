<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">


<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/CounterWebApp/desktop/ppal/inicio">SGTI - Itapúa</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
       
        
        <c:if test="${tipoUsuario == 'SOCIO' || tipoUsuario == 'TECNICO'}">
	         <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Horas <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	          	<c:if test="${tipoUsuario == 'SOCIO'}">
	          		<li><a href="/CounterWebApp/desktop/hora/principal">Agregar tipo de hora</a></li>
	          		<li><a href="/CounterWebApp/desktop/tiposDeHora/tabla">Ver tipos de hora</a></li>
	          		<li role="separator" class="divider"></li>
	          		<li><a href="/CounterWebApp/desktop/dias/principal">Agregar Horario laboral</a></li>
					<li><a href="/CounterWebApp/desktop/dias/tabla">Ver horarios laborales</a></li>
	          		<li role="separator" class="divider"></li>
	          		<li><a href="/CounterWebApp/desktop/hora/ingresar">Registrar hora</a></li>
	          	</c:if>
	            <c:if test="${tipoUsuario == 'TECNICO'}">
	            	<li><a href="/CounterWebApp/desktop/hora/ingresar">Registrar hora</a></li>
	            </c:if>
	          </ul>
	        </li>
        </c:if>

		<c:if test="${tipoUsuario == 'SOCIO'}">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Usuarios <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="/CounterWebApp/desktop/tecnicos/ingresar">Agregar usuario</a></li>
	            <li><a href="/CounterWebApp/desktop/tecnicos/tabla">Ver y modificar usuario</a></li>
	            <li><a href="/CounterWebApp/desktop/informe/tecnicos">Informe de horas</a></li>
	          </ul>
	        </li>
        </c:if>
        
        <c:if test="${tipoUsuario == 'SOCIO'}">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Clientes <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	            <li><a href="/CounterWebApp/desktop/cliente/principal">Agregar cliente</a></li>
	            <li><a href="/CounterWebApp/desktop/cliente/tabla">Ver y modificar cliente</a></li>
	          </ul>
	        </li>
		</c:if>
		
		<c:if test="${tipoUsuario == 'SOCIO' || tipoUsuario == 'CONTRAPARTE'}">
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Contratos <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	          <c:if test="${tipoUsuario == 'SOCIO'}">
	            <li><a href="/CounterWebApp/desktop/contrato/ingresar">Crear contrato</a></li>
	            <li><a href="/CounterWebApp/desktop/contrato/tablaSocio">Ver contratos</a></li>
	            <li><a href="/CounterWebApp/desktop/contrato/tablaSocioInformarFacturar">Informar Horas</a></li>
	          </c:if>
	          <c:if test="${tipoUsuario == 'CONTRAPARTE'}">
	            <li><a href="/CounterWebApp/desktop/contrato/tablaContraparte">Ver mis contratos</a></li>
	          </c:if>
	          </ul>
	        </li>
		</c:if>
		<c:if test="${tipoUsuario == 'SOCIO' || tipoUsuario == 'TECNICO'}">
			<li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Actividad <span class="caret"></span></a>
	          <ul class="dropdown-menu">
	          <c:if test="${tipoUsuario == 'SOCIO'}">
	            <li><a href="/CounterWebApp/desktop/actividad/ingresar">Crear actividad</a></li>
	            <li><a href="/CounterWebApp/desktop/actividad/ver">Ver actividades</a></li>
	          </c:if>
	          <c:if test="${tipoUsuario == 'TECNICO'}">
	            <li><a href="/CounterWebApp/desktop/actividad/verPorUsuario">Ver Mis Actividades</a></li>
	          </c:if>
	          </ul>
	        </li>
        </c:if>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
      
	      	<c:choose>
	      		<c:when test="${notificaciones > 0}">
	      			<li class="dropdown">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="font-size: large; color: red;">${notificaciones} <span class="glyphicon glyphicon-bell"></span></a>
			          <ul class="dropdown-menu">
			          	<c:forEach items="${despliegeNotificaciones}" var="notificacion">
			          		<li><a href="${notificacion.url}">Tienes ${notificacion.cantidad} ${notificacion.mensaje}</a></li>
			          		<li role="separator" class="divider"></li>
			          	</c:forEach>
			          </ul>
			        </li>
	      		</c:when>
	      		<c:otherwise>
	      			<li class="dropdown">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" ><span class="glyphicon glyphicon-bell"></span></a>
			        </li>
	      		</c:otherwise>
	      	</c:choose>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="font-size: large;"> <span class="glyphicon glyphicon-user"></span> </a>
          <ul class="dropdown-menu">
            <li><a href="#">Ver mis datos</a></li>
            <li><a href="/CounterWebApp/desktop/tecnicos/cargarCambiarContrasena">Cambiar mi contraseña</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/CounterWebApp/desktop/login/logout">Cerrar sesión</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
</head>
<body>
	<header>
        <div class="container">
            <h1 class="text-center" >Sistema de Gestión de Técnicos e Incidentes</h1>
        </div>
    
    </header>
    
<script src="<c:url value="/resources/js/jquery.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script> 
</body>
    