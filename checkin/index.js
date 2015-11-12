var express = require('express');
var router = express.Router();

router.get('/', function(req, res) {
  res.send('GET /checkin');
});

module.exports = router;