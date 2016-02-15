<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <h2 class="text-center">Contratos</h2>
            <div>
               
                    
                    <div class="form-group">
                        <input class="form-control" id="system-search" name="q" placeholder="Buscar..." required>
                        <table class="table table-hover table-list-search">

                            <thead>
                                <tr>
                                    <th>Contrato</th>
                                    <th>Cliente</th>
                                    <th>Contraparte</th>
                                    <th>Tipo</th>
                                    <th>Fin_Vig. configuración</th>
                                    <th>Precio actual</th>
                                    <th>Fin Vig. precio</th>
                                </tr>
                            </thead>
                            
                            <tbody class="searchable">

                            <c:forEach items="${contratos}" var="contrato" >
                                <form action="/Sgti/desktop/configuracion/tablaConfiguraciones" method="POST">
                                <tr >
                                    <input type="text" name="id" value="${contrato.id}" hidden></input>
                                    
                                    <td><input class="vert-align form-control" name="id" value="${contrato.id}" disabled="disabled"></td>
                                    <td hidden="hidden">${contrato.id}</td>
                                    <td><input class="vert-align form-control" name="cliente" value="${contrato.cliente.nombre}" disabled="disabled"></td>
                                    <td hidden="hidden">${contrato.cliente.nombre}</td>
                                    <td><input class="vert-align form-control" name="contraparte" value="${contrato.contraparte.id}" disabled="disabled"></td>
                                    <td hidden="hidden">${contrato.contraparte.id}</td>


                                    <c:set var="encontreConfiguracion" value="false" />
                                    <c:forEach items="${configuraciones}" var="configuracion" >
                                        <c:if test="${configuracion.idContrato  == contrato.id}">
                                            <c:set var="encontreConfiguracion" value="true" />
                                            <td><input class="vert-align form-control" id="tipo" name="tipoContrato" value="${configuracion.tipoContrato}" disabled="disabled"></td>
                                            <td hidden="hidden">${configuracion.tipoContrato}</td>
                                            <fmt:formatDate value="${configuracion.fechaFin}" var="formattedfechaConfigFin" type="date" pattern="dd-MM-yyyy" />
                                            <td><input class="vert-align form-control" id="finConfig" name="configFin" value="${formattedfechaConfigFin}" disabled="disabled"></td>
                                            <td hidden="hidden">${formattedfechaConfigFin}</td>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${encontreConfiguracion eq false}">
                                        <td><input class="vert-align form-control" name="tipoContrato" value="" disabled="disabled"></td>
                                        <td><input class="vert-align form-control" name="configFin" value="" disabled="disabled"></td>
                                    </c:if>

                                    




                                    <c:set var="encontrePrecio" value="false" />
                                    <c:forEach items="${precios}" var="precio" >
                                        <c:if test="${precio.idContrato  == contrato.id}">
                                            <c:set var="encontrePrecio" value="true" />
                                            <td><input class="vert-align form-control" name="Precio" value="${precio.precio}" disabled="disabled"></td>
                                            <td hidden="hidden">${precio.precio}</td>
                                            <fmt:formatDate value="${precio.fechaHasta}" var="formattedfechaPrecioFin" type="date" pattern="dd-MM-yyyy" />
                                            <td><input class="vert-align form-control" name="PrecioFin" value="${formattedfechaPrecioFin}" disabled="disabled"></td>
                                            <td hidden="hidden">${formattedfechaPrecioFin}</td>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${encontrePrecio eq false}">
                                        <td><input class="vert-align form-control" name="Precio" value="" disabled="disabled"></td>
                                        <td><input class="vert-align form-control" name="PrecioFin" value="" disabled="disabled"></td>
                                    </c:if>
                                
                                    <td class="vert-align"><button class="btn btn-primary" type="submit" title="Configuraciones"> <span class="glyphicon glyphicon-cog" aria-hidden="true"></span></button></td>
                                </form>

                                <form action="/Sgti/desktop/precio/tablaPrecios" method="POST">
                                    <input type="text" name="id" value="${contrato.id}" hidden></input>
                                    
                                    <td class="vert-align"><button class="btn btn-primary" type="submit" title="Precios"> <span class="glyphicon glyphicon-usd" aria-hidden="true"></span></button></td>
                                </form>

                                <form action="/Sgti/desktop/tiposDeHora/tablaHorasContrato" method="POST">
                                    <input type="text" name="id" value="${contrato.id}" hidden></input>
                                    
                                    <td class="vert-align"><button class="btn btn-primary" type="submit" title="Tipos de hora"> <span class="glyphicon glyphicon-time" aria-hidden="true"></span></button></td>
                                </form>

                                <form action="/Sgti/desktop/contrato/tecnicosEnContrato" method="POST">
                                    <input type="text" name="id" value="${contrato.id}" hidden></input>
                                    
                                    <td class="vert-align"><button class="btn btn-primary" type="submit" title="TÃ©cnicos asignados"> <span class="glyphicon glyphicon-user" aria-hidden="true"></span></button></td>
                                </form>

                                    
                                </tr>
                                
                            </c:forEach>
                        
                           </tbody>
                        </table>




            </div>
        </div>
</div>
<script src="<c:url value="/resources/js/busqueda-tablas.js"/>"></script>
</body>