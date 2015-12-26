var assert = require('assert');
var mongoose = require('mongoose');
var request = require('supertest');
var User = require('../users/models/User');

describe('', function() {
  var server;
  var testUID;
  var testFakeUID = '123456789012345678901234';
  var testEmail = 'test@example.com';
  var testFakeEmail = 'fake@example.com';
  var testUser = {
    fname: 'test_fname',
    linit: 'l',
    email: testEmail
  };
  var testModifiedUser = {
    fname: 'modified_fname',
    linit: 'm',
    email: testEmail
  };

  before(function() {
    server = require('../index.js');
  });

  it('(POST /users/new) errors when creating a user with invalid data', function(done) {
    request(server)
      .post('/users/new')
      .send({})
      .expect(400)
      .end(done);
  });

  it('(POST /users/new) creates a new user', function(done) {
    request(server)
      .post('/users/new')
      .send(testUser)
      .expect(201)
      .expect(function(res) {
        testUID = res.body._id;
        assert(typeof testUID === 'string');
      })
      .end(done);
  });

  it('(GET /users/id/:user_fake) returns a 404 for a non-existent user id', function(done) {
    request(server)
      .get('/users/id/'+testFakeUID)
      .expect(404)
      .end(done);
  });

  it('(GET /users/id/:user_id)', function(done) {
    request(server)
      .get('/users/id/'+testUID)
      .expect(200)
      .expect(function(res) {
        assert.equal(res.body._id, testUID);
        assert.equal(res.body.fname, testUser.fname);
        assert.equal(res.body.linit, testUser.linit);
        assert.equal(res.body.email, testUser.email);
      })
      .end(done);
  });

  it('(GET /users/email/:email_fake) returns a 404 for non-existent email', function(done) {
    request(server)
      .get('/users/email/'+testFakeEmail)
      .expect(404)
      .end(done);
  });

  it('(GET /users/email/:email_id) returns a user by email', function(done) {
    request(server)
      .get('/users/email/'+testEmail)
      .expect(200)
      .expect(function(res) {
        assert.equal(res.body._id, testUID);
        assert.equal(res.body.fname, testUser.fname);
        assert.equal(res.body.linit, testUser.linit);
        assert.equal(res.body.email, testUser.email);
      })
      .end(done);
  });

  it('(PUT /users/:user_id)', function(done) {
    var testPUT;
    var verifyDbUpdate;

    // Define the tests
    testPUT = function() {
      request(server)
        .put('/users/'+testUID)
        .send(testModifiedUser)
        .expect(200)
        .expect(function(res) {
          assert.equal(res.body._id, testUID);
          assert.equal(res.body.fname, testModifiedUser.fname);
          assert.equal(res.body.linit, testModifiedUser.linit);
          assert.equal(res.body.email, testModifiedUser.email);
        })
        .end(verifyDbUpdate);
    };

    verifyDbUpdate = function() {
      request(server)
        .get('/users/id/'+testUID)
        .expect(function(res) {
          assert.equal(res.body._id, testUID);
          assert.equal(res.body.fname, testModifiedUser.fname);
          assert.equal(res.body.linit, testModifiedUser.linit);
          assert.equal(res.body.email, testModifiedUser.email);
        })
        .end(done);
    };

    // Execute the tests
    testPUT();
  });

  after(function() {
    User.remove({_id: testUID}, function() {
      server.close();
    });
  });
});