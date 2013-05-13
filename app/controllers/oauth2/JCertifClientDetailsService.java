package controllers.oauth2;

import java.util.ArrayList;
import java.util.List;

import models.exception.JCertifDuplicateObjectException;
import models.oauth2.Client;

import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

/**
 * <p>Implémentation de {@link ClientDetailsService} utilisant MongoDB comme backing store.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class JCertifClientDetailsService implements ClientDetailsService, ClientRegistrationService {

	@Override
	public void addClientDetails(ClientDetails clientDetails)
			throws ClientAlreadyExistsException {
		Client client = new Client(clientDetails);
		try{
			client.create();
		}catch (JCertifDuplicateObjectException e){
			throw new ClientAlreadyExistsException(e.getMessage(), e);
		}
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails)
			throws NoSuchClientException {
		Client client = Client.find(clientDetails.getClientId());
		
		if(client == null){
			throw new NoSuchClientException("client " + clientDetails.getClientId() + " doesn't exists");
		}
		
		client.merge((Client)clientDetails);
		client.save();
	}

	@Override
	public void updateClientSecret(String clientId, String secret)
			throws NoSuchClientException {
		Client client = Client.find(clientId);
		
		if(client == null){
			throw new NoSuchClientException("client " + clientId + " doesn't exists");
		}
		
		client.setClientSecret(secret);
		client.save();
	}

	@Override
	public void removeClientDetails(String clientId)
			throws NoSuchClientException {
		Client client = Client.find(clientId);
		
		if(client == null){
			throw new NoSuchClientException("client " + clientId + " doesn't exists");
		}
		
		client.remove();
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		List<ClientDetails> clients = new ArrayList<ClientDetails>();
		clients.addAll(Client.findAll());
		return clients;
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId)
			throws ClientRegistrationException {
		return Client.find(clientId);
	}

}
