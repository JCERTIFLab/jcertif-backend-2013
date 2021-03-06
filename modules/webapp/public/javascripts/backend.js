Backend = {}

// Service d'initialisation
Backend.init = function () {

    $( "#menu" ).menu();

    Backend.registerParticipant.initDialog();
    Backend.addStatus.initDialog();
    Backend.addParticipantToSession.initDialog();
	Backend.removeParticipantFromSession.initDialog();
	Backend.listParticipantSessions.initDialog();
	Backend.reinitialPassword.initDialog();
	Backend.changePassword.initDialog();
	Backend.addSession.initDialog();
	Backend.addSponsorLevel.initDialog();      
	Backend.removeSponsorLevel.initDialog();
	Backend.addCategory.initDialog();      
	Backend.removeCategory.initDialog();
	Backend.addCivilite.initDialog();      
	Backend.removeCivilite.initDialog();
	Backend.logIn.initDialog();
	Backend.infosParticipant.initDialog();

    $("#register-participant").click(function () {
        Backend.registerParticipant.openDialog();
    });	
    $("#add-status").click(function () {
        Backend.addStatus.openDialog();
    });    
    $("#add-participant-session").click(function () {
        Backend.addParticipantToSession.openDialog();
    });
    $("#list-participant-sessions").click(function () {
        Backend.listParticipantSessions.openDialog();
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
	$("#session-add").click(function () {
        Backend.addSession.openDialog();
    });
	$("#add-sponsor-level").click(function () {
        Backend.addSponsorLevel.openDialog();
    });
	$("#remove-sponsor-level").click(function () {
        Backend.removeSponsorLevel.openDialog();
    });
	$("#add-category").click(function () {
        Backend.addCategory.openDialog();
    });
	$("#remove-category").click(function () {
        Backend.removeCategory.openDialog();
    });
	$("#add-civilite").click(function () {
        Backend.addCivilite.openDialog();
    });
	$("#remove-civilite").click(function () {
        Backend.removeCivilite.openDialog();
    });
	$("#login").click(function () {
        Backend.logIn.openDialog();
    });
	$("#participant-infos").click(function () {
        Backend.infosParticipant.openDialog();
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
                    		$('#dialog-register-participant').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
                }
            }

        });
    }

}

// Service session
Backend.addSession = {

    initDialog: function () {
	
	
        var sessionInfos = [ 'id', 'title', 'summary', 'description', 'keyword', 'start', 'end'];

        for (var i = 0; i < sessionInfos.length; i++) {
            $("#dialog-add-session form fieldset").append('<label for="' + sessionInfos[i] + '">' + sessionInfos[i] + '</label>');
            $("#dialog-add-session form fieldset").append('<input type="text" name="' + sessionInfos[i] + '" id="' + sessionInfos[i] + '" class="text ui-widget-content ui-corner-all"/>');
        }
		$.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/ref/sessionstatus/list"
                    }).done(function (msg) {
                            status =msg;
							option="";
							$.each(status, function(arrayID,group) {
					option=option+'<option value="'+group.label+'">'+group.label+'</option>';
			});
            $("#dialog-add-session form fieldset").append('<label for="status">status</label>');
            $("#dialog-add-session form fieldset").append('<select name="status" id="status" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
						
						
							$.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/ref/category/list"
                    }).done(function (msg) {
                            categories =msg;
							option="";
							$.each(categories, function(arrayID,group) {
					option=option+'<option value="'+group.label+'">'+group.label+'</option>';
			});
            $("#dialog-add-session form fieldset").append('<label for="category">category</label>');
            $("#dialog-add-session form fieldset").append('<select name="category" id="category" multiple="true" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
						
						$.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "/speaker/list"
                    }).done(function (msg) {
                            speakers =msg;
							option="";
							$.each(speakers, function(arrayID,group) {
					option=option+'<option value="'+group.email+'">'+group.lastname+' '+group.firstname+'</option>';
			});
            $("#dialog-add-session form fieldset").append('<label for="speakers">speakers</label>');
            $("#dialog-add-session form fieldset").append('<select name="speakers" id="sprakers" multiple="true" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });

    },

    openDialog: function () {
        $("#dialog-add-session").dialog({
            width: 800,
            height: 700,
            modal: true,
            buttons: {
                "Ajouter": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/session/new",
                        data: $('#dialog-add-session form').serializeJSONString()
                    }).done(function (msg) {
                    		$('#dialog-add-session').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
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
                "Save": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/sessionstatus/new",
                        data: $('#dialog-add-status form').serializeJSONString()
                    }).done(function (msg) {
                    		$('#dialog-add-status').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
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
                    		$('#dialog-participant-changePassword').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
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
                        type: "GET",
                        url: "/participant/"+$("#emailParticipantInit").val()+"/lostpassword"
                    }).done(function (msg) {
                    		$('#dialog-participant-reinitial').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
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
                            alert(msg.responseText);
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
                            alert(msg.responseText);
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
                    		$('#dialog-add-participant-session').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
                }
            }

        });
    }

}

// Service list participant sessions
Backend.listParticipantSessions = {

    initDialog: function () {
			participants=[];
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
            $("#dialog-list-participant-sessions form fieldset").append('<label for="participant">emailParticipant</label>');
            $("#dialog-list-participant-sessions form fieldset").append('<select name="emailParticipant" id="emailParticipantList" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
            }).fail(function (msg) {
                alert(msg.responseText);
            });
    },

    openDialog: function () {
        $("#dialog-list-participant-sessions").dialog({
            width: 300,
            height: 300,
            modal: true,
            buttons: {
                "Voir Sessions": function () {
                	window.location = "/participant/"+$("#emailParticipantList option").filter(":selected").val()+"/session/list";
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
                            alert(msg.responseText);
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
                            alert(msg.responseText);
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
                    		$('#dialog-remove-participant-session').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
                }
            }

        });
    }
}

//Service de création des niveaux de sponsoring
Backend.addSponsorLevel = {
		
	initDialog: function () {
		$("#dialog-add-sponsor-level form fieldset").append('<label for="label">Niveau de partenariat</label>');
        $("#dialog-add-sponsor-level form fieldset").append('<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all"/>');
    },

    openDialog: function () {
    	$("#dialog-add-sponsor-level").dialog({
            width: 450,
            height: 250,
            modal: true,
            buttons: {
                "Add": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/sponsorlevel/new",
                        data: $('#dialog-add-sponsor-level form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-add-sponsor-level").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de suppression des niveaux de sponsoring
Backend.removeSponsorLevel = {
		
	initDialog: function () {
		$.getJSON("/ref/sponsorlevel/list", function(data){
			options = "";
			$.each(data, function(index, sponsorLevel) {
				options=options+'<option value="'+sponsorLevel.label+'">'+sponsorLevel.label+'</option>';
			});
			$("#dialog-remove-sponsor-level form fieldset").append('<label for="label">Niveau de partenariat</label>');
	        $("#dialog-remove-sponsor-level form fieldset").append('<select name="label" id="label" class="ui-widget-content ui-corner-all">'+options+'</select>');
		});
    },

    openDialog: function () {
    	$("#dialog-remove-sponsor-level").dialog({
            width: 420,
            height: 200,
            modal: true,
            buttons: {
                "Supprimer": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/sponsorlevel/remove",
                        data: $('#dialog-remove-sponsor-level form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-remove-sponsor-level").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de création de nouvelles catégories
Backend.addCategory = {
		
	initDialog: function () {
		$("#dialog-add-category form fieldset").append('<label for="label">Cat&eacute;gorie</label>');
        $("#dialog-add-category form fieldset").append('<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all"/>');
    },

    openDialog: function () {
    	$("#dialog-add-category").dialog({
            width: 350,
            height: 250,
            modal: true,
            buttons: {
                "Add": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/category/new",
                        data: $('#dialog-add-category form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-add-category").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de suppression de catégories
Backend.removeCategory = {
		
	initDialog: function () {
		$.getJSON("/ref/category/list", function(data){
			options = "";
			$.each(data, function(index, categorie) {
				options=options+'<option value="'+categorie.label+'">'+categorie.label+'</option>';
			});
			$("#dialog-remove-category form fieldset").append('<label for="label">Cat&eacute;gorie</label>');
	        $("#dialog-remove-category form fieldset").append('<select name="label" id="label" class="ui-widget-content ui-corner-all">'+options+'</select>');
		});
    },

    openDialog: function () {
    	$("#dialog-remove-category").dialog({
            width: 350,
            height: 200,
            modal: true,
            buttons: {
                "Supprimer": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/category/remove",
                        data: $('#dialog-remove-category form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-remove-category").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de création de nouvelles civilités
Backend.addCivilite = {
		
	initDialog: function () {
		$("#dialog-add-civilite form fieldset").append('<label for="label">Civilit&eacute;</label>');
        $("#dialog-add-civilite form fieldset").append('<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all"/>');
    },

    openDialog: function () {
    	$("#dialog-add-civilite").dialog({
            width: 350,
            height: 250,
            modal: true,
            buttons: {
                "Add": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/title/new",
                        data: $('#dialog-add-civilite form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-add-civilite").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de suppression de civilités
Backend.removeCivilite = {
		
	initDialog: function () {
		$.getJSON("/ref/title/list", function(data){
			options = "";
			$.each(data, function(index, civilite) {
				options=options+'<option value="'+civilite.label+'">'+civilite.label+'</option>';
			});
			$("#dialog-remove-civilite form fieldset").append('<label for="label">Civilit&eacute;</label>');
	        $("#dialog-remove-civilite form fieldset").append('<select name="label" id="label" class="ui-widget-content ui-corner-all">'+options+'</select>');
		});
    },

    openDialog: function () {
    	$("#dialog-remove-civilite").dialog({
            width: 350,
            height: 200,
            modal: true,
            buttons: {
                "Supprimer": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/home/ref/title/remove",
                        data: $('#dialog-remove-civilite form').serializeJSONString()
                    }).done(function (msg) {
                    	$("#dialog-remove-civilite").dialog("destroy");
                    }).fail(function (msg) {
                        alert(msg.responseText);
                    });
                }
            }
        });
    }
}

//Service de login
Backend.logIn = {
		
    initDialog: function () {
    	$("#dialog-login form fieldset").append('<label for="email">Email</label>');
        $("#dialog-login form fieldset").append('<input type="text" name="email" id="email" class="text ui-widget-content ui-corner-all"/>');
        $("#dialog-login form fieldset").append('<label for="password">Mot de passe</label>');
        $("#dialog-login form fieldset").append('<input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all"/>');
    },

    openDialog: function () {
        $("#dialog-login").dialog({
            width: 300,
            height: 350,
            modal: true,
            buttons: {
                "Login": function () {
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: "/participant/login",
                        data: $('#dialog-login form').serializeJSONString()
                    }).done(function (msg) {
                    		$('#dialog-login').dialog("destroy");
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });
                }
            }

        });
    }
}

//Service retrieve participant information
Backend.infosParticipant = {


    initDialog: function () {
    	participants=[];
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
            $("#dialog-participant-infos form fieldset").append('<label for="participant">emailParticipant</label>');
            $("#dialog-participant-infos form fieldset").append('<select name="emailParticipant" id="emailParticipantInfos" class="ui-widget-content ui-corner-all">'+option+'</select>');
				
                        }).fail(function (msg) {
                            alert(msg.responseText);
                        });     
    },

    openDialog: function () {
        $("#dialog-participant-infos").dialog({
            width: 300,
            height: 250,
            modal: true,
            buttons: {
                "Infos": function () {
                	window.location = "/home/participant/get/"+$("#emailParticipantInfos option").filter(":selected").val();
                }
            }

        });
    }

}