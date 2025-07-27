var WebSocketServer = require('ws').WebSocketServer;
var wss = new WebSocketServer({ port: 5001 });

wss.on('connection', function (mySocket) {
    console.log('Client connected');

    mySocket.on('message', function (message) {
        console.log('Received: ' + message);
        console.log('Broadcasting message to all connected clients');
        wss.clients.forEach(function (client) {
            if (client.readyState === WebSocket.OPEN) {
                client.send(message);
            }
        });
    });

    mySocket.on('close', function () {
        console.log('Client disconnected');
    });
});
