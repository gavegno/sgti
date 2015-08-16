<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/css/datepicker.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap-clockpicker.css" />" rel="stylesheet">
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
            <h1 class="text-center">Sistema de Gesti�n de T�cnicos e Incidentes</h1>
        </div>
    </header>

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
            <h2 class="container text-center">Gesti�n de D�as</h2>
            <div class="form-group container">
                <form class="form-horizontal" action="/CounterWebApp/desktop/dias/ingresardia" method="POST">
                
                	<div class="form-group container">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" name="idHorarioLaboral" value="${idHorarioLaboral}" class="form-control" id="inputIdHorarioLaboral" placeholder="Horario Laboral" required autofocus>        
                    </div>
                    
                    <div class="form-group container">
                        <label for="inputContrato" class="control-label">Seleccione el d��a:</label>
                        <select class="form-control" id="inputContrato" name="nombreDia">
                            <option value="Lunes">Lunes</option>
                            <option value="Martes">Martes</option>
                            <option value="Miercoles">Mi�rcoles</option>
                            <option value="Jueves">Jueves</option>
                            <option value="Viernes">Viernes</option>
                            <option value="Sábado">Sabado</option>
                            <option value="Domingo">Domingo</option>
                        </select>    
                    </div>

                    <div class="form-group container">
                        <div class="from-group clockpicker">
                            <label for="inputHoraDesde" class="control-label">Hora desde:</label>
                            <input type="text" class="form-control" name="horaDesde" id="inputHoraDesde" value="08:00">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>

                    <div class="form-group container">
                        <div class="from-group clockpicker">
                            <label for="inputHoraHasta" class="control-label">Hora hasta:</label>
                            <input type="text" class="form-control" name="horaHasta" id="inputHoraHasta" value="18:00">
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-time"></span>
                            </span>
                        </div>
                    </div>

                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Agregar </button>
                    <!-- <button class="btn btn-default" type="button"> Cancelar </button> -->
                </div>       

				<input type="hidden" class="form-control" name="idContrato" id="inputHoraHasta" value="${idContrato}">

                </form>
               
                <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/ingresar" method="POST">
                <input type="hidden" class="form-control" name="idContrato" id="inputHoraHasta" value="${idContrato}">
                <div class="form-group container">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Siguiente </button>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
    <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>
    <script src="<c:url value="/resources/js/bootstrap-clockpicker.js"/>"></script>
    <script type="text/javascript">
$('.clockpicker').clockpicker({
    placement: 'top',
    align: 'left',
    donetext: 'Listo'
});
</script> 

</body>