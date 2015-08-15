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
            <h2 class="text-center">Usuarios</h2>
            <div>
                
                    
                    <div class="form-group">
                        <table class="table table-hover">

                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Email</th>
                                    <th>Teléfono</th>  
                                    <th>Tipo</th>
                                </tr>
                            </thead>
                            
                            <tbody>

                            <c:forEach items="${usuarios}" var="usuario" >
                                <form action="/CounterWebApp/desktop/tecnicos/editar" method="POST">
                                <tr >
                                    <td><input class="vert-align" name="id" value="${usuario.id}" hidden><c:out value="${usuario.id}" /></td>
                                    <td><input class="vert-align" name="nombre" value="${usuario.nombre}" hidden><c:out value="${usuario.nombre}" /></td>
                                    <td><input class="vert-align" name="apellido" value="${usuario.apellido}" hidden><c:out value="${usuario.apellido}" /></td>
                                    <td><input class="vert-align" name="email" value="${usuario.email}" hidden><c:out value="${usuario.email}" /></td>
                                    <td><input class="vert-align" name="telefono" value="${usuario.telefono}" hidden><c:out value="${usuario.telefono}" /></td>
                                    <td><input class="vert-align" name="tipo" value="${usuario.tipo}" hidden><c:out value="${usuario.tipo}" /></td>
                                    <td class="vert-align"><button class="btn btn-info" id="boton" type="submit"> Editar </button></td>
                                </tr>
                                </form>
                            </c:forEach>
                        
                           </tbody>
                        </table>




            </div>
        </div>
    </div>
</div>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"> </script>
    

</body>