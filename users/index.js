var express = require('express');
var User = require('./models/User');
var router = express.Router();
var mongoose = require('mongoose');
var responseHelper = require('../helpers/responseHelper.js');

/**
 * GET /:type/:identifier
 *
 * Retrieve a specific user by either email or id.
 */
router.get('/:type/:identifier', function(req, res) {
  var condition;
  var query;
  var type = req.params.type;
  var id = req.params.identifier;

  // Only email or id type allowed
  if (type !== 'id' && type !== 'email') {
    responseHelper.sendError(res, 400, 'Invalid type ' + type + '. Only `id` or `email` allowed');
    return;
  }

  // Valid ObjectId is either 12 byte string or 24 character hex
  if (type == 'id' && !isValidObjectId(id)) {
    responseHelper.sendError(res, 400, 'The user ID needs to either be a 12 byte string or 24 character hex');
    return;
  }

  condition = {};
  if (type == 'id') {
    condition._id = new mongoose.Types.ObjectId(id);
  }
  else if (type == 'email') {
    condition.email = id;
  }

  User.findOne(condition)
    .populate('reviews')
    .exec(function(err, doc) {
      if (err) {
        console.log(err);
        responseHelper.sendError(res, 500, 'Error getting the user ' + id);
        return;
      }

      if (doc) {
        res.status(200).send(doc);
      }
      else {
        responseHelper.sendError(res, 404, 'No user found for ' + id);
      }
    });
});

/**
 * POST /new
 *
 * Create a new user.
 */
router.post('/new', function(req, res) {
  var user;
  var fname = req.body.fname;
  var linit = req.body.linit;
  var email = req.body.email;

  if (!fname || !linit || !email) {
    responseHelper.sendError(res, 400, 'Missing one or more required params');
    return;
  }

  if (typeof email !== 'string') {
    responseHelper.sendError(res, 400, 'Invalid email');
  }
  else {
    email = email.toLowerCase();
  }

  var promise = User.findOne({email: email}).exec();
  promise.then(function(doc) {
    var errMsg;
    var newUser;

    // User with this email already exists
    if (doc) {
      errMsg = 'User with this email already exists: ' + email;
      console.log(errMsg);
      responseHelper.sendError(res, 400, errMsg);

      // Throw error to break promise chain
      throw new Error(errMsg);
    }
    // Free to create a user with this email
    else {
      newUser = new User({
        fname: fname,
        linit: linit,
        email: email
      });

      return newUser.save();
    }
  })
  .then(function(doc) {
    if (doc) {
      console.log('Created new user: ' + doc.fname + ' ' + doc.linit + '. / ' + doc._id);
      res.status(201).send(doc);
      promise.fulfill();
    }
  })
  .then(null, function(err) {
    if (err) {
      console.log(err);
      responseHelper.sendError(res, 500, 'Error saving the new user');
    }
  });
});

/**
 * PUT /:uid
 *
 * Update info on a user.
 */
router.put('/:uid', function(req, res) {
  var update;
  var uid = req.params.uid;

  if (!isValidObjectId(uid)) {
    responseHelper.sendError(res, 400, 'The user ID needs to either be a 12 byte string or 24 character hex');
    return;
  }

  update = {};
  if (req.body.fname) {
    update.fname = req.body.fname;
  }
  if (req.body.linit) {
    update.linit = req.body.linit;
  }
  if (req.body.email) {
    update.email = req.body.email;
  }

  User.findByIdAndUpdate(uid, update, function(err, doc) {
    if (err) {
      console.log(err);
      responseHelper.sendError(res, 500, 'Error updating user ' + uid);
      return;
    }

    if (doc) {
      console.log('Updated user ' + uid);

      // The `doc` returned here is just the one found, not the updated version of it.
      // So manually updating so the client can use the updated one if it wants.
      if (update.fname) doc.fname = update.fname;
      if (update.linit) doc.linit = update.linit;
      if (update.email) doc.email = update.email;

      res.status(200).send(doc);
    }
    else {
      responseHelper.sendError(res, 404, 'No user found for ' + uid);
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