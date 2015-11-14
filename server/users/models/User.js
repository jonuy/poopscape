var mongoose = require('mongoose');

var schema = mongoose.Schema({

  // First name
  fname: String,

  // Last initial
  linit: String,

  // Email
  email: String,

  // Reviews left by the user
  reviews: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Review'
  }],

  checkins: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Location'
  }]

});

schema.index({
  email: 1
});

module.exports = mongoose.model('User', schema);