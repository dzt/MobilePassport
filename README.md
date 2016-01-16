# MobilePassport
Authentication with Passport.js on Mobile

|    URL    | Method |                                                 Success Response (200 OK)                                                 | Error Response |             Description             |              Parameters             |
|:---------:|:------:|:-------------------------------------------------------------------------------------------------------------------------:|:--------------:|:-----------------------------------:|:-----------------------------------:|
|   /login  |   GET  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Unauthorized  |       Login as existing user,       |         username & password         |
|  /signup  |   PUT  | { "_id": "569998f20d3f752c66c421ef", "email": "example@me.com", "username": "example", "name": "Example User", "__v": 0 } |  Invalid input |       Register as a new user.       | name, username, password, and email |
| /user/:id |  POST  |                                                                                                                           |  Not Logged In |             Update User.            | name, username, password, and email |
| /user/:id | DELETE |                                                          Deleted                                                          | Unauthorized   |        Delete yourself nerd.        |                                     |
|  /logout  |  POST  |                                                         Logged out                                                        |  Not Logged In | Logout as currently logged in user. |            None Required            |
