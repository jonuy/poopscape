var express = require('express');
var Location = require('./models/Location');
var router = express.Router();
var mongoose = require('mongoose');

/**
 * GET /
 *
 * Return locations near points given in query params.
 */
router.get('/', function(req, res) {
  var query;
  var lng = req.query.lng;
  var lat = req.query.lat;
  var maxDist = req.query.max_distance ? Number(req.query.max_distance) : 1600;  // 1600 meters ~= 1 mile

  if (!lng) {
    res.status(400).send('lng query value required');
    return;
  }
  else if (!lat) {
    res.status(400).send('lat query value required');
    return;
  }

  query = Location.where(
    {
      loc: {
        $near: {
          $geometry: {type: 'Point', coordinates: [lng, lat]},
          $maxDistance: maxDist
        }
      }
    }
  );

  query.find(function(err, docs) {
    if (err) {
      console.log(err);
      res.status(500).send('Error finding a location');
      return;
    }

    if (docs && docs.length > 0) {
      res.status(200).send(docs);
    }
    else {
      res.status(404).send('No locations nearby');
    }
  });
});

/**
 * GET /:lid
 *
 * Retrieve a specific location by id.
 */
router.get('/:lid', function(req, res) {
  var query;
  var lid = req.params.lid;

  // Valid ObjectId is either 12 byte string or 24 character hex
  if (typeof lid != 'string' || (lid.length != 12 && lid.length != 24)) {
    res.status(400).send('The location ID needs to either be a 12 byte string or 24 character hex');
    return;
  }

  query = Location.where({_id: new mongoose.Types.ObjectId(lid)});
  query.findOne(function(err, doc) {
    if (err) {
      console.log(err);
      res.status().send('Error getting the location: ' + lid);
      return;
    }

    if (doc) {
      res.status(200).send(doc);
    }
    else {
      res.status(404).send('No location found for ' + lid);
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
    res.status(400).send('Missing one or more of the required params.');
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
      res.status(500).send('Error saving the new location');
      return;
    }

    console.log('Created new location: ' + req.body.name + ' / ' + doc._id);
    res.status(201).send(doc);
  });
});

module.exports = router;