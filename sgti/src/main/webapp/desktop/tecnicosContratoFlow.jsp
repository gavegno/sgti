<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="es">
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
    <h2 class="container text-center">Paso 5 de 6: Asignación de técnicos al contrato </h2>
            <div>
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido} <br>
                    
                    <c:forEach items="${precios}" var="precio" >
                        Precio: fecha desde: ${precio.fechaDesde} --- fecha hasta: ${precio.fechaHasta} --- valor de precio: ${precio.precio} --- valor precio extra: ${precio.precioExtra} <br>
                    </c:forEach> 

                    <c:forEach items="${tiposDeHoraAsignados}" var="tipoAsignado" >
                        Tipo de hora: ${tipoAsignado.tipoHora.tipo} --- factor de cómputo: ${tipoAsignado.computo} <br>
                    </c:forEach> 

                </div>

                    <div class="container">
                        <table class="table">

                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>

                                </tr>
                            </thead>
                            
                            <tbody>
                                


                                <c:forEach items="${tecnicos}" var="tecnico" >
                                <tr>
                                    <form class="form-control" action="/CounterWebApp/desktop/contrato/sacarTecnicoDesdeFlow" method="POST">
                                        <input class="form-control" type="hidden" name="id" value="${idContrato}" />
                                        <input class="form-control" type="hidden" name="idUsuario" value="${tecnico.id}" />
                                        <td><input class="form-control" size="20" type="text" name="idUsuario" value="${tecnico.id}" disabled="disabled"></td>
                                        <td><input class="form-control" size="20" type="text" name="nombre" value="${tecnico.nombre}" disabled="disabled" ></td>
                                        <td><input class="form-control" size="20" type="text" name="apellido" value="${tecnico.apellido}" disabled="disabled"></td>

                                        <td class="vert-align"><button class="btn btn-danger" type="submit" title="Sacar de este contrato"> <span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></td>
                                    
                                    
                                    </form>
                                </tr>
                                </c:forEach>
                        
                           </tbody>
                        </table>


                        <br>
                        <form class="form-horizontal" action="/CounterWebApp/desktop/contrato/asignarTecnicoDesdeFlow" method="POST">
                            <input class="form-control" type="hidden" name="id" value="${idContrato}" />

                        <div class="row">
                            <div class="col-lg-2">
                                <div class="input-group">
                                    <label for="inputContrato" class="control-label">Asignar técnico a contrato: </label>
                                </div>
                            </div>

                            <div class="col-lg-3">
                                <div class="input-group">
                                    <select class="form-control" id="inputTecnico" name="tecnicoAgregar">
                                        <c:if test="${empty tecnicosCandidatos}">
                                            <option value="0"> <c:out value="No quedan técnicos" /></option>
                                        </c:if>

                                        <c:forEach items="${tecnicosCandidatos}" var="candidato" >
                                            <option value="${candidato.id}"> <c:out value="${candidato.nombre} ${candidato.apellido}" /></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="col-lg-1">
                                <div class="input-group">

                                    <c:if test="${empty tecnicosCandidatos}">
                                        <span class="input-group-btn">
                                            <button class="btn btn-success" id="boton" type="submit" disabled="disabled"> <span class="glyphicon glyphicon-ok"></span></button>
                                        </span>  
                                    </c:if>

                                    <c:if test="${not empty tecnicosCandidatos}">
                                        <span class="input-group-btn">
                                            <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span></button>
                                        </span>  
                                    </c:if>

                                </div>
                            </div> 
                                
                        </div>
                        </form>

                        
                        <br><br>
                        <form class="form-horizontal" action="/CounterWebApp/desktop/configuracion/ingresar" method="POST">
                            <input type="hidden" class="form-control" name="idContrato" value="${idContrato}">
                            <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Pasar al paso 6 </button>
                            <br><br>
                        </form>
            </div>
        </div>
    </div>

</body>