db.civilite.remove();
db.civilite.insert({label: 'M.'});
db.civilite.insert({label: 'Mme'});
db.civilite.insert({label: 'Mlle'});
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
	sessions: ['01', '02']
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
	sessions: ['01', '03', '05']
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
	sessions: ['02']
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
	biography: 'All about test'
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
	biography: 'All about test-senior'
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
	biography: 'All about Andriew'
});