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
            <h2 class="text-center">Gestión de usuario Contraparte</h2>
            <div>
                <form class="form-horizontal" action="editarContraparte" method="POST">
                    
                    <div class="form-group">
                        <label for="inputNombre" class="control-label">Nombre:</label>
                        <input type="nombre" class="form-control" id="inputNombre" placeholder="Nombre" required autofocus>        
                    </div>

                    <div class="form-group">
                        <label for="inputApellido" class="control-label">Apellido:</label>
                        <input type="apellido" class="form-control" id="inputApellido" placeholder="Apellido" required autofocus>        
                    </div>

                    <div class="form-group">
                        <label for="inputEmail" class="control-label">Email:</label>
                        <input type="email" class="form-control" id="inputEmail" placeholder="Email" required autofocus>        
                    </div>

                    <div class="form-group">
                        <label for="inputTelefono" class="control-label">Teléfono:</label>
                        <input type="telefono" class="form-control" id="inputTelefono" placeholder="Teléfono" required autofocus>        
                    </div>

                    <div class="form-group">
                        <label for="inputTipo" class="control-label">Tipo de usuario:</label>
                        <select class="form-control" id="inputTipo" disabled>
                            <option value="Contraparte">Contraparte</option>
                        </select>    
                    </div>

                    
                    
                <div class="form-group">
                    <button class="btn btn-success" id="boton" type="submit"> <span class="glyphicon glyphicon-ok"></span> Confirmar </button>
                    <button class="btn btn-default" type="button"> Cancelar </button>
                </div>       



                </form>
            </div>
        </div>
    </div>
</div>
</body>