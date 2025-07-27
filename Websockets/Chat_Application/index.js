const io = require('socket.io')(3000, {
    cors: {
        origin: 'http://localhost:8080', // Allow requests from this origin
        methods: ['GET', 'POST'],
        allowedHeaders: ['my-custom-header'],
        credentials: true
    }
}); // Initialize Socket.IO on port 3000

io.on('connection', (socket) => {
    console.log('A client with ID ' + socket.id + ' is connected');
    socket.on('send-message', (msg) => {
        console.log('Received message: ' + msg);
        // Echo the message back to the client
        socket.send('Server received: ' + msg);
    });
});


