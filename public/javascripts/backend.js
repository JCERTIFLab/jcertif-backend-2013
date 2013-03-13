Backend = {}

// Service d'initialisation
Backend.init = function () {



    $( "#menu" ).menu();

    Backend.registerParticipant.initDialog();
    Backend.addStatus.initDialog();
    Backend.addParticipantToSession.initDialog();
	Backend.removeParticipantFromSession.initDialog();
	Backend.reinitialPassword.initDialog();
	Backend.changePassword.initDialog();
    
        
    

    $("#register-participant").click(function () {
        Backend.registerParticipant.openDialog();
    });
	
  $("#add-status").click(function () {
        Backend.addStatus.openDialog();
    });
    
     $("#add-participant-session").click(function () {
        Backend.addParticipantToSession.openDialog();
    });
     $("#remove-participant-session").click(function () {
        Backend.removeParticipantFromSession.openDialog();
    });
    $("#participant-reinitialPassword").click(function () {
        Backend.reinitialPassword.openDialog();
    });
    $("#participant-changePassword").click(function () {
        Backend.changePassword.openDialog();
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

// Service changePassword
Backend.changePassword = {

    initDialog: function () {
            $("#dialog-participant-changePassword form fieldset").append('<label for="emailParticipant">emailParticipant</label>');
            $("#dialog-participant-changePassword form fieldset").append('<input type="text" name="emailParticipant" id="emailParticipantChang" class="text ui-widget-content ui-corner-all"/>');
 			$("#dialog-participant-changePassword form fieldset").append('<label for="oldPassword">oldPassword</label>');
            $("#dialog-participant-changePassword form fieldset").append('<input type="password" name="oldpassword" id="oldpassword" class="text ui-widget-content ui-corner-all"/>');
			$("#dialog-participant-changePassword form fieldset").append('<label for="newPasswordt">newPassword</label>');
            $("#dialog-participant-changePassword form fieldset").append('<input type="password" name="newpassword" id="newpassword" class="text ui-widget-content ui-corner-all"/>');
			
    },

    openDialog: function () {
        $("#dialog-participant-changePassword").dialog({
            width: 300,
            height: 400,
            modal: true,
            buttons: {
                "Change": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/participant/"+$("#emailParticipantChang").val()+"/changepassword",
                        data: $('#dialog-participant-changePassword form').serializeJSONString()
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

// Service reinitialPassword
Backend.reinitialPassword = {

    initDialog: function () {
            $("#dialog-participant-reinitial form fieldset").append('<label for="emailParticipant">emailParticipant</label>');
            $("#dialog-participant-reinitial form fieldset").append('<input type="text" name="emailParticipantInit" id="emailParticipantInit" class="text ui-widget-content ui-corner-all"/>');

    },

    openDialog: function () {
        $("#dialog-participant-reinitial").dialog({
            width: 300,
            height: 250,
            modal: true,
            buttons: {
                "réinitialiser": function () {
                    $.ajax({
                        type: "POST",
                        url: "/participant/"+$("#emailParticipantInit").val()+"/lostpassword"
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


// Service add participant to session
Backend.addParticipantToSession = {


    initDialog: function () {
participants=[];
	sessions=[];
 						$.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/participant/list"
                    }).done(function (msg) {
                            participants =msg;
							option="";
							$.each(participants, function(arrayID,group) {
					option=option+'<option value="'+group.email+'">'+group.lastname+' '+group.firstname+'</option>';
			});
            $("#dialog-add-participant-session form fieldset").append('<label for="participant">emailParticipant</label>');
            $("#dialog-add-participant-session form fieldset").append('<select name="emailParticipant" id="emailParticipantAdd" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });
                        
                        $.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/session/list"
                    }).done(function (msg) {
                            sessions = msg;
							option="";
							$.each(sessions, function(arrayID,group) {
					option=option+'<option value="'+group.id+'">'+group.title+'</option>';
			});
							 $("#dialog-add-participant-session form fieldset").append('<label for="session">idSession</label>');
            $("#dialog-add-participant-session form fieldset").append('<select name="idSession" id="idSessionAdd" class="ui-widget-content ui-corner-all">'+option+'</select>');
                 
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });

           
    },

    openDialog: function () {
        $("#dialog-add-participant-session").dialog({
            width: 300,
            height: 300,
            modal: true,
            buttons: {
                "Inscrire": function () {
                    $.ajax({
                        type: "POST",
                        url: "/participant/"+$("#emailParticipantAdd option").filter(":selected").val()+"/session/add/"+$("#idSessionAdd option").filter(":selected").val()
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

// Service remove participant from session
Backend.removeParticipantFromSession = {


    initDialog: function () {
	participants=[];
	sessions=[];
 						$.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/participant/list"
                    }).done(function (msg) {
                            participants =msg;
							option="";
							$.each(participants, function(arrayID,group) {
					option=option+'<option value="'+group.email+'">'+group.lastname+' '+group.firstname+'</option>';
			});
            $("#dialog-remove-participant-session form fieldset").append('<label for="participant">emailParticipant</label>');
            $("#dialog-remove-participant-session form fieldset").append('<select name="emailParticipant" id="emailParticipantRemove" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });
                        
                        $.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/session/list"
                    }).done(function (msg) {
                            sessions = msg;
							option="";
							$.each(sessions, function(arrayID,group) {
					option=option+'<option value="'+group.id+'">'+group.title+'</option>';
			});
							 $("#dialog-remove-participant-session form fieldset").append('<label for="session">idSession</label>');
            $("#dialog-remove-participant-session form fieldset").append('<select name="idSession" id="idSession" class="ui-widget-content ui-corner-all">'+option+'</select>');
                 
                        }).fail(function (msg) {
                            alert("Opps : " + msg.responseText);
                        });

           
    },

    openDialog: function () {
        $("#dialog-remove-participant-session").dialog({
            width: 300,
            height: 300,
            modal: true,
            buttons: {
                "Désinscrire": function () {
                    $.ajax({
                        type: "POST",
                        url: "/participant/"+$("#emailParticipantRemove option").filter(":selected").val()+"/session/remove/"+$("#idSession option").filter(":selected").val()
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