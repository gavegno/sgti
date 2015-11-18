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

            <h2 class="text-center">Tipos de hora de técnico</h2>
             <div class="alert alert-info text-center">
                    Técnico: ${tecnico.nombre} ${tecnico.apellido}
                </div>
            <div class="row">
            <div class="col-sm-3 col-md-3">
               


                    <div class="table-responsive">
                        <table class="table">

                            <thead>
                                <tr>
                                    <th>Tipo de hora</th>
                                    
                                </tr>
                            </thead>
                            
                            <tbody>
                                

                                <c:forEach items="${tiposHoraTecnico}" var="tipo" >
                                <tr>
                                <form action="/CounterWebApp/desktop/tiposDeHora/borrarTipoHoraTecnico" method="POST">
                                        
                                
                                    <input type="text" name="id" value="${id}" hidden></input>
                                    <input type="text" name="tipoId" value="${tipo.id}" hidden></input>
                                    <input type="text" name="tipoNombre" value="${tipo.tipo}" hidden></input>

                                    <td><input class="form-control" type="text" name="tipoNombre" value="${tipo.tipo}" disabled="disabled"></td>
                                    <td class="vert-align"><button class="btn btn-danger" type="submit"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button></td>

                                </form>
                            </tr>
                            </c:forEach> 
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

                        <br>
                        <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/asignarTipoHoraTecnico" method="POST">
                            <input class="form-control" type="hidden" name="id" value="${id}" />

                        <div class="row">
                            <div class="col-lg-2">
                                <div class="input-group">
                                    <label for="inputTipo" class="control-label">Asignar tipo de hora: </label>
                                </div>
                            </div>

                            <div class="col-lg-3">
                                <div class="input-group">
                                    <select class="form-control" id="inputTipo" name="tipoHoraAgregar">
                                        <c:if test="${empty tipoHorasCandidatos}">
                                            <option value="0"> <c:out value="No quedan tipos de hora" /></option>
                                        </c:if>

                                        <c:forEach items="${tipoHorasCandidatos}" var="candidato" >
                                            <option value="${candidato.id}"> <c:out value="${candidato.tipo}" /></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="col-lg-1">
                                <div class="input-group">

                                    <c:if test="${empty tipoHorasCandidatos}">
                                        <span class="input-group-btn">
                                            <button class="btn btn-success" id="boton" type="submit" disabled="disabled"> <span class="glyphicon glyphicon-ok"></span></button>
                                        </span>  
                                    </c:if>

                                    <c:if test="${not empty tipoHorasCandidatos}">
                                        <span class="input-group-btn">
                                            <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span></button>
                                        </span>  
                                    </c:if>

                                </div>
                            </div> 
                                
                        </div>
                        </form>

                        
                        <br><br>
                    
                    <form class="form-horizontal" action="/CounterWebApp/desktop/tecnicos/tabla" method="GET">
                        <button class="btn btn-default" id="boton" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                        <br><br>
                    </form>

</div>
</body>