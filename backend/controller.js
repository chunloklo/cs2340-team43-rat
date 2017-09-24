var db = require('./dbs');

module.exports = {};
// basic schema is 
/*
    {
        username: String,
        password: String
    }

    I am beyond lazy so we will not be doing password hashing
    because tbh who is really gonna use this app
*/

module.exports.register = function (req, res) {
    console.log("Registering user")
    db.insert({
        username: req.params.username,
        password: req.params.password
    }, function (err, doc) {
        if (err) {
            res.status(500).send("Uh oh, " + err);
        } else {
            res.status(200).send(doc._id);
        }
    });

};

module.exports.login = function (req, res) {
    console.log("Logging in")
    db.findOne({
        username: req.params.username,
        password: req.params.password
    }, function (err, doc) {
    //if there are no duplicates
        if (doc === null) {
            res.status(401).send("Invalid credentials");
        } else if (err) {
            res.status(500).send("Uh oh, " + err);
        } else {
            res.status(200).send(doc._id);
        }
    });

};

module.exports.api = function (req, res) {
    res.status(200).send("Success");
};
