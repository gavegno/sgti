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
      <a class="navbar-brand" href="/CounterWebApp/desktop/paginaPrincipal.jsp">SGTI - Itapúa</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Ejemplo <span class="sr-only">(current)</span></a></li>
        
         <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Horas <span class="caret"></span></a>
          <ul class="dropdown-menu">
          	<c:if test="${tipoUsuario == 'SOCIO'}">
          		<li><a href="/CounterWebApp/desktop/tiposDeHora.jsp">Agregar tipo de hora</a></li>
          		<li><a href="/CounterWebApp/desktop/agregarHorarioLaboral.jsp">Agregar Horario Laboral</a></li>
          		<li><a href="/CounterWebApp/desktop/hora/ingresar">Registrar hora</a></li>
          	</c:if>
            <c:if test="${tipoUsuario == 'TECNICO'}">
            	<li><a href="/CounterWebApp/desktop/hora/ingresar">Registrar hora</a></li>
            </c:if>
          </ul>
        </li>

        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Usuarios <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/CounterWebApp/desktop/tecnicos/ingresar">Agregar usuario</a></li>
            <li><a href="/CounterWebApp/desktop/tecnicos/tabla">Ver y modificar usuario</a></li>
            <li><a href="#">Eliminar usuario</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Ejemplo separado</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Otro ejemplo separado</a></li>
          </ul>
        </li>
        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Clientes <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/CounterWebApp/desktop/clientes.jsp">Agregar cliente</a></li>
            <li><a href="/CounterWebApp/desktop/cliente/tabla">Ver y modificar cliente</a></li>
            <li><a href="#">Eliminar cliente</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Ejemplo separado</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Otro ejemplo separado</a></li>
          </ul>
        </li>

        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Contratos <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/CounterWebApp/desktop/contrato/ingresar">Crear contrato</a></li>
            <li><a href="#">Ver contratos</a></li>
            <li><a href="/CounterWebApp/desktop/contrato/tabla">Ver contratos por contraparte</a></li>
            <li><a href="#">Modificar contrato</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Ejemplo separado</a></li>
          </ul>
        </li>

		<li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Actividad <span class="caret"></span></a>
          <ul class="dropdown-menu">
          <c:if test="${tipoUsuario == 'SOCIO'}">
            <li><a href="/CounterWebApp/desktop/actividad/ingresar">Crear actividad</a></li>
            <li><a href="/CounterWebApp/desktop/actividad/ver">Ver actividades</a></li>
            <li><a href="/CounterWebApp/desktop/actividad/verPorUsuario">Ver Mis Actividades</a></li>
          </c:if>
          <c:if test="${tipoUsuario == 'TECNICO'}">
            <li><a href="/CounterWebApp/desktop/actividad/verPorUsuario">Ver Mis Actividades</a></li>
          </c:if>
          </ul>
        </li>
        
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Mi cuenta<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Ver mis datos</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="/CounterWebApp/desktop/login/logout">Cerrar sesión</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script> 