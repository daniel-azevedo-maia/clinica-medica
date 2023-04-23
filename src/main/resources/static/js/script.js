$.ajax({
  method: "GET",
  url: "http://localhost:8080/medicos/",
  contentType: "application/json; charset=utf-8",
  success: function (response) {    
        
        for(let i = 0; i < response.length; i++) {

          $("#medicoOpcao").append('<option value="' + response[i].nome + '">' + response[i].nome + '</option>');
        }
  }
}).fail(function (xhr, status, errorThrown) {
  alert("Erro ao buscar médico: " + xhr.responseText);
});


$("#nome").keyup(function () {

  let nome = $(this).val();
  $("#tituloCard").text(nome);
  
});

$("#crm").keyup(function () {

    let crm = $(this).val();
    $("#crmCard").text("CRM nº " + crm);
    
  });

  $("#especialidade").on("change", function () {
    let especialidade = $(this).val();
    $("#especialidadeCard").text("Especialidade: " + especialidade);
  });
  
  
  function cadastrarMedico() {

    event.preventDefault();
    let nome = $("#nome").val();
    let crm = $("#crm").val();
    let especialidade = $("#especialidade").val();

    if(nome == null || nome != null && nome.trim() == '') {
      $("#nome").focus();
			alert('Informe o nome');
			return;
    }

    if (crm == null || crm != null && crm.trim() == ''){
			$("#crm").focus();
			alert('Informe o CRM');
			return;
		}

    $.ajax({
			method : "POST",
			url : "http://localhost:8080/medicos",
			data : JSON.stringify({
				nome : nome,
				crm : crm,
        especialidade : especialidade
			}),
			contentType : "application/json; charset=utf-8",
			success : function(response) {
        
				Swal.fire(
          'Médico cadastrado com sucesso!',
          '',
          'success'
        )

        document.getElementById("formMedico").reset();

			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao salvar médico: " + xhr.responseText);
		});

  }

  function buscarMedico() {

    $.ajax({
      method : "GET",
      url: "http://localhost:8080/medicos/" + $("#crm").val(),
      contentType : "application/json; charset=utf-8",
      success : function(response) {
        $("#nomeEncontrado").val(response.nome);
        $("#crmEncontrado").val(response.crm);
        $("#especialidadeEncontrada").val(response.especialidade);
  
      }
    }).fail(function(xhr, status, errorThrown) {
      alert("Erro ao buscar médico: " + xhr.responseText);
    });
  }

