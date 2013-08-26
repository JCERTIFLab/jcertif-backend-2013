db.country.remove();
db.country.insert({cid: 'Country1', name: 'CountryName1', version: '01', deleted: 'false'});
db.country.insert({cid: 'Country2', name: 'CountryName2', version: '02', deleted: 'true'});
db.country.insert({cid: 'Country3', name: 'CountryName3', version: '03', deleted: 'false'});
db.country.insert({cid: 'Country4', name: 'CountryName4', version: '02', deleted: 'true'});