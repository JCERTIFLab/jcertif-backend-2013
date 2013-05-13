db.title.remove();
db.title.insert({label: 'M.', version: '01', deleted: 'false'});
db.title.insert({label: 'Mme', version: '01', deleted: 'false'});
db.title.insert({label: 'Mlle', version: '01', deleted: 'false'});
db.participant.remove();
db.participant.insert({
	email: 'test@member.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Lastname',
	firstname: 'Firstname',
	website: 'www.test.com',
	city: 'Paris',
	country: 'France',
	company: 'Test SA',
	phone: '0102030405',
	biography: 'All about test',
	sessions: ['01', '02'], 
	version: '01', 
	deleted: 'false'
});
db.participant.insert({
	email: 'test2@member.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Lastname',
	firstname: 'Firstname',
	website: 'www.test.com',
	city: 'Paris',
	country: 'France',
	company: 'Test SA',
	phone: '0102030405',
	biography: 'All about test',
	sessions: ['01', '02'], 
	version: '02', 
	deleted: 'true'
});
db.participant.insert({
	email: 'test-senior@member.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Lastname',
	firstname: 'Firstname',
	website: 'www.testSenior.com',
	city: 'Paris',
	country: 'France',
	company: 'Test SA',
	phone: '0102030405',
	biography: 'All about test-senior',
	sessions: ['01', '03', '05'], 
	version: '01', 
	deleted: 'false'
});
db.participant.insert({
	email: 'jandiew@gmail.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Johnson',
	firstname: 'Andriew',
	website: 'www.jandiew.com',
	city: 'Hostin',
	country: 'Texas',
	company: 'JCertif',
	phone: '0102030405',
	biography: 'All about Andriew',
	sessions: ['02'], 
	version: '01', 
	deleted: 'false'
});
db.speaker.remove();
db.speaker.insert({
	email: 'test@member.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Lastname',
	firstname: 'Firstname',
	website: 'www.test.com',
	city: 'Paris',
	country: 'France',
	company: 'Test SA',
	phone: '0102030405',
	biography: 'All about test', 
	version: '01', 
	deleted: 'false'
});
db.speaker.insert({
	email: 'test-senior@member.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Lastname',
	firstname: 'Firstname',
	website: 'www.testSenior.com',
	city: 'Paris',
	country: 'France',
	company: 'Test SA',
	phone: '0102030405',
	biography: 'All about test-senior', 
	version: '01', 
	deleted: 'false'
});
db.speaker.insert({
	email: 'jandiew@gmail.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Johnson',
	firstname: 'Andriew',
	website: 'www.jandiew.com',
	city: 'Hostin',
	country: 'Texas',
	company: 'JCertif',
	phone: '0102030405',
	biography: 'All about Andriew', 
	version: '01', 
	deleted: 'false'
});
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
	user: 'jandiew@gmail.com',
	version: '01', 
	deleted: 'false'
});
db.oauth_refresh_token.remove();
db.oauth_refresh_token.insert({
	refresh_token: '18f5302c-0409-4ff6-b881-e51fc67ed4ed',
	scope: [ 'user' ],
	client_id: 'backoffice',
	user: 'jandiew@gmail.com',
	version: '01', 
	deleted: 'false'
});