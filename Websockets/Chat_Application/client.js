const { io } = require('socket.io-client');

// Connect to the Socket.IO server running on localhost:3000
const socket = io('http://localhost:3000');


// When the client successfully connects to the server
socket.on('connect', () => {
    console.log(`You are now connected to server with ID ${socket.id}`);
    console.log('Sending message to server every second for 10 seconds');

    // Send a message to the server every second for 10 seconds
    const intervalId = setInterval(() => {
        // Emit a custom event 'send-message' with a message payload
        socket.emit('send-message', 'Hello from client!');
    }, 1000);

    // Stop sending messages after 10 seconds
    setTimeout(() => {
        clearInterval(intervalId);
    }, 10000);

    // Listen for 'receive-message' events from the server
    socket.on('receive-message', (data) => {
        console.log('Received from server: ' + data);
    });
});


// When the client disconnects from the server
socket.on('disconnect', () => {
    console.log('Disconnected from server');
});


// Handle connection errors
socket.on('error', (error) => {
    console.error('Socket.IO error: ' + error);
});
