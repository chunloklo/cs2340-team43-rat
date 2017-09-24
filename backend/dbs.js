var Datastore = require('nedb')

var db = new Datastore({ filename: __dirname + '/data.db', autoload: true });

module.exports = db;