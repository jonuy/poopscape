var mongoose = require('mongoose');

var data = [
  {
    _id: mongoose.Types.ObjectId('rvwseed00000'),
    ref: mongoose.Types.ObjectId('locseed00000') + ':' + mongoose.Types.ObjectId('usrseed00000'),
    lid: mongoose.Types.ObjectId('locseed00000'),
    uid: mongoose.Types.ObjectId('usrseed00000'),
    rating: 1,
    review: 'Do NOT poop here. It\'s been pooped in enough already. Poop in the stalls. Poop on the walls. You don\'t need to add to it.'
  },
  {
    _id: mongoose.Types.ObjectId('rvwseed00001'),
    ref: mongoose.Types.ObjectId('locseed00000') + ':' + mongoose.Types.ObjectId('usrseed00001'),
    lid: mongoose.Types.ObjectId('locseed00000'),
    uid: mongoose.Types.ObjectId('usrseed00001'),
    rating: 2,
    review: 'Avoid in most scenarios. But when in a pinch, I mean, there is at least a toilet to go in. Lines are sometimes long though and they will card you before allowing you to poop.'
  },
  {
    _id: mongoose.Types.ObjectId('rvwseed00002'),
    ref: mongoose.Types.ObjectId('locseed00001') + ':' + mongoose.Types.ObjectId('usrseed00003'),
    lid: mongoose.Types.ObjectId('locseed00001'),
    uid: mongoose.Types.ObjectId('usrseed00003'),
    rating: 3,
    review: 'Not the greatest. But it gets the job done. Sinks available so you can wash your hands after your business.'
  },
  {
    _id: mongoose.Types.ObjectId('rvwseed00003'),
    ref: mongoose.Types.ObjectId('locseed00004') + ':' + mongoose.Types.ObjectId('usrseed00000'),
    lid: mongoose.Types.ObjectId('locseed00004'),
    uid: mongoose.Types.ObjectId('usrseed00000'),
    rating: 4,
    review: 'I like pooping here. Sometimes when I\'m in the neighborhood I come by to poop just because. Sometimes I don\'t even need to poop and come just to sit in the stalls. It smells nice.'
  },
  {
    _id: mongoose.Types.ObjectId('rvwseed00004'),
    ref: mongoose.Types.ObjectId('locseed00004') + ':' + mongoose.Types.ObjectId('usrseed00003'),
    lid: mongoose.Types.ObjectId('locseed00004'),
    uid: mongoose.Types.ObjectId('usrseed00003'),
    rating: 5,
    review: '5 stars. Would absolutely poop here again. Toilets imported from Japan keep your bum warm and cleaner than you ever knew it could get.'
  }
];

module.exports = [
  {
    'model': 'Review',
    'documents': data
  }
];