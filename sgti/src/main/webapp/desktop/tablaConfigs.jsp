<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/styles.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet">
    <%@ include file="header.jsp" %>
    <%
        String usuarioSesion=(String)session.getAttribute("usuario");
        request.removeAttribute("usuario");
        if(usuarioSesion==null)
        {
            response.sendRedirect("/CounterWebApp/desktop/login2.jsp");
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

            <h2 class="text-center">Configuraciones de contrato</h2>
            <div>
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido}
                </div>

                    <div class="table-responsive">
                        <table class="table">

                            <thead>
                                <tr>
                                    <th>Fecha inicio</th>
                                    <th>Fecha fin</th>
                                    <th>Renovación</th>
                                    <th>Periodo ren. (meses)</th>
                                    <th>TipoContrato</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                <c:forEach items="${configs}" var="configuracion" >
                                
                                <form action="/CounterWebApp/desktop/configuracion/editarConfig" method="POST">

                                    <tr>
                                
                                    <input type="text" name="idContrato" value="${idContrato}" hidden></input>
                                    <input type="text" name="id" value="${idContrato}" hidden></input>
                                    <input type="text" name="idConfiguracion" value="${configuracion.id}" hidden></input>

                                    <td><input class="form-control" size="10" type="date" class="form_datetime" name="fechaInicio" value="${configuracion.fechaInicio}" disabled="disabled"></td>

                                    <td><input class="form-control" size="10" type="date" class="form_datetime" name="fechaFin" value="${configuracion.fechaFin}" disabled="disabled"></td>

                                    <td><input type="text" class="vert-align form-control" name="renovacion" value="${configuracion.renovacion}" disabled="disabled"></td>

                                    <td><input class="vert-align form-control" name="periodoRenov" value="${configuracion.periodoRenovacion}" disabled="disabled"></td>

                                    <td><input type="text" class="vert-align form-control" name="tipoContrato" value="${configuracion.tipoContrato}" disabled="disabled"></td>

                                    <td class="vert-align"><button class="btn btn-success" type="submit"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Ver/Editar </button></td>
                                </form>

                                <form action="/CounterWebApp/desktop/configuracion/copiarConfig" method="POST">
                                    <input type="text" name="idContrato" value="${idContrato}" hidden></input>
                                    <input type="text" name="idConfiguracion" value="${configuracion.id}" hidden></input>
                                    <td class="vert-align"><button class="btn btn-primary" type="submit"> <span class="glyphicon glyphicon-copy" aria-hidden="true"></span> Copiar </button></td>
                                </form>
                                </tr>
                            </c:forEach> 
                            </tbody>
                        </table>
                    
                    <br>

                    <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/agregarConfig" method="POST">
                        <input type="text" name="idContrato" value="${idContrato}" hidden></input>
                        <button class="btn btn-primary" id="boton" type="submit"> <span class="glyphicon glyphicon-plus"></span> Agregar nueva </button>
                    </form>
                    
                    <br>
                    <form class="form-horizontal" action="/CounterWebApp/desktop/contrato/tablaSocio" method="GET">
                        <button class="btn btn-default" id="boton" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                        <br><br>
                    </form>


                    
</div>
</div>
</div>
</body>