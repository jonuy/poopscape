var assert = require('assert');
var mongoose = require('mongoose');
var request = require('supertest');
var Location = require('../locations/models/Location');

describe('', function() {
  var server;
  var testLocationId;
  var testLocation = {
      name: 'TEST LOCATION',
      street: 'TEST STREET',
      city: 'TEST CITY',
      state: 'TEST STATE',
      lat: 40.7127,
      lng: 74.0059
    };

  before(function() {
    server = require('../index.js');
  });

  it('(POST /locations/new) errors when not enough info is provided', function(done) {
    request(server)
      .post('/locations/new')
      .expect(400)
      .end(done);
  });

  it('(POST /locations/new) can create a new location', function(done) {
    request(server)
      .post('/locations/new')
      .send(testLocation)
      .expect(201)
      .expect(function(res) {
        testLocationId = res.body._id;
        assert(typeof testLocationId === 'string');
      })
      .end(done);
  });

  it('(GET /locations/:lid) can get location by id', function(done) {
    request(server)
      .get('/locations/' + testLocationId)
      .expect(200)
      .expect(function(res) {
        assert.equal(res.body._id, testLocationId);
        assert.equal(res.body.name, testLocation.name);
        assert.equal(res.body.street_address, testLocation.street);
        assert.equal(res.body.city_address, testLocation.city);
        assert.equal(res.body.state_address, testLocation.state);
        assert.equal(res.body.loc.type, 'Point');
        assert.equal(res.body.loc.coordinates[0], testLocation.lng);
        assert.equal(res.body.loc.coordinates[1], testLocation.lat);
      })
      .end(done);
  });

  it('(GET /locations) returns no results if nothing is near a point', function(done) {
    request(server)
      .get('/locations?lat=-1&lng=-1')
      .expect(404)
      .end(done);
  });

  it('(GET /locations) can find location near a given point', function(done) {
    request(server)
      .get('/locations?lat='+testLocation.lat+'&lng='+testLocation.lng)
      .expect(200)
      .expect(function(res) {
        // Response is an array
        assert(res.body.length > 0);

        // The first result should probably be our test location
        assert.equal(res.body[0].dis, 0);
        assert.equal(res.body[0].obj._id, testLocationId);
      })
      .end(done);
  });

  after(function() {
    Location.remove({_id: testLocationId}, function() {
      server.close();
    })
  });
});