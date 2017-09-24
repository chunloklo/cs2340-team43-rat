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


//todo, clean up method logic so we're not writing lots of redundant code
module.exports.register = function (req, res) {

    db.findOne({
        username: req.params.username
    }, function (err, doc) {
        //if there are no duplicates

        var message = "";
        if (doc === null) {
            db.insert({
                username: req.params.username,
                password: req.params.password
            }, function (err, doc) {
                if (err) {
                    message = "Registration Insertion Error, " + err;
                    res.status(500).json({error: message});
                    console.log(message);
                } else {
                    message = "Successfully registered user " + req.params.username;
                    res.status(200).send(doc._id);
                    console.log(message);
                }
            });
        } else if (err) {
            message = "Registration Lookup Error for user " + req.params.username + "; " + err;
            res.status(500).json({error: message});
            console.log(message);
        } else {
            message = "User already exists for user " + req.params.username;
            res.status(400).json({error: message});
            console.log(message);
        }
    });

};

module.exports.login = function (req, res) {
    db.findOne({
        username: req.params.username,
        password: req.params.password
    }, function (err, doc) {
    //if there are no duplicates
        var message = "";
        if (doc === null) {
            message = "Invalid credentials for user " + req.params.username;
            res.status(401).json({error: message});
            console.log(message);
        } else if (err) {
            message = "Login Error with user " + req.params.username + "; " + err;
            console.log(message);
            res.status(500).json({error: message});
        } else {
            message = "Successfully logged in for user " + req.params.username;
            console.log(message);
            res.status(200).send(doc._id);
        }
    });

};

module.exports.api = function (req, res) {
    //basic health test endpoint
    res.status(200).send("Success");
};
