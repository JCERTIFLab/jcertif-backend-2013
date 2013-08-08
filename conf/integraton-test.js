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
    name : "salle 1",
    site : "01",
    seats : "500",
    description : "This is the bigest room",
    photo : "http://www.website1.com/pictures/rooms/room1.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "02",
    name : "salle 2",
    site : "01",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "03",
    name : "salle 3",
    site : "01",
    seats : "200",
    description : "A medium size room",
    photo : "http://www.website1.com/pictures/rooms/room2.gif", 
	version: '01', 
	deleted: 'false'
});
db.room.insert({
    id : "04",
    name : "salle 4",
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
    biography: 'I am an accomplished IT professional with over ten years of experience. As a senior consultant and trainer at Fujitsu Canada, I have worked in a variety of industries such as Insurance, Transportation and Education using different technologies and programming languages including Java and JEE, Microsoft, and Oracle as well as Open Source technologies. Very involved in the Java and Android Communities, I have been leading the expansion of the Java User Groups and Google Developer Groups movement in Africa with enthusiasm. I am collaborating with global companies such as Oracle and Google in order to promote African developer communities and get support for their local events and initiatives. I have strong communication and interpersonal skills and am very much at ease in diverse cultural settings, having lived in Africa, Europe and North America. My blog : http://www.bonbhel.com',
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
    biography: "Président de Jcertif France && Manager Senior JCertif International, je suis membre et  co-leader de Congo-Jug. Je suis un homme passionné et audacieux, j’aime croire que c’est possible lorsqu’on se donne les moyens. Ingénieur développeur et formateur  JAVA JEE, je partage mon temps entre des missions de formation et développement projet  au sein de la communauté JCertif, la plus grande communauté sur le développement des applications WEB et MOBILES en Afrique.",
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
    biography: "Consultant Senior chez CGI France et Certifié IBM WAS 7.0, j'ai participé à différents projets de développements d'applications Java/JEE pour différents grands comptes dont IBM France, Conforama et SGCIB. Passionné des architectures distribuées, je développe actuellement mes compétences sur l'intégration des processus métiers (EAI) et sur les architectures orientées services (SOA).",
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
    biography: 'Honoré Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam ornare blandit egestas. Morbi ornare id massa sed fringilla. In eros tellus, facilisis aliquam gravida ac, condimentum lobortis urna. Quisque in nisi non dolor condimentum porta nec at nisi. Curabitur euismod, elit in dapibus mattis, arcu eros lacinia metus, id sollicitudin urna lorem id velit. Fusce sagittis posuere orci vel cursus. Vestibulum tristique lacus non risus scelerisque accumsan. Etiam blandit fermentum nisi sed scelerisque. Pellentesque et nunc risus. Morbi eu odio vitae ante ullamcorper tincidunt ac non erat. Donec ultricies risus quis orci luctus bibendum ac at nulla. Suspendisse at elit turpis. Donec nec velit dui',
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
db.speaker.insert({
    email: 'n.cishugi@gmail.com',
    title: 'M.',
    password: 'mm3qZc+CWB9Uil6PEEh1sTIzMGO/NpRdYYIoJg==',
    lastname: 'Nelson',
    firstname: 'Cishugi',
    website: '',
    city: 'Brazzaville',
    country: 'CONGO',
    company: 'NedBox',
    phone: '',
    photo: 'https://github.com/JCERTIFLab/jcertif-static-resources/raw/master/img/speakers/2013/nelson.cishugi.jpg',
    biography: "Web designer évoluant en Afrique notamment au Congo, en République Démocratique du Congo,au Kenya et au Rwanda, je travaille aussi bien avec des institutions internationales telles le PNUD ou l'Union Européenne, que des sociétés commerciales parmi lesquelles Nestlé ou encore ECAir. Diplômé en Marketing Management et Certifié Expert Adobe sur sa Creative Suite, ma passion est d'adapter le web et ses outils aux besoins réels de l'Afrique, aussi bien en termes de contenus et d'ergonomie que de design.",
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
    deleted: 'false',
    room: '01'
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
    deleted: 'false',
    room: '01'
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
    deleted: 'false',
    room: '01'
});
db.session.insert({
    id : "04",
    title : "Cursus Java - Partie 1",
    summary : "Initiation à Java",
    description : "Initiation à Java ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Java",
    category : ['Java'],
    start : "10/09/2013 09:00",
    end : "10/09/2013 12:00",
    speakers : ["honorenz@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '01'
});
db.session.insert({
    id : "05",
    title : "Cursus Java - Partie 2",
    summary : "Initiation à Java",
    description : "Initiation à Java ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Java",
    category : ['Java'],
    start : "10/09/2013 13:00",
    end : "10/09/2013 17:00",
    speakers : ["honorenz@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '01'
});
db.session.insert({
    id : "06",
    title : "Cursus Web - Partie 1",
    summary : "Initiation au web",
    description : "Initiation au web ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "HTML",
    category : ['Web'],
    start : "10/09/2013 09:00",
    end : "10/09/2013 12:00",
    speakers : ["rossi.oddet@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '02'
});
db.session.insert({
    id : "07",
    title : "Cursus Web - Partie 2",
    summary : "Initiation au web",
    description : "Initiation au web ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "HTML",
    category : ['Web'],
    start : "10/09/2013 13:00",
    end : "10/09/2013 17:00",
    speakers : ["rossi.oddet@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '02'
});
db.session.insert({
    id : "08",
    title : "Cursus Web Design - Partie 1",
    summary : "Initiation au Web Design",
    description : "Initiation au Web Designipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Photoshop",
    category : ['Web Design'],
    start : "11/09/2013 09:00",
    end : "11/09/2013 12:00",
    speakers : ["lunikvie@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '02'
});
db.session.insert({
    id : "09",
    title : "Cursus Web Design - Partie 2",
    summary : "Initiation à Web Design",
    description : "Initiation à Web Design ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Photoshop",
    category : ['Web Design'],
    start : "11/09/2013 13:00",
    end : "11/09/2013 17:00",
    speakers : ["lunikvie@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '02'
});
db.session.insert({
    id : "10",
    title : "Cursus Java - Partie 3",
    summary : "Initiation à Java",
    description : "Initiation à Java ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Java",
    category : ['Java'],
    start : "11/09/2013 09:00",
    end : "11/09/2013 12:00",
    speakers : ["honorenz@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '01'
});
db.session.insert({
    id : "11",
    title : "Cursus Java - Partie 4",
    summary : "Initiation à Java",
    description : "Initiation à Java ipsum dolor sit amet, consectetur adipiscing elit. Mauris luctus elit dui. Aliquam",
    status: "Brouillon",
    keyword : "Java",
    category : ['Java'],
    start : "11/09/2013 13:00",
    end : "11/09/2013 17:00",
    speakers : ["honorenz@gmail.com"],
    version: '02',
    deleted: 'false',
    room: '01'
});
db.session.insert({
    id : "12",
    title : "Initiation Android - Premiers pas",
    summary : "Initiation Android - Premiers pas",
    description : "Découvrir c’est quoi Android. Mettre en place l’environnement de travail. Comprendre la structure et le rôle des différents fichiers utilisés dans un projet Android (Manifest.xml, les layouts, …).",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "10/09/2013 09:00",
    end : "10/09/2013 12:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "13",
    title : "Initiation Android - Gestions des activités",
    summary : "Initiation Android - Gestions des activités",
    description : " Cycle de vie d’une activité. Communication entre les activités. Les layouts. Les fenêtres Dialog et Toast. Les menus contextuels.",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "10/09/2013 13:00",
    end : "10/09/2013 17:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "14",
    title : "Initiation Android - Les éléments graphiques (partie 1)",
    summary : "Initiation Android - Les éléments graphiques (partie 1)",
    description : "Les éléments graphiques usuels : TextView, EditText, Button,…  Les éléments graphiques complexes : ListView, Gallery, Carousel, GridView, Spinner, AutoCompleteText, ViewFlipper,…",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "11/09/2013 09:00",
    end : "11/09/2013 12:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "15",
    title : "Initiation Android - Les éléments graphiques (partie 2)",
    summary : "Initiation Android - Les éléments graphiques (partie 2)",
    description : "Compléter la découverte des éléments graphiques complexes.",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "11/09/2013 13:00",
    end : "11/09/2013 17:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "16",
    title : "Initiation Android - Concepts avancés (partie 1)",
    summary : "Initiation Android - Concepts avancés (partie 1)",
    description : "Base de données locales SQLite. Services.  Les permissions.",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "12/09/2013 09:00",
    end : "12/09/2013 12:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "17",
    title : "Initiation Android - Concepts avancés (partie 2)",
    summary : "Initiation Android - Concepts avancés (partie 2)",
    description : "Fragments. Bien organiser son projet. Construire, signer et déployer son application.",
    status: "Brouillon",
    keyword : "Android",
    category : ['Android'],
    start : "12/09/2013 13:00",
    end : "12/09/2013 17:00",
    speakers : ["firas.gabsi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '03'
});
db.session.insert({
    id : "18",
    title : "Les applications qui touchent au web design : Quand les utiliser ? Leurs forces et leurs faiblesses",
    summary : "Les applications qui touchent au web design : Quand les utiliser ? Leurs forces et leurs faiblesses",
    description : "Annuaire des applications pour les web designers. Les différentes solutions existantes à chaque étape du projet. (Draft, framework,...)",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "10/09/2013 09:00",
    end : "10/09/2013 12:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
});
db.session.insert({
    id : "19",
    title : "Fireworks et Adobe, qu'y a t-il de mieux à travailler avec ?",
    summary : "Fireworks et Adobe, qu'y a t-il de mieux à travailler avec ?",
    description : "Le petit plus de Fireworks sur Photoshop et les autres applications Adobe orientées web et comment maximiser son rendu avec Javascript",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "10/09/2013 13:00",
    end : "10/09/2013 17:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
});
db.session.insert({
    id : "20",
    title : "Fireworks les bases",
    summary : "Fireworks les bases",
    description :  "Les pré-requis pour utiliser Fireworks et paramétrer son espace de travail selon le gabarit du site (PC, Tablette, Mobile).",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "11/09/2013 09:00",
    end : "11/09/2013 12:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
});
db.session.insert({
    id : "21",
    title : "Les outils prédéfinis et leur utilisation",
    summary : "Les outils prédéfinis et leur utilisation",
    description : "Les outils de création et de modification dans Fireworks.",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "11/09/2013 13:00",
    end : "11/09/2013 17:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
});
db.session.insert({
    id : "22",
    title : "Intégration d'un template crée avec Photoshop - Utilisation des Sprites CSS",
    summary : "Intégration d'un template crée avec Photoshop - Utilisation des Sprites CSS",
    description : "Intégration d'un projet conçu sous PS dans FW - Simplifier certaines animations avec les Sprites, les avantages et les limites.",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "12/09/2013 09:00",
    end : "12/09/2013 12:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
});
db.session.insert({
    id : "23",
    title : "Intégration CSS - Utilisation du module pour les codes (Exemple avec le JQuery pour mobile)",
    summary : "Intégration CSS - Utilisation du module pour les codes (Exemple avec le JQuery pour mobile)",
    description : "Intégration d'une feuille CSS dans le module pour FW - Comment intégrer du jquery à un design sous FW (Si jamais je n'ai pas fini, ce dernier chapitre sera supprimé et retenu pour l'année prochaine). On sait que pour Jcertif dixit Max <i>C'est faire le grand écart entre ceux qui sont déjà utilisateurs des technologies et ceux pour qui c'est la toute première fois qu'il voit une interface de logiciels</i>",
    status: "Brouillon",
    keyword : "Web Design",
    category : ['Web Design'],
    start : "12/09/2013 13:00",
    end : "12/09/2013 17:00",
    speakers : ["n.cishugi@gmail.com"],
    version: '01',
    deleted: 'false',
    room: '04'
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
