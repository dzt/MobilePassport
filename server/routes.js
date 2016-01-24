var users  = require('./controllers/users');
var express = require('express');

module.exports = function(app, passport) {

    app.get('/', function(req, res) {
        res.writeHead(200, {"Content-Type": "application/json"});
        res.end("MobilePassport API v1");
    });

    // Login [x]
    app.post('/login', users.login);

    // Register [x]
    app.post('/signup', users.create); 

    // Search For User by ID [x]
    app.get('/user/search/id/:id', users.read);

    // Search For User by Username [x]
    app.get('/user/search/username/:username', users.readByUsername);

    // My Profile for Currently Logged in User [x]
    app.get('/user/profile', isLoggedIn, users.me); 

    // Update As Currently Logged In User [x]
    app.post('/user/update', isLoggedIn, users.update);

    // Delete Currently Logged in User [x]
    app.delete('/user/delete', isLoggedIn, users.delete);

    // [x]
    app.post('/logout', function(req, res) {
        req.logout();
        res.end('Logged out')
    });

};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated())
        return next();
    res.end('Not logged in');
}