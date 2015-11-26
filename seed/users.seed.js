var mongoose = require('mongoose');

var data = [
  {
    _id: mongoose.Types.ObjectId('usrseed00000'),
    fname: 'Cara',
    linit: 'A',
    email: 'test0@email.com',
    reviews: [
      mongoose.Types.ObjectId('rvwseed00000'),
      mongoose.Types.ObjectId('rvwseed00003')
    ],
    checkins: []
  },
  {
    _id: mongoose.Types.ObjectId('usrseed00001'),
    fname: 'Mady',
    linit: 'B',
    email: 'test1@email.org',
    reviews: [
      mongoose.Types.ObjectId('rvwseed00001')
    ],
    checkins: []
  },
  {
    _id: mongoose.Types.ObjectId('usrseed00002'),
    fname: 'Leah',
    linit: 'C',
    email: 'test2@email.io',
    reviews: [],
    checkins: []
  },
  {
    _id: mongoose.Types.ObjectId('usrseed00003'),
    fname: 'Joel',
    linit: 'D',
    email: 'test3@email.co',
    reviews: [
      mongoose.Types.ObjectId('rvwseed00002'),
      mongoose.Types.ObjectId('rvwseed00004')
    ],
    checkins: []
  }
];

module.exports = [
  {
    'model': 'User',
    'documents': data
  }
];