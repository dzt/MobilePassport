var users  = require('./controllers/users');

module.exports = function(app, passport) {

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
    app.get('/user/:id', users.read);
    app.get('/user/myProfile/' + req.user._id, isLoggedIn, users.read); 
    app.put('/signup', users.create); 
    app.post('/user/:id', isLoggedIn, users.update);
    app.delete('/user/:id', isLoggedIn, users.delete); // example: localhost:3000/user/56918c9fb764de20280b2ef0
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