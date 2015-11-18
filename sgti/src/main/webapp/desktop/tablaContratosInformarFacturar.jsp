<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
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
    <div class="row">
            <h2 class="text-center">Contratos</h2>
            <div>
               
                    
                    <div class="form-group">
                        <table class="table table-hover">

                            <thead>
                                <tr>
                                    <th>Contrato</th>
                                    <th>Cliente</th>
                                    <th>Contraparte</th>
                                    <th>Tipo</th>
                                    <th>Fin_Vig. configuraciÃ³n</th>
                                    <th>Precio actual</th>
                                    <th>Fin Vig. precio</th>
                                </tr>
                            </thead>
                            
                            <tbody>

                            <c:forEach items="${contratos}" var="contrato" >
                                <form action="/CounterWebApp/desktop/informe/informarHoras" method="POST">
                                <tr >
                                    <input type="text" name="id" value="${contrato.id}" hidden></input>
                                    
                                    <td><input class="vert-align form-control" name="id" value="${contrato.id}" disabled="disabled"></td>
                                    <td><input class="vert-align form-control" name="cliente" value="${contrato.cliente.nombre}" disabled="disabled"></td>
                                    <td><input class="vert-align form-control" name="contraparte" value="${contrato.contraparte.id}" disabled="disabled"></td>

                                    


                                    <c:set var="encontreConfiguracion" value="false" />
                                    <c:forEach items="${configuraciones}" var="configuracion" >
                                        <c:if test="${configuracion.idContrato  == contrato.id}">
                                            <c:set var="encontreConfiguracion" value="true" />
                                            <td><input class="vert-align form-control" id="tipo" name="tipoContrato" value="${configuracion.tipoContrato}" disabled="disabled"></td>
                                            <fmt:formatDate value="${configuracion.fechaFin}" var="formattedfechaConfigFin" type="date" pattern="dd-MM-yyyy" />
                                            <td><input class="vert-align form-control" id="finConfig" name="configFin" value="${formattedfechaConfigFin}" disabled="disabled"></td>
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
                                            <fmt:formatDate value="${precio.fechaHasta}" var="formattedfechaPrecioFin" type="date" pattern="dd-MM-yyyy" />
                                            <td><input class="vert-align form-control" name="PrecioFin" value="${formattedfechaPrecioFin}" disabled="disabled"></td>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${encontrePrecio eq false}">
                                        <td><input class="vert-align form-control" name="Precio" value="" disabled="disabled"></td>
                                        <td><input class="vert-align form-control" name="PrecioFin" value="" disabled="disabled"></td>
                                    </c:if>
                                
                                    <td class="vert-align"><button class="btn btn-primary" type="submit">Generar Informe</button></td>
                                </form>
                                
		                     	<form action="/CounterWebApp/desktop/informe/validarInforme" method="POST" accept-charset="UTF-8" style="display:inline">
		
		                           <input type="text" name="id" value="${contrato.id}" hidden></input>
		                           <td class="vert-align"><button type="button" class="btn" data-toggle="modal" data-target="#myModal"><span class="glyphicon" aria-hidden="true"></span> Validar Informe </button></td>
		
		                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		                              <div class="modal-dialog" role="document">
		                                <div class="modal-content">
		                                  <div class="modal-header">
		                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		                                    <h4 class="modal-title" id="myModalLabel">Validar Informe</h4>
		                                  </div>
		                                  <div class="modal-body">
		                                    <p>¿Realmente desea validar el informe?
		                                    No se podrán volver a informar las horas con fecha de informe ${contratosFechaInforme[contrato.id]}</p>
		                                  </div>
		                                  <div class="modal-footer">
		                                    <button type="button" class="btn btn-default" data-dismiss="modal"> Cancelar </button>
		                                    <button type="submit" class="btn btn-danger"> Confirmar </button>
		                                  </div>
		                                </div>
		                              </div>
		                            </div>
		                        </form>
                             </tr>
                            </c:forEach>
                        
                           </tbody>
                        </table>




            </div>
        </div>
</div>
</body>