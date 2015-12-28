var express = require('express');
var mongoose = require('mongoose');
var Review = require('./models/Review');
var router = express.Router();
var responseHelper = require('../helpers/responseHelper.js');

var Location = require('../locations/models/Location');
var User = require('../users/models/User');

var multer = require('multer');
var upload = multer(); // for parsing multipart/form-data

/**
 * POST /new
 *
 * Create a new review.
 */
router.post('/new', function(req, res) {
  var _id;
  var promise;
  var ref;
  var lid = req.body.lid;
  var uid = req.body.uid;
  var rating = req.body.rating;
  var review = req.body.review;

  if (!lid || !uid || !rating) {
    responseHelper.sendError(res, 400, 'Some required parameters are missing');
    return;
  }

  if (!isValidObjectId(lid) || !isValidObjectId(uid)) {
    responseHelper.sendError(res, 400, 'lid or uid is invalid. Needs to either be a 12 byte string or 24 character hex.');
    return;
  }

  // Combining lid and uid to make the actual id
  ref = lid + ':' + uid;

  update = {
    ref: ref,
    lid: lid,
    uid: uid,
    rating: rating
  };
  if (review) update.review = review;

  options = {upsert: true};

  promise = Review.findOne({ref: ref}).exec();
  promise.then(function(doc) {
    var newReview;

    if (doc) {
      _id = doc._id;

      return doc.update({$set: update});
    }
    else {
      newReview = new Review(update);
      return newReview.save();
    }
  }).
  then(function(doc) {
    if (doc) {
      if (!_id) {
        _id = doc._id;
      }

      return Location.update({_id: lid}, {$addToSet: {reviews: _id}});
    }
    else {
      throw new Error();
    }
  }).
  then(function() {
    return User.update({_id: uid}, {$addToSet: {reviews: _id}});
  }).
  then(function() {
    var responseData = update;
    responseData._id = _id;

    res.status(202).send(responseData);
  }).
  then(null, function(err) {
    if (err) {
      console.log(err);
      responseHelper.sendError(res, 500, 'Error saving review ' + ref);
      return;
    }
  });
});

module.exports = router;

/********************
 * Helper functions *
 ********************/

/**
 * Checks if value is a valid mongoose ObjectId.
 *
 * @param id The value to check
 * @return Boolean
 */
function isValidObjectId(id) {
  return typeof id === 'string' && (id.length == 12 || id.length == 24);
}