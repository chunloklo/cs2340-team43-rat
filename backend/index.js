//dependencies for our web server
//express does most of the heavy lifting
var express = require('express');
var bodyParser = require('body-parser');
var cors = require('cors')

//requires our helpful database file from dbs.js
var controller = require('./controller');

//sets up web server
var app = express();


app.set('port', process.env.PORT || 3000);
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors())

app.get('/api', controller.api);
app.get('/api/register/:username/:password/', controller.register);
app.get('/api/login/:username/:password/', controller.login);
// normally we'd be using post with hashed passes, but that's a lot of work


app.listen(app.get('port'), function() {
    console.log('App is running at http://localhost:%d', app.get('port')); 
    console.log('  Press CTRL-C to stop\n');
});

module.exports = app;