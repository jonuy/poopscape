var express = require('express');
var Location = require('./models/Location');
var router = express.Router();

/**
 * GET /
 *
 * This does nothing.
 */
router.get('/', function(req, res) {
  if (!req.query.long) {
    res.status(400).send('long query value required');
  }
  else if (!req.query.lat) {
    res.status(400).send('lat query value required');
  }

  res.status(404).send('This endpoint doesn\'t do anything');
});

/**
 * GET /:lid
 *
 * Retrieve a specific location by id.
 */
router.get('/:lid', function(req, res) {
  res.send('GET /' + req.params.lid);
});

/**
 * POST /new
 *
 * Create a new location.
 */
router.post('/new', function(req, res) {
  res.status(201).send('yup');
});

module.exports = router;