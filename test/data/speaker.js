db.session.insert({
    id : "104",
    title : "title 2",
    summary : "summary 2",
    description : "description 2",
    status: "Approuvé",
    keyword : "keyword 2",
    category : ["HTML 5", "Android"],
    start : "12/02/2013 10:22",
    end : "16/02/2013 10:23",
    speakers : ["fowler@acm.org"], 
	version: '03', 
	deleted: 'false'
});
db.speaker.remove();
db.speaker.insert({
	email: 'fowler@acm.org',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Fowler',
	firstname: 'Martin',
	website: 'http://martinfowler.com',
	city: 'Chicago',
	country: 'USA',
	company: 'ThoughtWorks',
	phone: '0102030405',
	photo: 'http://martinfowler.com/pictues/martinPic.gif',
	biography: 'All about Martin', 
	version: '03', 
	deleted: 'false'
});
db.speaker.insert({
	email: 'larry.ellison@oracle.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Ellison',
	firstname: 'Larry',
	website: 'www.larryellison.com',
	city: 'NYC',
	country: 'USA',
	company: 'Oracle Corporation',
	phone: '0102030405',
	photo: 'http://www.larryellison.com/pictues/LarryPic.gif',
	biography: 'All about Larry', 
	version: '01', 
	deleted: 'false'
});
db.speaker.insert({
	email: 'larry2.ellison@oracle.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Ellison',
	firstname: 'Larry',
	website: 'www.larryellison.com',
	city: 'NYC',
	country: 'USA',
	company: 'Oracle Corporation',
	phone: '0102030405',
	photo: 'http://www.larryellison.com/pictues/LarryPic.gif',
	biography: 'All about Larry', 
	version: '02', 
	deleted: 'true'
});