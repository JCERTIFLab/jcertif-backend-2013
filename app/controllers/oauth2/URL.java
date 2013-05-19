package controllers.oauth2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import models.util.Tools;

/**
 * <p>Classe Helper permettant de construire de URLs</p>
 * 
 * @author Martial SOMDA
 *
 */
public class URL {

	private String base;
	private Map<String, String> queryStringParameters;
	
	public URL(){
		this("");
	}
	
	public URL(String base){
		this(base, new HashMap<String, String>());
	}
	
	public URL(String base, Map<String, String> queryStringParameters){
		this.base = base;
		this.queryStringParameters = queryStringParameters;
	}
	
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Map<String, String> getQueryStringParameters() {
		return queryStringParameters;
	}

	public void setQueryStringParameters(Map<String, String> queryStringParameters) {
		this.queryStringParameters = queryStringParameters;
	}

	public String get(){
		
		if(Tools.isBlankOrNull(base)){
			throw new IllegalArgumentException("The URL must have a base scheme and host");
		}
		
		StringBuilder builder = new StringBuilder(base);
		boolean first = true;
		
		for(Entry<String, String> entry : queryStringParameters.entrySet()){
			if(first){
				builder.append("?");
				first = false;
			}else{
				builder.append("&");
			}
			
			builder.append(entry.getKey()).append("=").append(entry.getValue());
		}
		
		return builder.toString();
	}
	
	public static class Builder {
		
		private URL url;
		
		public Builder(){
			url = new URL();
		}
		
		public Builder withBase(String base){
			url.setBase(base);
			return this;
		}
		
		public Builder withQueryParam(String param, String value){
			url.getQueryStringParameters().put(param, value);
			return this;
		}
		
		public String build(){
			return url.get();
		}
		
	}
}
