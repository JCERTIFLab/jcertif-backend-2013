Backend = {}

// Service d'initialisation
Backend.init = function () {



    $( "#menu" ).menu();

    Backend.registerParticipant.initDialog();
        Backend.addStatus.initDialog();
    

    $("#register-participant").click(function () {
        Backend.registerParticipant.openDialog();
    });
	
  $("#add-status").click(function () {
        Backend.addStatus.openDialog();
    });
}



// Fonction permettant de transformer un formulaire en chaine JSON
$.fn.serializeJSONString = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return JSON.stringify(o);
};

// Service participant
Backend.registerParticipant = {

    initDialog: function () {
        var participantInfos = [ 'email', 'password', 'title', 'lastname', 'firstname', 'website', 'city', 'country', 'company', 'phone', 'photo', 'biography'];

        for (var i = 0; i < participantInfos.length; i++) {
            $("#dialog-register-participant form fieldset").append('<label for="' + participantInfos[i] + '">' + participantInfos[i] + '</label>');
            $("#dialog-register-participant form fieldset").append('<input type="text" name="' + participantInfos[i] + '" id="' + participantInfos[i] + '" class="text ui-widget-content ui-corner-all"/>');
        }

    },

    openDialog: function () {
        $("#dialog-register-participant").dialog({
            width: 800,
            height: 700,
            modal: true,
            buttons: {
                "Inscrire": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/participant/register",
                        data: $('#dialog-register-participant form').serializeJSONString()
                    }).done(function (msg) {
                            alert("Cool");
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });
                }
            }

        });
    }

}

// Service status
Backend.addStatus = {

    initDialog: function () {
            $("#dialog-add-status form fieldset").append('<label for="label">Label</label>');
            $("#dialog-add-status form fieldset").append('<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all"/>');

    },

    openDialog: function () {
        $("#dialog-add-status").dialog({
            width: 300,
            height: 250,
            modal: true,
            buttons: {
                "Add": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/ref/sessionstatus/new",
                        data: $('#dialog-add-status form').serializeJSONString()
                    }).done(function (msg) {
                            alert("Cool");
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });
                }
            }

        });
    }

}