db.site.remove();
db.site.insert({
    id : "01",
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
    site : "01",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "02",
    name : "name 2",
    site : "01",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
	deleted: 'false'
});
db.speaker.remove();
db.speaker.insert({
    email: 'martialsomda@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Somda',
    firstname: 'Martial',
    website: 'https://plus.google.com/u/0/103524117708800337304/post',
    city: 'Paris',
    country: 'FRANCE',
    company: 'Capgemini',
    phone: '',
    photo: 'https://secure.gravatar.com/avatar/bb13813fbc7d64b3a528924fa0960d61?s=400&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png',
    biography: 'Martial Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'rossi.oddet@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Oddet',
    firstname: 'Rossi',
    website: 'http://blog.roddet.com',
    city: 'Nantes',
    country: 'FRANCE',
    company: 'SQLI',
    phone: '',
    photo: 'https://secure.gravatar.com/avatar/7b3ffda0746f3f4503b7a3094ec1ef95?s=420&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png',
    biography: 'Rossi Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'bonbhel@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Bonbhel',
    firstname: 'Max',
    website: 'http://bonbhel.com',
    city: 'Quebec',
    country: 'CANADA',
    company: 'Fujitsu',
    phone: '',
    photo: 'https://secure.gravatar.com/avatar/b38c860d01f1976089983cf74479d316?s=420&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png',
    biography: ' Max Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'rmalonga9@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Chrisbel',
    firstname: 'Roland',
    website: 'http://chrisbel.net',
    city: 'Paris',
    country: 'FRANCE',
    company: 'Odellya Consulting',
    phone: '',
    photo: 'https://secure.gravatar.com/avatar/627784ca4e9955a4755b89c0dd028993?s=400&d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png',
    biography: 'Roland Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'audran.zhazha@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Youlou Zhazha',
    firstname: 'Audran',
    website: 'https://plus.google.com/u/0/101766498398690247380/posts',
    city: 'Paris',
    country: 'FRANCE',
    company: 'BNP Paribas',
    phone: '',
    photo: 'https://lh4.googleusercontent.com/-FEiFbt1HtBU/AAAAAAAAAAI/AAAAAAAAABc/NndL5Z63_G0/s120-c/photo.jpg',
    biography: 'Audran Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'honorenz@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Nzambu',
    firstname: 'Honoré',
    website: 'https://plus.google.com/u/0/103777575657747514286',
    city: 'Kinshasa',
    country: 'RDC',
    company: 'Altitude Concept',
    phone: '',
    photo: 'https://apis.google.com/c/u/0/photos/private/AIbEiAIAAABDCK6H0MG9oam2NCILdmNhcmRfcGhvdG8qKDc3M2M0Mzg0OTRiZWU3ZDcwM2Q0YzU3NzAwMDNjOWVjZDIwZjdhZjUwAdUs9mxyv6s3zL4LRD0ugk7huIoH?sz=90',
    biography: 'Honor� Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'lunikvie@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Ngoyi',
    firstname: 'Dodo',
    website: 'https://plus.google.com/u/0/112620052567755671165',
    city: 'Brazzaville',
    country: 'CONGO',
    company: 'ARPCE',
    phone: '',
    photo: 'https://lh4.googleusercontent.com/-4kEkEC4Ot4M/AAAAAAAAAAI/AAAAAAAAAGk/tvR3BC79xAw/s120-c/photo.jpg',
    biography: 'Dodo Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'firas.gabsi@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Gabsi',
    firstname: 'Firas',
    website: 'https://plus.google.com/u/0/106786988013133048222/posts',
    city: 'Tunis',
    country: 'TUNISIE',
    company: 'JCertif',
    phone: '',
    photo: 'https://lh6.googleusercontent.com/-KzV6foLH6yg/AAAAAAAAAAI/AAAAAAAAAFI/MhU-e1oPZOI/s120-c/photo.jpg',
    biography: 'Firas Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.speaker.insert({
    email: 'akinidanielle@gmail.com',
    title: 'Mlle',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Akini',
    firstname: 'Danielle',
    website: 'https://plus.google.com/u/0/116225765638889651370/posts',
    city: 'Douala',
    country: 'CAMEROUN',
    company: 'Invest Logistic',
    phone: '',
    photo: 'https://lh3.googleusercontent.com/-rK9Pg0Hchk4/AAAAAAAAAAI/AAAAAAAAAMk/uktDAMkYk38/s120-c/photo.jpg',
    biography: 'Danielle Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
    version: '01',
    deleted: 'false'
});
db.session.remove();
db.session.insert({
    id : "01",
    title : "Bilan & Vision JCertif",
    summary : "Bilan & Vision JCertif",
    description : "Bilan & Vision JCertif ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "JCertif",
    category : ['Community'],
    start : "09/09/2013 09:00",
    end : "09/09/2013 09:50",
    speakers : ["bonbhel@gmail.com"],
    version: '01',
    deleted: 'false'
});
db.session.insert({
    id : "02",
    title : "JCertif Education",
    summary : "JCertif Education",
    description : "JCertif Education ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "JCertif",
    category : ['Community'],
    start : "09/09/2013 10:00",
    end : "09/09/2013 10:50",
    speakers : ["rmalonga9@gmail.com"],
    version: '03',
    deleted: 'false'
});
db.session.insert({
    id : "03",
    title : "JCertif Lab",
    summary : "JCertif Lab",
    description : "JCertif Lab ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "JCertif",
    category : ['Community'],
    start : "09/09/2013 11:00",
    end : "09/09/2013 11:50",
    speakers : ["rossi.oddet@gmail.com"],
    version: '02',
    deleted: 'true'
});
db.sponsor.remove();
db.sponsor.insert({
    name: 'Google',
    email: 'google@google.fr',
    city : 'Mountain View',
    country : 'US',
    logo: 'https://raw.github.com/JCERTIFLab/jcertif-static-resources/master/img/sponsors/google.png',
    website : 'http://www.google.com',
    level : 'Titanium',
    deleted: 'false'
});
db.sponsor.insert({
    name: 'Oracle',
    email: 'oracle@oracle.fr',
    logo: 'https://raw.github.com/JCERTIFLab/jcertif-static-resources/master/img/sponsors/oraclent.png',
    website : 'http://www.oracle.com',
    city : 'San Francisco',
    country : 'US',
    level : 'Platine',
    deleted: 'false'
});
db.sponsor.insert({
    name: 'Warid',
    email: 'warid@warid.fr',
    logo: 'https://raw.github.com/JCERTIFLab/jcertif-static-resources/master/img/sponsors/warid.gif',
    website : 'http://www.warid.com',
    city : 'Brazzaville',
    country : 'CONGO',
    level : 'Platine',
    deleted: 'false'
});
db.sponsor.insert({
    name: 'Objis',
    email: 'objis@objis.fr',
    logo: 'https://raw.github.com/JCERTIFLab/jcertif-static-resources/master/img/sponsors/objis.png',
    website : 'http://www.objis.com',
    city : 'Brazzaville',
    country : 'CONGO',
    level : 'Or',
    deleted: 'false'
});
db.sponsor.insert({
    name: 'Burotop',
    email: 'burotop@burotop.fr',
    logo: 'https://raw.github.com/JCERTIFLab/jcertif-static-resources/master/img/sponsors/burotop.png',
    website : 'http://www.burotop.com',
    city : 'Brazzaville',
    country : 'CONGO',
    level : 'Or',
    deleted: 'false'
});
