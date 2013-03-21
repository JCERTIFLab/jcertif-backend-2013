package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.POST;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.running;
import static play.test.Helpers.status;


import org.junit.Test;

import play.Logger;
import play.mvc.Result;

public abstract class ReferentielControllerTest {

	public abstract String getCreateURL();
	public abstract String getRemoveURL();
	
	@Test
    public void test_referentiel_new_forbidden() {
        Logger.info("Une requête de création d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, getCreateURL()));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }
	
	@Test
    public void test_referentiel_remove_forbidden() {
        Logger.info("Une requête de suppression d'un objet du referentiel requiert les droits d'administration");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, getRemoveURL()));
                assertThat(status(result)).isEqualTo(FORBIDDEN);
            }
        });
    }
	
    @Test
    public void test_referentiel_new_badrequest() {
        Logger.info("Une requête de création d'un objet du referentiel sans paramètre JSON renvoie une réponse BAD REQUEST");
        running(fakeApplication(), new Runnable() {
            public void run() {
                Result result = route(fakeRequest(POST, getCreateURL()).withSession("admin", "admin"));
                assertThat(status(result)).isEqualTo(BAD_REQUEST);
            }
        });
    }
}