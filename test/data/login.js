db.participant.remove();
db.participant.insert({
	email: 'test@participant.com',
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
db.speaker.remove();
db.speaker.insert({
	email: 'test@speaker.com',
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