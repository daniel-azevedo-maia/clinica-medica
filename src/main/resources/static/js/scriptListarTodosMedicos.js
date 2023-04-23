$.ajax({
  method: "GET",
  url: "http://localhost:8080/medicos/",
  contentType: "application/json; charset=utf-8",
  success: function (response) {    

    for(let i = 0; i < response.length; i++) {
      $('#tabelaPacientes > tbody').append('<tr id = ' + response[i].crm + '><td>'+ response[i].crm +'</td> <td> '+ response[i].nome +' </td><td><button class = "btn btn-primary" onclick="acessarPacientes(' + response[i].crm + ')">Acessar pacientes</button></td>  <td> ' + response[i].especialidade + '</td> <td> <button class = "btn btn-warning" onclick = editarPaciente()>Editar</button><button class = "btn btn-danger" onclick = deletarMedico(' + response[i].crm + ')>Deletar</button></td></tr>');

    }

  },
}).fail(function (xhr, status, errorThrown) {
  alert("Erro ao buscar médico: " + xhr.responseText);
});

function acessarPacientes(crm){
 
  window.location.href = '/listarPacientes.html?crmEncontrado=' + crm;
    
  }

  function deletarMedico(crm) {
    event.preventDefault();
  
    Swal.fire({
      title: "Você realmente deseja excluir este médico?",
      showCancelButton: true,
      confirmButtonText: "Sim, desejo excluir",
    }).then((result) => {
  
      if (result.isConfirmed) {
        $.ajax({
          method: "DELETE",
          url: "http://localhost:8080/medicos/" + crm,
          success: function (response) {
            Swal.fire("Médico excluído com sucesso!", "", "success");
            $("#" + crm).remove()
          },
        }).fail(function (xhr, status, errorThrown) {
          Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Nenhum médico consultado!",
          });
        });
  
      } else if (result.isDenied) {
        Swal.fire("Changes are not saved", "", "info");
      }
    });
  }