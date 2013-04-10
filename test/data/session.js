db.session_status.remove();
db.session_status.insert({label: 'Brouillon'});
db.session.remove();
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
