package models.validation;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import models.Model;
import models.util.Constantes;
import models.util.Tools;
import models.validation.Constraints.Category;
import models.validation.Constraints.Country;
import models.validation.Constraints.Date;
import models.validation.Constraints.DatesCoherence;
import models.validation.Constraints.NotBlank;
import models.validation.Constraints.Password;
import models.validation.Constraints.Room;
import models.validation.Constraints.SessionStatus;
import models.validation.Constraints.Site;
import models.validation.Constraints.SponsorLevel;
import models.validation.Constraints.Title;

/**
 * <p>Classe regroupant les classes de validation des contraintes sur les objets du model JCertif.</p>
 * 
 * @author Martial SOMDA
 * @see ConstraintValidator
 * @see Constraints
 */
public class Validators {

	public static class DateValidator implements ConstraintValidator<Date, String>{

		@Override
		public void initialize(Date passwordAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return Tools.isBlankOrNull(value) || Tools.isValidDate(value);
		}
	}
	
	public static class DatesCoherenceValidator implements ConstraintValidator<DatesCoherence, Object>{

		private String startDateProperty;
		private String endDateProperty;
		
		@Override
		public void initialize(DatesCoherence dateCoherenceAnnotation) {
			this.startDateProperty = dateCoherenceAnnotation.startDateProperty();
			this.endDateProperty = dateCoherenceAnnotation.endDateProperty();
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {
			SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT, Locale.FRANCE);
			Class<?> validatedObjectClass = value.getClass();
			boolean isValid = false;
			try {
				Field startDateField = validatedObjectClass.getDeclaredField(startDateProperty);
				Field endDateField = validatedObjectClass.getDeclaredField(endDateProperty);
				startDateField.setAccessible(true);
				Object startDateValue = startDateField.get(value);
				endDateField.setAccessible(true);
				Object endDateValue = endDateField.get(value);
				
				if(startDateValue != null
						&& Tools.isValidDate(startDateValue.toString())
						&& endDateValue != null
						&& Tools.isValidDate(endDateValue.toString())){
					java.util.Date startDate = sdf.parse(startDateValue.toString());
		            java.util.Date endDate = sdf.parse(endDateValue.toString());
		            isValid = startDate.before(endDate);
				}else{
					isValid = true;
				}	            
	            
	        } catch (ParseException e) {
	        	isValid = true;
	        } catch (Exception e) {
	        	isValid = false;
	        }
			return isValid;
		}
	}
	
	public static class NotBlankValidator  implements ConstraintValidator<NotBlank, String>{
		
		@Override
		public void initialize(NotBlank notBlankAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {	
			return !Tools.isBlankOrNull(value);
		}
	}
	
	public static class NotBlankListValidator  implements ConstraintValidator<NotBlank, Collection<String>>{
		
		@Override
		public void initialize(NotBlank notBlankAnnotation) {
		}

		@Override
		public boolean isValid(Collection<String> values, ConstraintValidatorContext context) {	
			
			if(Tools.isBlankOrNull(values)){
				return false;
			}
			boolean isValid = true;
			for(Iterator<String> itr = values.iterator();itr.hasNext();){
				isValid = !Tools.isBlankOrNull(itr.next());
				
				if(!isValid){
					break;
				}
			}
			return isValid;
		}
	}
	
	public static class PasswordValidator implements ConstraintValidator<Password, String>{
		
		@Override
		public void initialize(Password passwordAnnotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return Tools.isBlankOrNull(value) || value.length() >= Constantes.PASSWORD_MIN_LENGTH;
		}
	}

	public static class CategoryValidator implements ConstraintValidator<Category, Collection<String>>{

		private ExistsValidator<models.Category> existsValidator;
		
		@Override
		public void initialize(Category existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.Category>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(Collection<String> values, ConstraintValidatorContext context) {
			boolean isValid = true;
			for(Iterator<String> itr = values.iterator();itr.hasNext();){
				isValid = existsValidator.isValid(itr.next());
				
				if(!isValid){
					break;
				}
			}
			return isValid;
		}
	}
	
	public static class SessionStatusValidator implements ConstraintValidator<SessionStatus, String>{

		private ExistsValidator<models.SessionStatus> existsValidator;
		
		@Override
		public void initialize(SessionStatus existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.SessionStatus>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class TitleValidator implements ConstraintValidator<Title, String>{

		private ExistsValidator<models.Title> existsValidator;
		
		@Override
		public void initialize(Title existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.Title>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class SiteValidator implements ConstraintValidator<Site, String>{

		private ExistsValidator<models.Site> existsValidator;
		
		@Override
		public void initialize(Site existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.Site>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class RoomValidator implements ConstraintValidator<Room, String>{

		private ExistsValidator<models.Room> existsValidator;
		
		@Override
		public void initialize(Room existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.Room>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class SponsorLevelValidator implements ConstraintValidator<SponsorLevel, String>{

		private ExistsValidator<models.SponsorLevel> existsValidator;
		
		@Override
		public void initialize(SponsorLevel existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.SponsorLevel>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class CountryValidator implements ConstraintValidator<Country, String>{

		private ExistsValidator<models.Country> existsValidator;
		
		@Override
		public void initialize(Country existsAnnotation) {
			existsValidator = 
				new ExistsValidator<models.Country>(existsAnnotation.propertyName(),existsAnnotation.modelClass());	
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return existsValidator.isValid(value);
		}
	}
	
	public static class ExistsValidator<T extends Model> {

		private String propertyName;
		private Class<T> modelClass;
		
		public ExistsValidator(String propertyName, Class<T> modelClass) {
			this.propertyName = propertyName;	
			this.modelClass = modelClass;	
		}

		public boolean isValid(String value) {
			boolean isValid = true;
			
			if(!Tools.isBlankOrNull(value) &&
					Model.getFinder().find(modelClass, propertyName, value) == null){
				isValid = false;
			}
			
			return isValid;
		}
	}

}
