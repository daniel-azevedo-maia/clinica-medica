function cadastrarPaciente() {

  event.preventDefault();
  let nome = $("#nomePaciente").val();
  let crm = $("#medicoOpcao option:selected").attr("id");


  if(nome == null || nome != null && nome.trim() == '') {
    $("#nome").focus();
    alert('Informe o nome');
    return;
  }

  if (crm == null || crm != null && crm.trim() == ''){
    $("#crm").focus();
    alert('Escolha algum médico');
    return;
  }

  // AJAX para cadastrar o paciente primeiro
  $.ajax({
    method : "POST",
    url : "http://localhost:8080/pacientes/",
    data : JSON.stringify({
      nome : nome,
    }),
    contentType : "application/json; charset=utf-8",
    success : function(response) {
    
      let pacienteId = response.id;

      $.ajax({
        method : "POST",
        url : "http://localhost:8080/pacientes/" + pacienteId + "/adicionarmedico/" + crm,
        data : JSON.stringify({}),
        contentType : "application/json; charset=utf-8",
        success : function(response) {
          
          Swal.fire(
            'Paciente cadastrado com sucesso!',
            '',
            'success'
          );
    
          document.getElementById("formPaciente").reset();
    
        }
      }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao salvar paciente: " + xhr.responseText);
      });

    }
  }).fail(function(xhr, status, errorThrown) {

  });

 

}

$.ajax({
  method: "GET",
  url: "http://localhost:8080/medicos/",
  contentType: "application/json; charset=utf-8",
  success: function(response) {
    for(let i = 0; i < response.length; i++) {
      $("#medicoOpcao").append('<option value="' + response[i].nome + '" id = ' + response[i].crm + '>' + response[i].nome + '</option>');
    }
  }
}).fail(function(xhr, status, errorThrown) {
  alert("Erro ao buscar médico: " + xhr.responseText);
});

$("#nome").keyup(function() {
  let nome = $(this).val();
  $("#tituloCard").text(nome);
});

$("#crm").keyup(function() {
  let crm = $(this).val();
  $("#crmCard").text("CRM nº " + crm);
});

$("#especialidade").on("change", function() {
  let especialidade = $(this).val();
  $("#especialidadeCard").text("Especialidade: " + especialidade);
});