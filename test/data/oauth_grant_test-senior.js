db.client.remove();
db.client.insert({
	client_id: 'backoffice',
	client_secret: 'r876CINW3pZuu7nL7h6AP',
	scope: [ 'user', 'admin' ],
	authorized_grant_types: [ 'refresh_token', 'authorization_code' ],
	access_token_validity: '3600',
	refresh_token_validity: '7200',
	version: '01', 
	deleted: 'false'
});
db.oauth_access_token.remove();
db.oauth_access_token.insert({
	access_token: 'e096fdd2-448b-4df4-9fca-11f80d8a5f86',
	token_type: 'bearer',
	scope: [ 'user' ],
	expires_in: '3600',
	refresh_token: '18f5302c-0409-4ff6-b881-e51fc67ed4ed',
	client_id: 'backoffice',
	user: 'test-senior@participant.com',
	version: '01', 
	deleted: 'false'
});
db.oauth_refresh_token.remove();
db.oauth_refresh_token.insert({
	refresh_token: '18f5302c-0409-4ff6-b881-e51fc67ed4ed',
	scope: [ 'user' ],
	client_id: 'backoffice',
	user: 'test-senior@participant.com',
	version: '01', 
	deleted: 'false'
});