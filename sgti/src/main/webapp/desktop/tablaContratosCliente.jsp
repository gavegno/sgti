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
            <h2 class="text-center">Contratos</h2>
            <div>
                <form class="form-horizontal">
                    
                    <div class="form-group">
                        <table class="table table-hover">

                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Cliente</th>
                                    <th>Contraparte</th>
                                </tr>
                            </thead>
                            
                            <tbody>

                            <c:forEach items="${contratos}" var="contrato" >
                                <form action="/CounterWebApp/desktop/tecnicos/editar" method="POST">
                                <tr >
                                    <td><input class="vert-align" name="id" value="${contrato.id}" hidden><c:out value="${contrato.id}" /></td>
                                    <td><input class="vert-align" name="cliente" value="${contrato.cliente.nombre}" hidden><c:out value="${contrato.cliente.nombre}" /></td>
                                    <td><input class="vert-align" name="contraparte" value="${contrato.contraparte.id}" hidden><c:out value="${contrato.contraparte.id}" /></td>
                                    <td class="vert-align"><button class="btn btn-info" id="boton" type="submit"> Ver </button></td>
                                </tr>
                                </form>
                            </c:forEach>
                        
                           </tbody>
                        </table>




                </form>
            </div>
        </div>
    </div>
</div>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"> </script>
    

</body>