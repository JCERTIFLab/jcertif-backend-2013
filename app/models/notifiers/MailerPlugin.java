package models.notifiers;
import play.api.Application;
import scala.runtime.AbstractFunction0;

import com.typesafe.plugin.CommonsMailerPlugin;
import com.typesafe.plugin.MailerAPI;

/**
 * <p>An extension to {@link CommonsMailerPlugin} that can be mocked at runtime.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class MailerPlugin extends CommonsMailerPlugin {

	private boolean mock;
	MailerAPI mockMailer;
	
	public MailerPlugin(Application application) {
		super(application);
		mock = application.configuration().getBoolean("smtp.mock").getOrElse(new AbstractFunction0<Boolean>() {
			@Override
			public Boolean apply() {
				return Boolean.FALSE;
			}
		});
		mockMailer = com.typesafe.plugin.MockMailer$.MODULE$;
	}
	
	public synchronized void mock(){
		mock = true;
	}
	
	public synchronized void release(){
		mock = false;
	}
	
	@Override
	public MailerAPI email() {
		if(mock){
			return mockMailer;
		}
		return super.email();
	}
}
