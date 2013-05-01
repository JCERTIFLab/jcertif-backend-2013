db.session_status.remove();
db.session_status.insert({label: 'Brouillon', version: '01', deleted: 'false'});
db.category.remove();
db.category.insert({label: 'Java', version: '01', deleted: 'false'});
db.site.remove();
db.site.insert({
    id : "101",
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
    site : "101",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "02",
    name : "name 2",
    site : "101",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
	deleted: 'false'
});
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
    speakers : ["11", "12"], 
	version: '01', 
	deleted: 'false'
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
    speakers : ["21", "22"], 
	version: '01', 
	deleted: 'false'
});
db.session.insert({
    id : "103",
    title : "title 3",
    summary : "summary 3",
    description : "description 3",
    status : "status 3",
    keyword : "keyword 3",
    category : ["HTML 5", "Android"],
    start : "12/02/2013 10:22",
    end : "16/02/2013 10:23",
    speakers : ["21", "22"], 
	version: '02', 
	deleted: 'true'
});
