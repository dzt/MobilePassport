var users  = require('./controllers/users');
var express = require('express');

module.exports = function(app, passport) {

    // Login
    app.post('/login', function(req, res, next) {
        passport.authenticate('local', function(err, user, info) {
            if (err)
                return next(err);
            if (!user)
                return res.json({ SERVER_RESPONSE: 0, SERVER_MESSAGE: "Wrong credentials" });
            req.logIn(user, function(err) {
                if (err)
                    return next(err);
                return res.json({ SERVER_RESPONSE: 1, SERVER_MESSAGE: "Logged in!" });
            });
        })(req, res, next);
    });

    // Search For User by ID
    app.get('/user/:id', users.read);

    // Search For User by Username
    app.get('/user/search/:username', users.readByUsername);

    // My Profile 
    app.get('/user/profile', isLoggedIn, users.me); 

    // Register [x]
    app.put('/signup', users.create); 

    // Update As Current User [x]
    app.post('/user/update', isLoggedIn, users.update);

    // Delete Currently Logged in User [x]
    app.delete('/user/delete', isLoggedIn, users.delete);

    // Log Out [x]
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