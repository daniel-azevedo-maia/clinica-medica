function buscarMedico(event) {
  event.preventDefault();

  $.ajax({
    method: "GET",
    url: "http://localhost:8080/medicos/" + $("#crm").val(),
    contentType: "application/json; charset=utf-8",
    success: function (response) {
      $("#nomeEncontrado").val(response.nome);
      $("#crmEncontrado").val(response.crm);
      $("#especialidadeEncontrada").val(response.especialidade);
    },
  }).fail(function (xhr, status, errorThrown) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Médico não encontrado!",
    });
  });
}

function removerMedico() {
  event.preventDefault();

  Swal.fire({
    title: "Você realmente deseja excluir este médico?",
    showCancelButton: true,
    confirmButtonText: "Sim, desejo excluir",
  }).then((result) => {
    /* Read more about isConfirmed, isDenied below */
    if (result.isConfirmed) {
      $.ajax({
        method: "DELETE",
        url: "http://localhost:8080/medicos/" + $("#crmEncontrado").val(),
        success: function (response) {
          Swal.fire("Médico excluído com sucesso!", "", "success");
        },
      }).fail(function (xhr, status, errorThrown) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Nenhum médico consultado!",
        });
      });

      document.getElementById("formBusca").reset();
    } else if (result.isDenied) {
      Swal.fire("Changes are not saved", "", "info");
    }
  });
}

function mostrarPacientes() {
  let nomeEncontrado = $("#nomeEncontrado").val();
  let crmEncontrado = $("#crmEncontrado").val();
  let especialidadeEncontrada = $("#especialidadeEncontrada").val();

  $.ajax({
    method: "GET",
    url: "http://localhost:8080/medicos/" + crmEncontrado + "/pacientes",
    contentType: "application/json; charset=utf-8",
    success: function (response) {
      window.location.href = 'http://127.0.0.1:5500/listarPacientes.html?crmEncontrado='+ crmEncontrado;
    },
  }).fail(function (xhr, status, errorThrown) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Nenhum médico consultado!",
    });
  });
}
