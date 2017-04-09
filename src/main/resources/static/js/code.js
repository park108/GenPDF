$(document).on("click", ".open-modal", function () {
    var code = $(this).data('id');
    $(".modal-body #code").text( code );

    var codeSet = $("#codeSet").attr("value");
    var action = "/admin/code/" + codeSet + "/" + code + "/delete";
    $("#deleteForm").attr("action", action);
});