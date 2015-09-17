<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
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

            <h2 class="text-center">Tipos de hora de contrato</h2>
            <div>
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.id}
                </div>

                    <div class="table-responsive">
                        <table class="table">

                            <thead>
                                <tr>
                                    <th>Tipo de hora</th>
                                    <th>Factor de cómputo</th>
                                </tr>
                            </thead>
                            
                            <tbody>
                                

                                <c:forEach items="${tiposHoraContrato}" var="tipo" >
                                <tr>
                                <form action="/CounterWebApp/desktop/tiposDeHora/editarContratoTipoHora" method="POST">
                                        
                                
                                    <input type="text" name="id" value="${id}" hidden></input>
                                    <input type="text" name="tipoId" value="${tipo.tipoHora.id}" hidden></input>
                                    <input type="text" name="tipoNombre" value="${tipo.tipoHora.tipo}" hidden></input>

                                    <td><input class="form-control" type="text" name="tipoNombre" value="${tipo.tipoHora.tipo}" disabled="disabled"></td>
                                    <td><input class="form-control" step="any" type="number" name="factorComputo" value="${tipo.computo}"></td>


                                    <td class="vert-align"><button class="btn btn-success" type="submit"> <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Guardar </button></td>
                                </form>

                                <form action="/CounterWebApp/desktop/tiposDeHora/borrarContratoTipoHora" method="POST">
                                    <input type="text" name="id" value="${id}" hidden></input>
                                    <input type="text" name="tipoId" value="${tipo.tipoHora.id}" hidden></input>

                                    <td class="vert-align"><button class="btn btn-danger" type="submit"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Borrar </button></td>
                                </form>
                            </tr>
                            </c:forEach> 
                            </tbody>
                        </table>
                    
                    <br>

                    <form class="form-horizontal" action="/CounterWebApp/desktop/tiposDeHora/agregarContratoTipoHora" method="POST">
                        <input type="text" name="id" value="${id}" hidden></input>
                        <button class="btn btn-primary" id="boton" type="submit"> <span class="glyphicon glyphicon-plus"></span> Agregar nuevo </button>
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