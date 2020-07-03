function mostrarTabla(tabla){
	var ruta;
	var mensaje;
	var rutaBtn;
	var extra;
	switch (tabla) {
		case 1:
			ruta="./cargarUsuario";
			mensaje="Editar usuario";
			rutaBtn = "./editarUsuario";
			extra="&tipo=1"
			break;
		case 2:
			ruta="./cargarclientes";
			mensaje="Editar materia";
			rutaBtn = "./editarMateria";
			extra=""
			break;
		case 3:
			ruta="./cargarCentrosEscolares";
			mensaje="Editar centro escolar";
			rutaBtn = "./editarCentroEscolar";
			extra=""
			break;
	}
	datosTabla(ruta, mensaje, rutaBtn, extra);
}

function datosTabla(tabla, mensaje, rutaBtn, extra){
	$(document).ready(function(){
		var table = $('#tabla').DataTable( {
			"ordering": false,
	        "processing": true,
	        "serverSide": true,
	        "ajax": tabla,
	        "language": {
	            "url": "//cdn.datatables.net/plug-ins/1.10.19/i18n/Spanish.json"
	        },
	        "pageLength": 5,
	        "columnDefs": [ {
	            "targets": 0,
	            "data": null,
	            "searchable": false,
	            "defaultContent": "<button class='btn btn-info' title='"+mensaje+"' id='i'><i class='fa fa-pencil-alt'></i></button>"
	        }]
	    });
		$('#tabla tbody').on( 'click', '#i', function () {
	        var data = table.row( $(this).parents('tr') ).data();
	        	location.href = './'+rutaBtn+'?id=' + data[0] + extra;
	    } );
	});
}