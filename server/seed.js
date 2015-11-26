var seeder = require('mongoose-seed');
var locationData = require('./seed/locations.seed.js');
var reviewData = require('./seed/reviews.seed.js');
var userData = require('./seed/users.seed.js');

seeder.connect('mongodb://localhost/poopscape', function() {
  seeder.loadModels([
      'locations/models/Location.js',
      'reviews/models/Review.js',
      'users/models/User.js'
    ]);

  seeder.clearModels([
      'Location',
      'Review',
      'User'
    ], function() {
      seeder.populateModels(locationData);
      seeder.populateModels(reviewData);
      seeder.populateModels(userData);
    });
});