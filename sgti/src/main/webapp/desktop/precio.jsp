<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <%
		String usuario=(String)session.getAttribute("usuario");
    	request.removeAttribute("usuario");
		if(usuario==null)
		{
			response.sendRedirect("/CounterWebApp/desktop/login2.jsp");
		}
	%>
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
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Precios</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/desktop/precio/ingresarPrecio" method="POST">
                    
                    <div class="form-group">
                        <label for="inputFechaDesde" class="control-label">Fecha Desde</label>
                        <input  type="date" name="fechaDesde" class="form-control" placeholder="Fecha Desde"  id="FechaDesde" required>
                    </div>
        
                    <div class="form-group">
                        <label for="inputFechaHasta" class="control-label">Fecha Hasta</label>
                        <input  type="date" name="fechaHasta" class="form-control" placeholder="Fecha Hasta"  id="FechaHasta">
                    </div>

                    <div class="form-group">
                        <label for="inputPrecio" class="control-label">Precio:</label>
                        <input type="number" name="precio" class="form-control" id="inputPrecio" placeholder="Precio" required autofocus>        
                    </div>
                
                <div class="form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Siguiente </button>
                    <button class="btn btn-default" type="button"> Cancelar </button>
                </div>       

				<input type="hidden" name="idContrato" value="${idContrato}"/>

                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
</body>