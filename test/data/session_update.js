db.session_status.remove();
db.session_status.insert({label: 'Status1'});
db.session_status.insert({label: 'Status2'});
db.category.remove();
db.category.insert({label: 'HTML 5'});
db.category.insert({label: 'Android'});
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
db.room.insert({
	id : "01",
    name : "name 1",
    site : "101",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif"
});
db.room.insert({
    id : "02",
    name : "name 2",
    site : "101",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif"
});