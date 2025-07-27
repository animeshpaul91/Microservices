var WebSocketServer = require('ws').WebSocketServer;
var wss = new WebSocketServer({ port: 5001 });

wss.on('connection', function (mySocket) {
    console.log('Client connected');

    mySocket.on('message', function (message) {
        console.log('Received: ' + message);
        mySocket.send('Hello from server!');
    });

    mySocket.on('close', function () {
        console.log('Client disconnected');
    });
});
