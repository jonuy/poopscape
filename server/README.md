Poopscape Backend

---

Powered by [Node.js](https://nodejs.org/) and [MongoDB](https://www.mongodb.org/).

### Seeding the db with test data

Seed data is taken from the `*.seed.js` files in the `seed/` folder.

And then you can run this command which will clear and re-seed the database:
```
$ node seed.js
```

### Running Automated Tests

```
$ npm test
```

### HTTP Endpoints

#### Locations

##### GET /locations

Returns locations near a point.

Parameters:
- lng (required) - Longitude of coordinate to search near
- lat (required) - Latitude of coordinate to search near
- max_distance (Default 1600) - Maximum distance in meters a search result can be

##### GET /locations/:location_id

Returns a specific location by its id.

##### POST /locations/new

Create a new location.

Parameters:
- name (required) - Name of the location
- street (required) - Street address
- city (required) - City address
- state - State address
- zip - Zip address
- lat (required) - Latitude of the location
- long (required) - Longitude of the location

#### Reviews

#### Users

#### Check-ins

_TBD_
