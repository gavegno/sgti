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
            <h2 class="text-center">Edición de Socios y Técnicos</h2>
            <div>
                <form class="form-horizontal" action="editarSocioTecnico" method="POST">
                    
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
                        <select class="form-control" id="inputTipo">
                            <option value="Socio">Socio</option>
                            <option value="Técnico">Técnico</option>
                        </select>    
                    </div>

                    <div class="form-group">
                        <label for="inputTipoHora" class="control-label">Tipos de horas:</label>
                        <select multiple class="form-control" id="inputTipoHora">
                            <option value="1">Tipo de hora 1</option>
                            <option value="2">Tipo de hora 2</option>
                            <option value="3">Tipo de hora 3</option>
                            <option value="4">Tipo de hora 4</option>
                            <option value="5">Tipo de hora 5</option>
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