// Fichier d'initialisation des données référentielles
db.referentiel.remove();
db.sponsor_level.remove();
db.sponsor_level.insert({code: 'Titanium', label: 'Titanium'});
db.sponsor_level.insert({code: 'Platine', label: 'Platine'});
db.sponsor_level.insert({code: 'Or', label: 'Or'});
db.sponsor_level.insert({code: 'Argent', label: 'Argent'});
db.sponsor_level.insert({code: 'Community', label: 'Community'});
db.sponsor_level.insert({code: 'Education', label: 'Education'});
db.sponsor_level.insert({code: 'Média', label: 'Média'});
db.category.remove();
db.category.insert({code: 'Android', label: 'Android'});
db.category.insert({code: 'HTML5', label: 'HTML5'});
db.category.insert({code: 'Java', label: 'Java'});
db.category.insert({code: 'Entreprise', label: 'Entreprise'});
db.category.insert({code: 'Web Design', label: 'Web Design'});
