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
db.site.insert({
    id : "102",
    name : "name 2",
    street : "street 2",
    city : "city 2",
    country : "country 2",
    contact : "contact@website2.com",
    website : "www.website2.com",
    description : "description 2",
    photo : "http://www.website2.com/pictures/website2.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.remove();
db.room.insert({
	id : "101",
    name : "name 1",
    site : "101",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "102",
    name : "name 2",
    site : "101",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "103",
    name : "name 3",
    site : "102",
    seats : "100",
    description : "A small size room",
    photo : "http://www.website2.com/pictures/rooms/room3.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "104",
    name : "name 4",
    site : "102",
    seats : "100",
    description : "A small size room",
    photo : "http://www.website2.com/pictures/rooms/room4.gif", 
	version: '02', 
	deleted: 'true'
});
