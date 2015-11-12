var express = require('express');
var router = express.Router();

var multer = require('multer');
var upload = multer(); // for parsing multipart/form-data

router.get('/', function(req, res) {
  res.send('GET /reviews');
});

module.exports = router;