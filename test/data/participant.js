db.participant.remove();
db.participant.insert({
	email: 'test@participant.com',
	title: 'Developer',
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
	email: 'test-senior@participant.com',
	title: 'Senior Developer',
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
	title: 'Chief executor',
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
db.session.remove();
db.session.insert({
    id : "03",
    title : "title 3",
    summary : "summary 3",
    description : "description 3",
    status : "status 3",
    keyword : "keyword 3",
    category : ["HTML 5", "Android"],
    start : "12/02/2013 10:22",
    end : "16/02/2013 10:23",
    speakers : ["11", "12"]
});
db.session.insert({
    id : "101",
    title : "title 1",
    summary : "summary 1",
    description : "description 1",
    status : "status 1",
    keyword : "keyword 1",
    category : ["HTML 5", "Android"],
    start : "12/02/2013 10:22",
    end : "16/02/2013 10:23",
    speakers : ["11", "12"]
});
db.session.insert({
    id : "102",
    title : "title 2",
    summary : "summary 2",
    description : "description 2",
    status : "status 2",
    keyword : "keyword 2",
    category : ["HTML 5", "Android"],
    start : "12/02/2013 10:22",
    end : "16/02/2013 10:23",
    speakers : ["21", "22"]
});