package models.validation;

import java.util.Locale;

import javax.validation.MessageInterpolator;
import javax.validation.MessageInterpolator.Context;

/**
 * @author Martial SOMDA
 *
 */
public class ContextualMessageInterpolator implements MessageInterpolator {

	MessageInterpolator defaultMessageInterpolator;
	
	public ContextualMessageInterpolator(MessageInterpolator defaultMessageInterpolator){
		this.defaultMessageInterpolator = defaultMessageInterpolator;
	}
	
	@Override
	public String interpolate(String messageTemplate, Context context) {
		String interpolatedString = defaultMessageInterpolator.interpolate(messageTemplate, context);
		interpolatedString = interpolatedString.replace("{value}", String.valueOf(context.getValidatedValue()));
		return interpolatedString;
	}

	@Override
	public String interpolate(String messageTemplate, Context context,
			Locale locale) {
		return interpolate(messageTemplate, context);
	}

}
