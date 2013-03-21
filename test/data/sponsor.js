db.sponsor.remove();
db.sponsor.insert({
	email: 'test@sponsor.com',
	name: 'Test SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'Platinium',
	website: 'www.test.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test'
});
db.sponsor.insert({
	email: 'test2@sponsor.com',
	name: 'Test2 SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'Gold',
	website: 'www.test2.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test 2'
});
db.sponsor.insert({
	email: 'test3@sponsor.com',
	name: 'Test3 SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'Gold',
	website: 'www.test3.com',
	city: 'Paris',
	country: 'France'
});
