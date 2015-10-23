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
                                    <th>Id</th>
                                    <th>Cliente</th>
                                    <th>Contraparte</th>
                                </tr>
                            </thead>
                            
                            <tbody>

                            <c:forEach items="${contratos}" var="contrato" >
                                <tr>
                                    

                                    <td><input class="vert-align form-control" name="id" value="${contrato.id}" disabled="disabled"></td>
                                    <td><input class="vert-align form-control" name="cliente" value="${contrato.cliente.nombre}" disabled="disabled"></td>
                                    <td><input class="vert-align form-control" name="contraparte" value="${contrato.contraparte.id}" disabled="disabled"></td>
                                   
                                    <form action="/CounterWebApp/desktop/hora/tablaHorasContraparte" method="POST">

                                        <input type="text" name="id" value="${contrato.id}" hidden></input>
                                        <td class="vert-align"><button class="btn btn-primary" type="submit"> <span class="glyphicon glyphicon-time" aria-hidden="true"></span> Ver horas </button></td>
                                    </form>

                                </tr>
                            </c:forEach>
                        
                           </tbody>
                        </table>




                
            </div>
        </div>
</div>
</body>