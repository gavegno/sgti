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
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Hora</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/desktop/hora/detalleHoraOk" method="POST">
                    
                    <input type="text" name="id" value="${hora.id}" hidden></input>
                    <input type="text" name="fechaDesde" value="${hora.fechaDesde}" hidden></input>
                    <input type="text" name="fechaHasta" value="${hora.fechaHasta}" hidden></input>
                    <input type="text" name="nombreTipoHora" value="${hora.nombreTipoHora}" hidden></input>
                    <input type="text" name="descripcion" value="${hora.descripcion}" hidden></input>
                    <input type="text" name="remoto" value="${hora.remoto}" hidden></input>
                    <input type="text" name="idContrato" value="${hora.idContrato}" hidden></input>
                    <input type="text" name="idActividad" value="${hora.idActividad}" hidden></input>


                    <div class="form-group">
                        <label for="usuario" class="control-label">Usuario:</label>
                        <input type="text" class="form-control" name="idUsuario" value="${hora.idUsuario}" disabled="disabled"></input>        
                    </div>

                    <div class="form-group">
                        <label for="fechainformar" class="control-label">Fecha Informar: actual:
                            <c:choose>
                            <c:when test="${empty hora.fechaInformar}">
                                No especificado 
                            </c:when>    
                            <c:otherwise>
                                <fmt:formatDate value="${hora.fechaInformar}" var="formattedfechaInformarActual" type="date" pattern="dd-MM-yyyy" />${formattedfechaInformarActual} 
                            </c:otherwise>
                        </c:choose> </label>
                        <select class="form-control form_datetime" name="fechaInformar" id="fechaInformar">
                            <c:forEach items="${fechasInforme}" var="fechaInforme" >
                                <fmt:formatDate value="${fechaInforme}" var="formattedfechaInforme" type="date" pattern="dd-MM-yyyy" />
                                <option value="${formattedfechaInforme}"><c:out value="${formattedfechaInforme}" /></option>
                                
                            </c:forEach>
                            <option value="nulo"><c:out value="No especificado" /></option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="fechafacturar" class="control-label">Fecha Facturar: actual:
                            <c:choose>
                            <c:when test="${empty hora.fechaFacturar}">
                                No especificado 
                            </c:when>    
                            <c:otherwise>
                                <fmt:formatDate value="${hora.fechaFacturar}" var="formattedfechaFacturacionActual" type="date" pattern="dd-MM-yyyy" />${formattedfechaFacturacionActual} 
                            </c:otherwise>
                        </c:choose> </label>

                        <select class="form-control form_datetime" name="fechaFacturar" id="fechafacturar">
                            <c:forEach items="${fechasFacturacion}" var="fechaFacturacion" >
                                <fmt:formatDate value="${fechaFacturacion}" var="formattedfechaFacturacion" type="date" pattern="dd-MM-yyyy" />
                                <option value="${formattedfechaFacturacion}"><c:out value="${formattedfechaFacturacion}" /></option>

                            </c:forEach>
                            <option value="nulo">No especificado</option>
                            </select>
                    </div>

                    <div class="form-group">
                        <label for="fechacomputar" class="control-label">Fecha Computar: actual:
                        <c:choose>
                            <c:when test="${empty hora.fechaComputar}">
                                No especificado 
                            </c:when>    
                            <c:otherwise>
                                <fmt:formatDate value="${hora.fechaComputar}" var="formattedfechaComputarActual" type="date" pattern="dd-MM-yyyy" />${formattedfechaComputarActual} 
                            </c:otherwise>
                        </c:choose> </label>
                        
                        <select class="form-control form_datetime" name="fechaComputar" id="fechacomputar">
                            <c:forEach items="${fechasComputacion}" var="fechaComputar" >
                                <fmt:formatDate value="${fechaComputar}" var="formattedfechaComputar" type="date" pattern="dd-MM-yyyy" />
                                <option value="${formattedfechaComputar}"><c:out value="${formattedfechaComputar}" /></option>
                                
                            </c:forEach>
                            <option value="nulo">No especificado</option>
                            </select>

                    </div>

                    <div class="form-group">
                        <label for="informada" class="control-label">Informada?</label>
                        <c:choose> 
                            <c:when test="${hora.informada}">
                                <input type="checkbox" class="checkbox" checked="checked" name="informada" disabled="disabled"></input>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" class="checkbox" name="informada" disabled="disabled"></input>
                            </c:otherwise>
                        </c:choose> 
                    </div>

                    <div class="form-group">
                        <label for="facturada" class="control-label">Facturada?</label>
                        <c:choose> 
                            <c:when test="${hora.facturada}">
                                <input type="checkbox" class="checkbox" checked="checked" name="facturada" disabled="disabled"></input>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" class="checkbox" name="facturada" disabled="disabled"></input>
                            </c:otherwise>
                        </c:choose> 
                    </div>

                    <div class="form-group">
                        <label for="validada" class="control-label">Validada?</label>
                        <c:choose> 
                            <c:when test="${hora.validada}">
                                <input type="checkbox" class="checkbox" checked="checked" name="validada" disabled="disabled" title="Esta propiedad se cambia desde la tabla de horas" </input>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" class="checkbox" name="validada" disabled="disabled" title="Esta propiedad se cambia desde la tabla de horas"></input>
                            </c:otherwise>
                        </c:choose> 
                    </div>
                       
                
                <div class="container form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                    </form>

                    <form class="form-horizontal" action="/CounterWebApp/desktop/hora/borrar" method="POST" accept-charset="UTF-8" style="display:inline">
                        <input type="text" name="id" value="${hora.id}" hidden></input>          
                                  
                        <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Borrar </button>


                            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                              <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                  <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">Eliminar la hora</h4>
                                  </div>
                                  <div class="modal-body">
                                    ¿Realmente desea eliminar la hora?
                                  </div>
                                  <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal"> Cancelar </button>
                                    <button type="submit" class="btn btn-danger"> Confirmar </button>
                                  </div>
                                </div>
                              </div>
                            </div>
                    </form>

                    <form class="form-horizontal" action="/CounterWebApp/desktop/hora/ingresar" method="GET" accept-charset="UTF-8" style="display:inline">                    
                        
                        <button class="btn btn-default" type="submit"> <span class="glyphicon glyphicon-menu-left"></span> Cancelar </button>
                    </form>
                </div>
                       
            <br><br>
            </div>
        </div>
    </div>
</div>
</body>
</html>