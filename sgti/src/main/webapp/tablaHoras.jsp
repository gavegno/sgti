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
        <th>Inicio</th>
        <th>Fin</th>
        <th>¿Remota?</th>
        <th>Contrato</th>
        <th>Actividad</th>
        <th>Descripción</th>
      </tr>
    </thead>
    <tbody>
        <form>
             <tr id="filaNueva" hidden>
                <td><input size="8" class="form-control" type="text" name="tipo" placeholder="Tipo"></td>
                <td><input size="16" type="text" class="form-control form_datetime" name="inicio"></td>
                <td><input size="16" type="text" class="form-control form_datetime" name="fin"></td>
                <td><select class="form-control" name="sesionRemota">
                            <option value="No">No</option>
                            <option value="Si">Si</option>
                        </select></td>
                <td><input class="form-control" size="8" type="text" name="contrato" placeholder="Elija un contrato"></td>
                <td><input class="form-control" size="8" type="text" name="actividad" placeholder="Elija una actividad"></td>
                <td><textarea class="form-control" rows="2" name="descripcion" placeholder="Descripción"></textarea></td>
                <td class="vert-align"><button id="addRow" class="btn btn-success">Confirmar</a></td>
              </tr>

        </form>
      <tr>
        <td><input size="8" class="form-control" type="text" name="tipo" value="Urgente" disabled></td>
        <td><input size="16" type="text" class="form-control form_datetime" name="inicio" value="25-07-2015 11:12" disabled></td>
        <td><input size="16" type="text" class="form-control form_datetime" name="fin" value="25-07-2015 21:12" disabled></td>
        <td><input class="form-control" size="8" type="text" name="sesionRemota" value="No" disabled></td>
        <td><input class="form-control" size="8" type="text" name="contrato" value="Contrato1" disabled></td>
        <td><input class="form-control" size="8" type="text" name="actividad" value="Actividad1" disabled></td>
        <td><textarea class="form-control" rows="2" disabled>Descripcion de prueba</textarea></td>
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