var mongoose = require('mongoose');

var schema = mongoose.Schema({

  // Location ID
  lid: mongoose.Schema.Types.ObjectId,

  // User ID
  uid: mongoose.Schema.Types.ObjectId,

  // Rating
  rating: Number,

  // The actual review if one is left
  review: String,

  // Image URL on S3?
  photo: String

});

module.exports = mongoose.model('Review', schema);