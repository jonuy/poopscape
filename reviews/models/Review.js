var mongoose = require('mongoose');

var schema = mongoose.Schema({

  // Reference ID. In practice will be a combo of lid:uid
  ref: String,

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

schema.index({
  ref: 1
});

module.exports = mongoose.model('Review', schema);