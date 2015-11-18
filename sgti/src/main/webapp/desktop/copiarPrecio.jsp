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
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="container text-center">Ver / editar precio</h2>

            <div class="form-group container">
                <div class="alert alert-info text-center">
                    Contrato: ${contrato.id}  ---  Cliente: ${contrato.cliente.nombre}  ---  Contraparte: ${contrato.contraparte.nombre} ${contrato.contraparte.apellido}
                </div>


                <form action="/CounterWebApp/desktop/precio/copiarPrecioOk" method="POST">
                    <input type="text" name="idContrato" value="${idContrato}" hidden></input></input>

                    <div class="form-group container">
                        <label for="fechaDesde" class="control-label">Inicio de vigencia</label>
                        <input  type="date" id="fechaDesde" class="form-control" name="fechaDesde" value="${precio.fechaDesde}" required>
                    </div>
                    

                    <div class="form-group container">
                        <label for="fechaHasta" class="control-label">Fin de vigencia</label>
                        <input  type="date" id="fechaHasta" class="form-control" name="fechaHasta" value="${precio.fechaHasta}" required>
                    </div>
                    
                    <div class="form-group container">
                        <label for="precio" class="control-label">Valor de precio: </label>
                        <input type="number" step="any" id="precio" class="form-control" name="valorPrecio" value="${precio.precio}" required>
                    </div> 
                    
                    <div class="form-group container">
                        <label for="precio" class="control-label">Valor de precio de cómputo extra: </label>
                        <input type="number" step="any" id="precioExtra" class="form-control" name="valorPrecioExtra" value="${precio.precioExtra}" required>
                    </div> 
                    



                       

                    <div class="form-group container">
                        <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Guardar </button>
                </form>

                        
                    </div>      
                
                <form class="form-horizontal" action="/CounterWebApp/desktop/precio/tablaPrecios" method="POST">
                    <div class="form-group container">
                    <input type="text" name="id" value="${idContrato}" hidden></input>
                    
                        <button class="btn btn-default" id="boton" type="submit"> <span class="glyphicon glyphicon-chevron-left"></span> Volver </button>
                        <br><br>
                    </div>       
                    
                </form>

            </div>
        </div>
    </div>
</div>
</body>