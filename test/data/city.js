db.country.remove();
db.country.insert({cid: 'Country1', name: 'CountryName1', version: '01', deleted: 'false'});
db.country.insert({cid: 'Country2', name: 'CountryName2', version: '02', deleted: 'true'});
db.country.insert({cid: 'Country3', name: 'CountryName3', version: '03', deleted: 'false'});
db.country.insert({cid: 'Country4', name: 'CountryName4', version: '02', deleted: 'true'});
db.city.remove();
db.city.insert({name: 'City1OfCountry1', cid: 'Country1', version: '01', deleted: 'false'});
db.city.insert({name: 'City2OfCountry1', cid: 'Country1', version: '02', deleted: 'false'});
db.city.insert({name: 'City3OfCountry1', cid: 'Country1', version: '03', deleted: 'false'});
db.city.insert({name: 'City1OfCountry2', cid: 'Country2', version: '02', deleted: 'true'});
db.city.insert({name: 'City1OfCountry3', cid: 'Country3', version: '01', deleted: 'false'});
db.city.insert({name: 'City2OfCountry3', cid: 'Country3', version: '02', deleted: 'false'});