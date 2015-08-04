<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SGTI</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">   
    <link rel="stylesheet" href="css/jquery.dataTables.css">
    <link rel="stylesheet" href="css/datepicker.css">
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.css">
</head>
<body>
     <header>
        <div class="container">
        
            <h1>Sistema de Gestión de Técnicos e Incidentes</h1>
        
        </div>
    
    </header>   
</head>
<body>
    <div class="container">
  <table id="mt" class="table table-hover">
    <thead>
      <tr>
        <th>Tipo</th>
        <th>Creación</th>
        <th>A realizar el</th>
        <th>Responsable</th>
        <th>Ingresado por</th>
        <th>Cliente</th>
      </tr>
    </thead>
    <tbody>
        <form>
             <tr id="filaNueva" hidden>
                <td><input class="form-control" size="8" type="text" name="tipo" placeholder="Tipo"></td>
                <td><input size="16" type="text" class="form-control form_datetime" name="fechaCreacion"></td>
                <td><input size="16" type="text" class="form-control form_datetime" name="fechaRealizar"> </td>
                <td><input class="form-control" size="8" type="text" name="responsable" placeholder="Elija el recurso"></td>
                <td><input class="form-control" size="8" type="text" name="ingresadoPor" placeholder="Ingresado por"></td>
                <td><input class="form-control" size="8" type="text" name="cliente" placeholder="Elija cliente"></td>
                <td class="vert-align"><button id="addRow" class="btn btn-success">Confirmar</a></td>
              </tr>

        </form>
      <tr>
        <td><input class="form-control" size="8" type="text" name="tipo" value="Urgente" disabled></td>
        <td><input class="form-control" size="16" type="text" class="form_datetime" name="fechaCreacion" value="25-07-2015 11:12" disabled></td>
        <td><input class="form-control" size="16" type="text" class="form_datetime" name="fechaRealizar" value="25-07-2015 21:12" disabled></td>
        <td><input class="form-control" size="8" type="text" name="responsable" value="tecnico1" disabled></td>
        <td><input class="form-control" size="8" type="text" name="ingresadoPor" value="socio1" disabled></td>
        <td><input class="form-control" size="8" type="text" name="cliente" value="Cliente1" disabled></td>
        <td class="vert-align"><button id="addRow" class="btn btn-primary" onclick="document.getElementById('filaNueva').hidden = false" >Copiar </td>
      </tr>
    </tbody>
  </table>
<!--
  <br>
  <button id="addRow" class="btn btn-primary">
    Add row
  </div> -->
</div>
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"> </script>
    <script src="js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript">
    $(".form_datetime").datetimepicker({format: 'dd-mm-yyyy hh:ii'});
</script> 
</body>
</html>