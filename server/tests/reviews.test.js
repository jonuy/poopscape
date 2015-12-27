var assert = require('assert');
var async = require('async');
var mongoose = require('mongoose');
var request = require('supertest');
var Location = require('../locations/models/Location');
var Review = require('../reviews/models/Review');
var User = require('../users/models/User');

describe('', function() {
  var server;
  var locationId;
  var userId;
  var reviewId;
  var testLocation = {
    name: 'test_location',
    street_address: 'test street address',
    city_address: 'test city',
    street_address: 'NY',
    zip_address: '10001',
    loc: {
      type: 'Point',
      coordinates: [74.0059, 40.7127]
    },
    approved: false
  };
  var testUser = {
    fname: 'test_fname',
    linit: 'l',
    email: 'test@example.com'
  };
  var testRating = 3;
  var testReview = 'This is a test review.';

  before(function(done) {
    server = require('../index.js');

    async.waterfall([
      // Create a test location
      function(callback) {
        Location.create(testLocation, function(err, doc) {
          if (!err && doc) {
            locationId = doc._id;
            callback();
          }
        });
      },
      // Create a test user
      function(callback) {
        User.create(testUser, function(err, doc) {
          if (!err) {
            userId = doc._id;
            callback();
          }
        });
      },
      function() {
        done();
      }
    ], function(err) {
      assert(false, 'Error during setup of /reviews tests');
    });
  });

  it('(POST /reviews/new) creates a review', function(done) {
    request(server)
      .post('/reviews/new')
      .send({
        lid: locationId,
        uid: userId,
        rating: testRating,
        review: testReview
      })
      .expect(202)
      .expect(function(res) {
        assert.equal(res.body.ref, locationId + ':' + userId);
        assert.equal(res.body.lid, locationId);
        assert.equal(res.body.uid, userId);
        assert.equal(res.body.rating, testRating);
        assert.equal(res.body.review, testReview);

        reviewId = res.body._id;
      })
      .end(done);
  });

  it('(POST /reviews/new) verify location and user docs got updated', function(done) {
    async.waterfall([
      function(callback) {
        Location.findById(locationId, function(err, location) {
          assert.equal(location.reviews[0], reviewId);
          callback();
        });
      },
      function(callback) {
        User.findById(userId, function(err, user) {
          assert.equal(user.reviews[0], reviewId);
          callback();
        });
      },
      function() {
        done();
      }
    ], function(err) {
      assert(false, 'Error during locationg and user update verification');
    });
  });

  after(function(done) {
    async.waterfall([
      // Remove test location
      function(callback) {
        Location.remove({_id: locationId}, function() {
          callback();
        });
      },
      // Remove test user
      function(callback) {
        User.remove({_id: userId}, function() {
          callback();
        });
      },
      // Remove the test review
      function(callback) {
        Review.remove({_id: reviewId}, function() {
          callback();
        });
      },
      function() {
        server.close();
        done();
      }
    ], function(err) {
      assert(false, 'Error during cleanup of /reviews tests');
    });
  });
});