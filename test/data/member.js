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