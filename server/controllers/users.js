var User = require('../models/user');
var passport = require('passport');

module.exports = {};

module.exports.create = function(req, res) {
    if (!req.body.name || !req.body.username || !req.body.password || !req.body.email)
        return res.status(400).end('Invalid input');

    User.findOne({ username:  req.body.username }, function(err, user) {
        if (user) {
            return res.status(400).end('User already exists');
        } else {

            var newUser = new User();
            newUser.name = req.body.name;
            newUser.username = req.body.username;
            newUser.password = newUser.generateHash(req.body.password);
            newUser.email = req.body.email;

            newUser.save();

            res.writeHead(200, {"Content-Type": "application/json"});

            newUser = newUser.toObject();
            delete newUser.password;
            res.end(JSON.stringify(newUser));
        }
    });
};

module.exports.login = function(req, res, next) {
        passport.authenticate('local', function(err, user, info) {
            if (err)
                return next(err);
            if(!user)
                return res.json({SERVER_RESPONSE: 0, SERVER_MESSAGE: "Fucked Up"})
            req.logIn(user, function(err) {
                if (err)
                    return next(err);
                if (!err)
                     /*if (!user)
                        res.writeHead(400, {"Content-Type": "application/json"});
                        return res.json({ SERVER_RESPONSE: 0, SERVER_MESSAGE: "Wrong credentials" });*/
                   
                    return res.json({ SERVER_RESPONSE: 1, SERVER_MESSAGE: "Logged in!" });
                
            });
        })(req, res, next);
};

module.exports.read = function(req, res) {
    User.findById(req.params.id, function(err, user) {
        if (user) {
            res.writeHead(200, {"Content-Type": "application/json"});
            user = user.toObject();
            delete user.password;
            delete user.__v;
            res.end(JSON.stringify(user));
        } else {
            return res.status(400).end('User not found');
        }
    });
};

module.exports.readByUsername = function(req, res) {
    User.findOne({ username: req.params.username }, function(err, user) {
        if (user) {
            res.writeHead(200, {"Content-Type": "application/json"});
            user = user.toObject();
            delete user.password;
            delete user.__v;
            res.end(JSON.stringify(user));
        } else {
            return res.status(400).end('User not found');
        }
    });
};

module.exports.me = function(req, res) {

    User.findOne({ username: req.user.username }, function(err, user) {
        if (user) {
            res.writeHead(200, {"Content-Type": "application/json"});
            user = user.toObject();
            delete user.password;
            delete user.__v;
            res.end(JSON.stringify(user));
        } else {
            return res.status(400).end('User not found');
        }
    });
    
};


module.exports.update = function(req, res) {
    User.findById(req.user.id, function(err, user) {
        if (user) {
            if (user.username != req.user.username) {
                return res.status(401).end('Modifying other user');
            } else {
                user.name = req.body.name ? req.body.name : user.name;
                user.desc = req.body.dec ? req.body.desc : user.desc;
                user.username = req.body.username ? req.body.username : user.username;
                user.password = req.body.password ? user.generateHash(req.body.password) : user.password;
                user.email = req.body.email ? req.body.email : user.email;
                user.save();

                res.writeHead(200, {"Content-Type": "application/json"});
                user = user.toObject();
                delete user.password;
                res.end(JSON.stringify(user));
            }
        } else {
            return res.status(400).end('User not found');
        }
    });
};

module.exports.delete = function(req, res) {
    User.remove({_id: req.user.id}, function(err) {
        res.end('Deleted')
});
};