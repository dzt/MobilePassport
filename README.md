# MobilePassport
Authentication with Passport.js on Mobile

# API Reference

|    URL    | Method |                                                 Success Response (200 OK)                                                 | Error Response |             Description             |              Parameters             |
|:---------:|:------:|:-------------------------------------------------------------------------------------------------------------------------:|:--------------:|:-----------------------------------:|:-----------------------------------:|
|   /login  |   GET  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Unauthorized  |       Login as existing user,       |         username & password         |
|  /signup  |   PUT  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Invalid input |       Register as a new user.       | name, username, password, and email |
| /user/:id |  POST  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Not Logged In |             Update User.            | name, username, password, and email |
| /user/:id | DELETE |                                                          Deleted                                                          | Unauthorized   |        Delete yourself nerd.        |                                     |
| /user/:id |   GET  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Not Logged In |            User not found           |                                     |
|  /logout  |  POST  |                                                         Logged out                                                        |  Not Logged In | Logout as currently logged in user. |            None Required            |

Note: In order to run the Node.js server you must create a file under the name of `.env` and make sure to add your own API Keys

```

MONGOLAB_URI=< Your MongoDB URI without the "<" and ">" >

```

Install the packages by running
`npm install`

Run the application by typing
`node app.js`

Licensed under the **[MIT License] [license]**.

[license]: https://github.com/dzt/MobilePassport/blob/master/LICENSE