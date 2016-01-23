var mongoose = require('mongoose');
var bcrypt   = require('bcrypt-nodejs');
var md5 = require('MD5');

var userSchema = mongoose.Schema({
    name: String,
    username: String,
    password: String,
    email: String,
    raw_avatar_url_16: String,
  	raw_avatar_url_32: String,
  	raw_avatar_url_64: String,
  	avatar_url_150: String
});

userSchema.methods.generateHash = function(password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

userSchema.methods.validPassword = function(password) {
    return bcrypt.compareSync(password, this.password);
};

userSchema.virtual('avatar_url_16').get(function() {
  if (this.use_gravatar) {
    return "//gravatar.com/avatar/" + md5(this.email) + ".png?s=16";
  } else {
    return this.raw_avatar_url_16;
  }
});

userSchema.virtual('avatar_url_32').get(function() {
  if (this.use_gravatar) {
    return "//gravatar.com/avatar/" + md5(this.email) + ".png?s=32";
  } else {
    return this.raw_avatar_url_32;
  }
});

userSchema.virtual('avatar_url_64').get(function() {
  if (this.use_gravatar) {
    return "//gravatar.com/avatar/" + md5(this.email) + ".png?s=64";
  } else {
    return this.raw_avatar_url_64;
  }
});

userSchema.virtual('avatar_url_150').get(function() {
  if (this.use_gravatar) {
    return "//gravatar.com/avatar/" + md5(this.email) + ".png?s=150";
  } else {
    return this.raw_avatar_url_64;
  }
});

module.exports = mongoose.model('User', userSchema);