var express = require('express');
var bodyParser = require('body-parser');
var mongoose = require('mongoose');
var app = express();
var router = express.Router();

// Mongoose db connection
mongoose.connect('mongodb://localhost/poopscape');

// For parsing application/json requests
app.use(bodyParser.json());

// Root path
router.get('/', function(req, res) {
  res.send('Find code for this app at https://github.com/jonuy/poopscape');
});

app.use('/', router);

// All the other resources
var locations = require('./locations');
var users = require('./users');
var reviews = require('./reviews');
var checkin = require('./checkin');

app.use('/locations', locations);
app.use('/users', users);
app.use('/reviews', reviews);
app.use('/checkin', checkin);

// Start up the server
var server = app.listen(3000, function() {
  var host = server.address().address;
  var port = server.address().port;

  console.log('Poopscape backend listening at http://%s:%s', host, port);
});

module.exports = server;