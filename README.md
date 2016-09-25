# spring-socks-example

### A small example of using SockJS with STOMP for full duplex comms with Spring. Heavily inspired by Sergi Almar's presentation on Spring WebSockets.

This is a very hacky prototype, but it demonstrates what can be achieved.

The idea is multiple people will access http://localhost:8080/ (The first time you do this will prompt you for a user/password. Use "client1" and "pass". These credentials are replaced by the UsernameHandshakeHandler class to simulate new unique users each time you open a new http://localhost:8080/ tab).

If you try this you'll see each window gets assigned a different username.

Each person declares when they are "ready" to be activated by clicking the Toggle Status button.

http://localhost:8080/server.html contains a simple RESTful service to simulate when the server decides to activate all people who have declared themselves to be ready. When this button is pressed, you should see that the browsers containing people that have declared themselves ready should change to an activated status.
