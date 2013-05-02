db.category.remove();
db.category.insert({label: 'Category1', version: '01', deleted: 'false'});
db.category.insert({label: 'Category2', version: '02', deleted: 'false'});
db.category.insert({label: 'Category3', version: '03', deleted: 'false'});
db.category.insert({label: 'Category4', version: '02', deleted: 'true'});