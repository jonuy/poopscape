var seeder = require('mongoose-seed');
var locationData = require('./seed/locations.seed.js');
console.log(locationData);

seeder.connect('mongodb://localhost/poopscape', function() {
  seeder.loadModels([
      'locations/models/Location.js',
    ]);

  seeder.clearModels([
      'Location'
    ], function() {
      seeder.populateModels(locationData);
    });
});