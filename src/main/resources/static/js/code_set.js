$(document).on("click", ".open-modal", function () {
    var code = $(this).data('id');
    $(".modal-body #code").text( code );

    var action = "/admin/code/" + code + "/delete";
    $("#deleteForm").attr("action", action);
});
