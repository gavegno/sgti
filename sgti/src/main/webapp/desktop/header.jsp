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
            <li><a href="tiposDeHora.jsp">Agregar tipo de hora</a></li>
            <li><a href="agregarHorarioLaboral.jsp">Agregar Horario Laboral</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Ejemplo separado</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Otro ejemplo separado</a></li>
          </ul>
        </li>

        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Usuarios <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="/CounterWebApp/desktop/tecnicos/ingresar">Agregar usuario</a></li>
            <li><a href="#">Modificar usuario</a></li>
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
            <li><a href="#">Modificar cliente</a></li>
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
            <li><a href="#">Modificar contrato</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Ejemplo separado</a></li>
          </ul>
        </li>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Enlace ejemplo</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Mi cuenta<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Ver mis datos</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Cerrar sesión</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>

    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script> 