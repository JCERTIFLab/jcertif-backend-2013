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
	sessions: ['01', '02'], 
	version: '01', 
	deleted: 'false'
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
	biography: 'All about test', 
	version: '01', 
	deleted: 'false'
});
db.speaker.insert({
	email: 'test2@speaker.com',
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
	version: '02', 
	deleted: 'true'
});