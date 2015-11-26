var mongoose = require('mongoose');

var data = [
  {
    _id: mongoose.Types.ObjectId('locseed00000'),
    name: 'Dunkin Donuts',
    street_address: '361 1st Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: 10010,
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.9796427, 40.7357309]
    },
    reviews: [
      mongoose.Types.ObjectId('rvwseed00000'),
      mongoose.Types.ObjectId('rvwseed00001')
    ]
  },
  {
    _id: mongoose.Types.ObjectId('locseed00001'),
    name: 'Dunkin Donuts',
    street_address: '266 1st Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10009',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.9809114, 40.7315461]
    },
    reviews: [
      mongoose.Types.ObjectId('rvwseed00002')
    ]
  },
  {
    _id: mongoose.Types.ObjectId('locseed00002'),
    name: 'Dunkin Donuts',
    street_address: '542 E 14th St',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10009',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.9781923, 40.7294784]
    }
  },
  {
    _id: mongoose.Types.ObjectId('locseed00003'),
    name: 'Dunkin Donuts',
    street_address: '215 1st Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10009',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.9855774, 40.7306461]
    }
  },
  {
    _id: mongoose.Types.ObjectId('locseed00004'),
    name: 'Starbucks',
    street_address: '400 E 23rd St',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10010',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.987364, 40.740301]
    },
    reviews: [
      mongoose.Types.ObjectId('rvwseed00003'),
      mongoose.Types.ObjectId('rvwseed00004')
    ]
  },
  {
    _id: mongoose.Types.ObjectId('locseed00005'),
    name: 'Starbucks',
    street_address: '286 1st Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10009',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.980894, 40.733136]
    }
  },
  {
    _id: mongoose.Types.ObjectId('locseed00006'),
    name: 'Starbucks',
    street_address: '219 1st Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10003',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.983248, 40.73085]
    }
  },
  {
    _id: mongoose.Types.ObjectId('locseed00007'),
    name: 'Starbucks',
    street_address: '145 2nd Avenue',
    city_address: 'New York',
    state_address: 'NY',
    zip_address: '10003',
    approved: true,
    loc: {
      type: 'Point',
      coordinates: [-73.987439, 40.729408]
    }
  }
];

module.exports = [
  {
    'model': 'Location',
    'documents': data
  }
];