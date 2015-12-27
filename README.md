Poopscape Backend
---

Powered by [Node.js](https://nodejs.org/) and [MongoDB](https://www.mongodb.org/).

## Seeding the db with test data

Seed data is taken from the `*.seed.js` files in the `seed/` folder.

And then you can run this command which will clear and re-seed the database:
```
$ node seed.js
```

## Running Automated Tests

```
$ npm test
```


## HTTP Endpoints


### Locations

#### GET /locations

Returns locations near a point.

Parameters:
- lng (required) - Longitude of coordinate to search near
- lat (required) - Latitude of coordinate to search near
- max_distance (Default 1600) - Maximum distance in meters a search result can be

#### GET /locations/:location_id

Returns a specific location by its id.

#### POST /locations/new

Create a new location.

Parameters:
- name (required) - Name of the location
- street (required) - Street address
- city (required) - City address
- state - State address
- zip - Zip address
- lat (required) - Latitude of the location
- long (required) - Longitude of the location


### Reviews

#### POST /reviews/new

Create a new review for a location.

Parameters:
- lid (required) - Location ID the review is for
- uid (required) - User ID of the user submitting the review
- rating (required) - _Number_ Rating given to the location
- review (optional) - _String_ A written review to provide more details

### Users

#### POST /users/new

Creates a new user.

Parameters:
- fname (required) - User first name
- linit (required) - User last initial
- email (required) - User email address

#### PUT /users/:uid

Update a user's info.

The `:uid` is the user's user ID.

Parameters:
- fname - Set this to adjust the user first name
- linit - Set this to change the user's last initial
- email - Set this to change the user's email address
#### GET /users/:type/:identifier

Returns a single user by either email or user id.

Possible `:type` fields:
- id - User ID
- email - User email

The `:identifier` is the email or user id of the user.

### Check-ins

_TBD_


## Deployment

Current plan is to host this on Heroku. A pipeline and stuff will eventually get setup. In the meantime, the `server-prod` branch is what will get deployed. The `git subtree` command can be used to ensure only the contents of the **server** folder are in the `server-prod` branch.

```
$ git checkout master
$ git subtree push --prefix server origin server-prod
```

The Heroku app is configured to automatically deploy on pushes to the `server-prod` branch. Otherwise, to automatically deploy the branch to Heroku, the following command can be run.

```
$ git push heroku-prod server-prod:master
```

Note: the `heroku-prod` remote name can vary depending on the git setup.