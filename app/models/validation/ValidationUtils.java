package models.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import models.Model;
import models.exception.JCertifInvalidRequestException;

/**
 * @author Martial SOMDA
 *
 */
public class ValidationUtils {

	public static void throwException(Set<ConstraintViolation<Model>> violations){
		StringBuilder builder = new StringBuilder();
		for(ConstraintViolation<Model> violation : violations){
			builder.append(violation.getMessage()).append("\n");
		}
		throw new JCertifInvalidRequestException(builder.toString());
	}
}
