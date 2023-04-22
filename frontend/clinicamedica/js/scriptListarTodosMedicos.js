$.ajax({
  method: "GET",
  url: "http://localhost:8080/medicos/",
  contentType: "application/json; charset=utf-8",
  success: function (response) {    

    for(let i = 0; i < response.length; i++) {
      $('#tabelaPacientes > tbody').append('<tr id = ' + response[i].crm + '><td>'+ response[i].crm +'</td> <td> '+ response[i].nome +' </td><td><button class = "btn btn-primary" onclick="acessarPacientes(' + response[i].crm + ')">Acessar pacientes</button></td>  <td> ' + response[i].especialidade + '</td> <td> <button class = "btn btn-warning" onclick = editarPaciente()>Editar</button><button class = "btn btn-danger" onclick = deletarPaciente(' + response[i].id + ')>Deletar</button></td></tr>');

    }

  },
}).fail(function (xhr, status, errorThrown) {
  alert("Erro ao buscar médico: " + xhr.responseText);
});

function acessarPacientes(crm){
 
  window.location.href = '/clinicamedica/listarPacientes.html?crmEncontrado=' + crm;
    
  }

