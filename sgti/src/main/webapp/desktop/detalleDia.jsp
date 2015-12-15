<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/css/datepicker.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/bootstrap-clockpicker.css" />" rel="stylesheet">
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
            <h2 class="text-center">Gestión de horario laboral</h2>
    
                <form class="form-horizontal" action="/Sgti/desktop/dias/ingresarCompleto" method="POST">
                
                    <div class="form-group container">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" name="idHorarioLaboral" value="" class="form-control" placeholder="Horario Laboral" required autofocus>        
                    </div>

                    <table id="mt" class="table table-hover">
                    <thead>
                      <tr>
                        <th>Día</th>
                        <th>Hora desde</th>
                        <th>Hora hasta</th>
                      </tr>
                    </thead>
                    <tbody>

                        
                        

                            <tr >
                                <input type="text" name="lunes" value="Lunes" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="lunes" value="Lunes" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="lunesdesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="luneshasta" value=""></td>

                            </tr>
                             <tr >
                                <input type="text" name="martes" value="Martes" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="martes" value="Martes" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="martesdesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="marteshasta" value=""></td>                                                    

                            </tr>
                             <tr >
                                <input type="text" name="miercoles" value="Miércoles" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="miercoles" value="Miércoles" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="miercolesdesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="miercoleshasta" value=""></td>                                                

                            </tr>
                             <tr >
                                <input type="text" name="jueves" value="Jueves" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="jueves" value="Jueves" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="juevesdesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="jueveshasta" value=""></td>                                            

                            </tr>
                             <tr >
                                <input type="text" name="viernes" value="Viernes" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="viernes" value="Viernes" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="viernesdesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="vierneshasta" value=""></td>                                          

                            </tr>
                             <tr >
                                <input type="text" name="sabado" value="Sábado" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="sabado" value="Sábado" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="sabadodesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="sabadohasta" value=""></td>                                                 

                            </tr>
                             <tr >
                                <input type="text" name="domingo" value="Domingo" hidden></input>
                                <td><input type="text" class="form-control" size="4" name="domingo" value="Domingo" disabled="disabled"></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="domingodesde" value=""></td>
                                <td><input type="text" class="form-control clockpicker" size="4" name="domingohasta" value=""></td>                                             

                            </tr>


                    </tbody>
                    </table>
                    <br>
                    <button class="btn btn-success" id="boton" type="submit"> Confirmar </button><br><br>


                </form>
            </div>
        </div>
    <script src="<c:url value="/resources/js/bootstrap-clockpicker.js"/>"></script>
    <script type="text/javascript">
$('.clockpicker').clockpicker({
    placement: 'top',
    align: 'left',
    donetext: 'Listo'
});
</script> 

</body>