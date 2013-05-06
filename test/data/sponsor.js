db.sponsor_level.remove();
db.sponsor_level.insert({label: 'SponsorLevel1', version: '01', deleted: 'false'});
db.sponsor_level.insert({label: 'SponsorLevel2', version: '01', deleted: 'false'});
db.sponsor_level.insert({label: 'SponsorLevel3', version: '01', deleted: 'false'});
db.sponsor.remove();
db.sponsor.insert({
	email: 'test@sponsor.com',
	name: 'Test SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'SponsorLevel1',
	website: 'www.test.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test', 
	version: '01', 
	deleted: 'false'
});
db.sponsor.insert({
	email: 'test2@sponsor.com',
	name: 'Test2 SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'SponsorLevel2',
	website: 'www.test2.com',
	city: 'Paris',
	country: 'France',
	phone: '0102030405',
	about: 'All about test 2', 
	version: '01', 
	deleted: 'false'
});
db.sponsor.insert({
	email: 'test3@sponsor.com',
	name: 'Test3 SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'SponsorLevel2',
	website: 'www.test3.com',
	city: 'Paris',
	country: 'France', 
	version: '01', 
	deleted: 'false'
});
db.sponsor.insert({
	email: 'test4@sponsor.com',
	name: 'Test3 SA',
    logo: 'http://www.test.com/icons/logo.gif',
	level: 'SponsorLevel2',
	website: 'www.test4.com',
	city: 'Paris',
	country: 'France', 
	version: '02', 
	deleted: 'true'
});
