db.site.remove();
db.site.insert({
    id : "01",
    name : "name 1",
    street : "street 1",
    city : "city 1",
    country : "country 1",
    contact : "contact@website1.com",
    website : "www.website1.com",
    description : "description 1",
    photo : "http://www.website1.com/pictures/website1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.remove();
db.room.insert({
	id : "01",
    name : "name 1",
    site : "01",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "02",
    name : "name 2",
    site : "01",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
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
	photo: 'http://martinfowler.com/mf-ade-home.jpg',
	biography: 'I am an author, speaker… essentially a loud-mouthed pundit on the topic of software development. I’ve been working in the software industry since the mid-80’s where I got into the then-new world of object-oriented software. I spent much of the 90’s as a consultant and trainer helping people develop object-oriented systems, with a focus on enterprise applications. In 2000 I joined ThoughtWorks.', 
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
	photo: 'http://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Larry_Elllison_on_stage.jpg/220px-Larry_Elllison_on_stage.jpg',
	biography: 'Larry Ellison was born in New York City to an unwed mother of Jewish heritage. His father was an Italian American U.S. Air Force pilot. After he contracted pneumonia at the age of nine months his mother gave him to her aunt and uncle for adoption. Larry did not meet his biological mother again until he was 48.', 
	version: '01', 
	deleted: 'false'
});
db.session.remove();
db.session.insert({
    id : "01",
    title : "title 1",
    summary : "summary 1",
    description : "description 1",
    status: "Brouillon",
    keyword : "keyword 1",
    category : ["HTML5", "Android"],
    start : "12/09/2013 10:00",
    end : "12/09/2013 12:00",
    speakers : ["fowler@acm.org", "larry.ellison@oracle.com"], 
	version: '01', 
	deleted: 'false'
});
db.session.insert({
    id : "02",
    title : "title 2",
    summary : "summary 2",
    description : "description 2",
    status: "Brouillon",
    keyword : "keyword 2",
    category : ["HTML5", "Android"],
    start : "16/09/2013 10:00",
    end : "16/09/2013 13:30",
    speakers : ["fowler@acm.org", "larry.ellison@oracle.com"], 
	version: '03', 
	deleted: 'false'
});
db.session.insert({
    id : "03",
    title : "title 3",
    summary : "summary 3",
    description : "description 3",
    status: "Brouillon",
    keyword : "keyword 3",
    category : ["HTML5", "Android"],
    start : "13/09/2013 08:30",
    end : "13/09/2013 12:00",
    speakers : ["fowler@acm.org", "larry.ellison@oracle.com"], 
	version: '02', 
	deleted: 'true'
});
db.participant.remove();
db.participant.insert({
	email: 'jandiew@gmail.com',
	title: 'M.',
	password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
	lastname: 'Johnson',
	firstname: 'Andriew',
	website: 'www.jandiew.com',
	city: 'Hostin',
	country: 'Texas',
	company: 'Cloudbees',
	phone: '0102030405',
	biography: 'All about Andriew',
	sessions: ['02'], 
	version: '01', 
	deleted: 'false'
});
db.sponsor.remove();
db.sponsor.insert({
	email: 'test@sponsor.com',
	name: 'Google',
        logo: 'http://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Google-Logo.svg/220px-Google-Logo.svg.png',
	level: 'Argent',
	website: 'www.google.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test', 
	version: '01', 
	deleted: 'false'
});
db.sponsor.insert({
	email: 'test2@sponsor.com',
	name: 'Oracle',
        logo: 'http://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Oracle_logo.svg/200px-Oracle_logo.svg.png',
	level: 'Titanium',
	website: 'www.oracle.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test 2', 
	version: '01', 
	deleted: 'false'
});
