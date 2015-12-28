var express = require('express');
var Location = require('./models/Location');
var router = express.Router();
var mongoose = require('mongoose');
var responseHelper = require('../helpers/responseHelper.js');

/**
 * GET /
 *
 * Query params:
 *   lng - required
 *   lat - required
 *   max_distance - optional. Default is 1600
 *
 * Return locations near points given in query params.
 */
router.get('/', function(req, res) {
  var promise;
  var lng = parseFloat(req.query.lng);
  var lat = parseFloat(req.query.lat);
  var maxDist = req.query.max_distance ? Number(req.query.max_distance) : 1600;  // 1600 meters ~= 1 mile

  if (!lng) {
    responseHelper.sendError(res, 400, 'lng query value required');
    return;
  }
  else if (!lat) {
    responseHelper.sendError(res, 400, 'lat query value required');
    return;
  }

  var point = {type: 'Point', coordinates: [lng, lat]};
  promise = Location.geoNear(point, { maxDistance: maxDist, spherical: true});
  promise.then(function(results, stats) {
    var opts;

    if (results && results.length > 0) {
      opts = [{path: 'obj.reviews', model: 'Review'}];
      return Location.populate(results, opts);
    }
    else {
      responseHelper.sendError(res, 404, 'No locations nearby');
      throw new Error('No locations nearby');
    }
  }).then(function(results) {
    res.status(200).send(results);
  }).then(null, function(err) {
    console.log(err);
    responseHelper.sendError(res, 500, 'Error finding a location');
    throw new Error();
  });
});

/**
 * GET /:lid
 *
 * Retrieve a specific location by id.
 */
router.get('/:lid', function(req, res) {
  var lid = req.params.lid;

  // Valid ObjectId is either 12 byte string or 24 character hex
  if (typeof lid != 'string' || (lid.length != 12 && lid.length != 24)) {
    res.status(400).send('The location ID needs to either be a 12 byte string or 24 character hex');
    return;
  }

  Location.findOne({_id: new mongoose.Types.ObjectId(lid)})
    .populate('reviews')
    .exec(function(err, doc) {
      if (err) {
        console.log(err);
        res.status().send('Error getting the location: ' + lid);
        return;
      }

      if (doc) {
        res.status(200).send(doc);
      }
      else {
        responseHelper.sendError(res, 404, 'No location found for ' + lid);
      }
    });
});

/**
 * POST /new
 *
 * Create a new location.
 */
router.post('/new', function(req, res) {
  var loc;

  if (!req.body.name || !req.body.street || !req.body.city || !req.body.state ||
      !req.body.lat || !req.body.lng) {
    responseHelper.sendError(res, 400, 'Missing one or more of the required params.');
    return;
  }

  loc = new Location({
    name: req.body.name,
    street_address: req.body.street,
    city_address: req.body.city,
    state_address: req.body.state,
    zip_address: req.body.zip,
    loc: {
      type: 'Point',
      coordinates: [req.body.lng, req.body.lat]
    },
    approved: false
  });

  loc.save(function(err, doc) {
    if (err) {
      console.log(err);
      responseHelper.sendError(res, 500, 'Error saving the new location');
      return;
    }

    console.log('Created new location: ' + req.body.name + ' / ' + doc._id);
    res.status(201).send(doc);
  });
});

module.exports = router;