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
            <h2 class="text-center">Clientes</h2>
            <div>
                <form class="form-horizontal">
                    
                    <div class="form-group">
                        <table class="table table-hover">

                            <thead>
                                <tr>
                                    
                                    <th>Nombre</th>
                                    <th>Dirección</th>
                                    <th>Teléfono</th>
                                </tr>
                            </thead>
                            
                            <tbody>

                            <tr >
                                
                                <td class="vert-align">Tienda Inglesa S.A.</td>
                                <td class="vert-align">Av. Italia 1234</td>
                                <td class="vert-align">26112541</td>                                                    
                                <td class="vert-align"><a class="btn btn-info" href="{site_url()}admin/info/1">Ver</a></td>

                            </tr>
                            <tr >
                                
                                <td class="vert-align">Mosca Hnos.</td>
                                <td class="vert-align">Ellauri 971</td>
                                <td class="vert-align">27115977</td>                                                    
                                <td class="vert-align"><a class="btn btn-info" href="{site_url()}admin/info/2">Ver</a></td>

                            </tr>
                        
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