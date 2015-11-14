var mongoose = require('mongoose');

var schema = mongoose.Schema({

  // Location name
  name: String,

  // Address
  street_address: String,
  city_address: String,
  state_address: String,
  zip_address: String,

  // Average rating
  avg_rating: Number,

  // Total number of ratings received
  total_ratings: Number,

  // Refs to reviews for this location
  reviews: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Review'
  }],

  // Users who've recently pooped here
  recent_checkins: [{
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User'
  }],

  // Reviewed and approved
  approved: Boolean,

  // Geo location
  loc: {
    type: { type: String },
    coordinates: [] // [lng, lat]
  }

});

schema.index({
  loc: '2dsphere'
});

module.exports = mongoose.model('Location', schema);

