var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  res.send('GET /users');
});

module.exports = router;