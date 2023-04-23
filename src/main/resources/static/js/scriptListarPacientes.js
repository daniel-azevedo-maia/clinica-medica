const urlParams = new URLSearchParams(window.location.search);

const crm = urlParams.get("crmEncontrado");

$.ajax({
  method: "GET",
  url: "http://localhost:8080/medicos/" + crm,
  contentType: "application/json; charset=utf-8",
  success: function (response) {    
    
    document.getElementById("titulo").innerHTML = "Pacientes de " + response.nome + " | CRM nº " + response.crm

    $.ajax({
      method: "GET",
      url: "http://localhost:8080/medicos/" + crm + "/pacientes",
      contentType: "application/json; charset=utf-8",
      success: function (response) {    

        if (response.length == 0) {
          $('#semPacientes').append('<button class="btn btn-success" onclick="cadastrarNovo()">Cadastrar novo paciente</button>');
        }
        
        for(let i = 0; i < response.length; i++) {
          $('#tabelaPacientes > tbody').append('<tr id = ' + response[i].id + '><td>'+ response[i].id +'</td> <td> '+ response[i].nome +' </td> <td> <button class = "btn btn-warning" onclick = editarPaciente()>Editar<button class = "btn btn-danger" onclick = deletarPaciente(' + response[i].id + ')>Deletar</td></tr>');
        }
    
      },
    }).fail(function (xhr, status, errorThrown) {
      alert("Erro ao buscar médico: " + xhr.responseText);
    });

  },
}).fail(function (xhr, status, errorThrown) {
  alert("Erro ao buscar médico: " + xhr.responseText);
});

function deletarPaciente(id) {
  event.preventDefault();

  Swal.fire({
    title: "Você realmente deseja excluir este paciente?",
    showCancelButton: true,
    confirmButtonText: "Sim, desejo excluir",
  }).then((result) => {

    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: "http://localhost:8080/pacientes/" + id,
        success: function (response) {
          Swal.fire("Paciente excluído com sucesso!", "", "success");
          $("#" + id).remove()
        },
      }).fail(function (xhr, status, errorThrown) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Nenhum paciente consultado!",
        });
      });

      document.getElementById("formBusca").reset();
    } else if (result.isDenied) {
      Swal.fire("Changes are not saved", "", "info");
    }
  });
}


function cadastrarNovo(){
 
  window.location.href = 'cadastrarpaciente.html';
    
  }