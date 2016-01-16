var User = require('../models/user');
var imgs = ['png', 'jpg', 'jpeg', 'gif', 'bmp'];

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

module.exports.read = function(req, res) {
    User.findById(req.params.id, function(err, user) {
        if (user) {
            res.writeHead(200, {"Content-Type": "application/json"});
            user = user.toObject();
            delete user.password;
            res.end(JSON.stringify(user));
        } else {
            return res.status(400).end('User not found');
        }
    });
};

module.exports.update = function(req, res) {
    User.findById(req.params.id, function(err, user) {
        if (user) {
            if (user.username != req.user.username) {
                return res.status(401).end('Modifying other user');
            } else {
                user.name = req.body.name ? req.body.name : user.name;
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

module.exports.profile = function(req, res) {
    res.end(JSON.stringify(user));
};

module.exports.delete = function(req, res) {
    User.remove({_id: req.params.id}, function(err) {
        res.end('Deleted')
    });
};