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
  

  $("#foto").on("change", function () {
    let input = this;
    if (input.files && input.files[0]) {
      let reader = new FileReader();
      reader.onload = function (e) {
        $(".card-img-top").attr("src", e.target.result);
      };
      reader.readAsDataURL(input.files[0]);
    }
  });
  