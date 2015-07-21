<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/styles.css" />">
    


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
            <h2 class="text-center">Gestión de Tipos de Hora</h2>
            <div>
                <form class="form-horizontal" action="/CounterWebApp/tiposDeHora/ingresar" method="POST">
                    
                    <div class="form-group">
                        <label for="inputDescripcion" class="control-label">Descripción:</label>
                        <label for="inputDescripcion" class="control-label">Tipo:</label>
                        <input type="tipoHora" class="form-control" name="tipoHora" id="inputTipoHora" placeholder="tipo de hora" required autofocus>        
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