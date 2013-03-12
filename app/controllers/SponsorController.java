package controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import models.exception.JCertifException;
import models.objects.Sponsor;
import models.objects.access.SponsorDB;
import models.util.Tools;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;

public class SponsorController extends AbstractController {

    public static Result listSponsor() {
        Logger.info("Enter listSponsor()");
        allowCrossOriginJson();
        Logger.info("Exit listSponsor()");
        return ok(JSON.serialize(SponsorDB.getInstance().list()));
    }

    public static Result updateSponsor() {
        Logger.info("Enter updateSponsor()");

        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit updateSponsor()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit updateSponsor()");
            return badRequest(e.getMessage());
        }

        String sponsorObjInJSONForm = request().body().asJson().toString();

        Sponsor sponsorToUpdate;
        try {
            sponsorToUpdate = new Sponsor(
                    (BasicDBObject) JSON.parse(sponsorObjInJSONForm));
        } catch (JSONParseException exception) {
            Logger.error(exception.getMessage());
            Logger.info("Exit updateSponsor()");
            return badRequest(sponsorObjInJSONForm);
        }

        Sponsor sponsorFromRepo;
        try {
            sponsorFromRepo = SponsorDB.getInstance().get(
                    sponsorToUpdate.getEmail());
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateSponsor()");
            return internalServerError(jcertifException.getMessage());
        }

        if (sponsorFromRepo == null) {
            Logger.info("Sponsor with email " + sponsorToUpdate.getEmail() + " does not exist");
            Logger.info("Exit updateSponsor()");
            return internalServerError(JSON
                    .serialize("sponsor with email \""
                            + sponsorToUpdate.getEmail()
                            + "\" does not exist"));
        }

        try {
            SponsorDB.getInstance().save(sponsorToUpdate);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit updateSponsor()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Successfull update sponsor");
        Logger.info("Exit updateSponsor()");
        return ok(JSON.serialize("Ok"));
    }

    public static Result addSponsor() {
        Logger.info("Enter addSponsor()");

        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit addSponsor()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit addSponsor()");
            return badRequest(e.getMessage());
        }

        String sponsorObjInJSONForm = requestBody.asJson().toString();
        Sponsor sponsor;
        try{
            sponsor = new Sponsor((BasicDBObject) JSON.parse(sponsorObjInJSONForm));
        }catch(JSONParseException exception){
            Logger.error(exception.getMessage());
            Logger.info("Exit addSponsor()");
            return badRequest(sponsorObjInJSONForm);
        }

        try {
            SponsorDB.getInstance().add(sponsor);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit addSponsor()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Sponsor '" + sponsor.getEmail() + "' added");
        Logger.info("Exit addSponsor()");

        return ok(JSON.serialize("Ok"));
    }

    public static Result removeSponsor() {
        Logger.info("Enter removeSponsor()");
        allowCrossOriginJson();

        try {
            checkAdmin();
        } catch (JCertifException jcertifException) {
            Logger.info("access rejected for non-administrator");
            Logger.info("Exit removeSponsor()");
            return forbidden(jcertifException.getMessage());
        }

        Http.RequestBody requestBody = request().body();
        try {
            Tools.verifyJSonRequest(requestBody);
        } catch (JCertifException e) {
            Logger.error(e.getMessage());
            Logger.info("Exit removeSponsor()");
            return badRequest(e.getMessage());
        }

        String emailSponsor;

        try{
            emailSponsor = requestBody.asJson().get("email").getTextValue();
        }catch(NullPointerException exception){
            Logger.error(exception.getMessage());
            Logger.info("Exit removeSponsor()");
            return badRequest(requestBody.asJson().toString());
        }

        Sponsor sponsorFromRepo;
        try {
            sponsorFromRepo = SponsorDB.getInstance().get(
                    emailSponsor);
        } catch (JCertifException jcertifException) {
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSponsor()");
            return internalServerError(jcertifException.getMessage());
        }

        if (sponsorFromRepo == null) {
            Logger.info("Sponsor with email " + emailSponsor + " does not exist");
            Logger.info("Exit removeSponsor()");
            return internalServerError(JSON
                    .serialize("sponsor with email \""
                            + emailSponsor
                            + "\" does not exist"));
        }

        try{
            SponsorDB.getInstance().remove(sponsorFromRepo);
        }catch(JCertifException jcertifException){
            Logger.error(jcertifException.getMessage());
            Logger.info("Exit removeSponsor()");
            return internalServerError(jcertifException.getMessage());
        }

        Logger.info("Sponsor '" + emailSponsor + "' removed");
        Logger.info("Enter removeSponsor()");

        return ok(JSON.serialize("Ok"));
    }
}
