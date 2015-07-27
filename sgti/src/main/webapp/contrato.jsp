<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        
        </div>
    
    </header>   
<br><br>
<div class="container">
    <div class="row">
        <div class="col-sm-10 col-md-8">
            <h2 class="text-center">Gestión de Contratos</h2>
            <div>
                <form class="form-horizontal" action="ingresarContrato" method="POST">
                    
                    <div class="form-group">
                        <label for="inputId" class="control-label">Id:</label>
                        <input type="text" class="form-control" name="id" id="inputId" placeholder="Id" required autofocus>        
                    </div>
                    
                    <div class="form-group">
                        <label for="inputCliente" class="control-label">Seleccione el cliente:</label>
                        <select class="form-control" id="inputCliente" name="cliente">
                            <c:forEach items="${clientes}" var="cliente" >
                        		<option value="${cliente.nombre}"><c:out value="${cliente.nombre}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                    <div class="form-group">
                        <label for="inputContraparte" class="control-label">Seleccione contraparte:</label>
                        <select class="form-control" id="inputContraparte" name="contraparte">
                            <c:forEach items="${contrapartes}" var="contraparte" >
                        		<option value="${contraparte.id}"><c:out value="${contraparte.nombre} ${contraparte.apellido}" /></option>
                        	</c:forEach>
                        </select>    
                    </div>

                <div class="form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Siguiente </button>
                    <button class="btn btn-default" type="button"> Cancelar </button>
                </div>       
                </form>
            </div>
        </div>
    </div>
</div>
   <!--  <script src="<c:url value="/resources/js/jquery.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"> </script>   -->
</body>