package models.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import models.util.Constantes;
import models.validation.Validators.CategoryValidator;
import models.validation.Validators.DateValidator;
import models.validation.Validators.DatesCoherenceValidator;
import models.validation.Validators.NotBlankListValidator;
import models.validation.Validators.NotBlankValidator;
import models.validation.Validators.PasswordValidator;
import models.validation.Validators.RoomValidator;
import models.validation.Validators.SessionStatusValidator;
import models.validation.Validators.SiteValidator;
import models.validation.Validators.SponsorLevelValidator;
import models.validation.Validators.TitleValidator;

/**
 * <p>Classe regroupant les contraintes spécifiques JCertif appliquées sur les objets du model.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Constraints {

	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {NotBlankValidator.class, NotBlankListValidator.class})
	public static @interface NotBlank {
		String message() default "{propertyName} cannot be empty or null";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};	
		String propertyName() default "";
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = DateValidator.class)
	public static @interface Date {
		String message() default "{propertyName} is not valid";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default "";
	}
	
	@Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = DatesCoherenceValidator.class)
	public static @interface DatesCoherence {
		String message() default "Start Date must not be equals or greater than End Date";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};	
		String startDateProperty() default "start";
		String endDateProperty() default "end";
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = PasswordValidator.class)
	public static @interface Password {
		String message() default "Password does not match policy (minimum length : "+ Constantes.PASSWORD_MIN_LENGTH + " )";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = TitleValidator.class)
	public static @interface Title {
		String message() default "Title {value} does not exist. Check Title List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.LABEL_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.Title.class;
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = CategoryValidator.class)
	public static @interface Category {
		String message() default "Category {value} does not exist. Check Category List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.LABEL_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.Category.class;
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = SessionStatusValidator.class)
	public static @interface SessionStatus {
		String message() default "SessionStatus {value} does not exist. Check Session Status List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.LABEL_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.SessionStatus.class;
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = SiteValidator.class)
	public static @interface Site {
		String message() default "Site {value} does not exist. Check Site List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.ID_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.Site.class;
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = RoomValidator.class)
	public static @interface Room {
		String message() default "Room {value} does not exist. Check Room List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.ID_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.Room.class;
	}
	
	@Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = SponsorLevelValidator.class)
	public static @interface SponsorLevel {
		String message() default "SponsorLevel {value} does not exist. Check Sponsor Level List";		
		Class<?>[] groups() default {};		
		Class<? extends Payload>[] payload() default {};
		String propertyName() default Constantes.LABEL_ATTRIBUTE_NAME;
		Class<?> modelClass() default models.SponsorLevel.class;
	}
	
}
