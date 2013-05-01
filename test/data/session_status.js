db.session_status.remove();
db.session_status.insert({label: 'Status1', version: '01', deleted: 'false'});
db.session_status.insert({label: 'Status2', version: '01', deleted: 'false'});
db.session_status.insert({label: 'Status3', version: '02', deleted: 'true'});