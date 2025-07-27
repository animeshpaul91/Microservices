const { io } = require('socket.io-client');
const socket = io('http://localhost:3000'); // Connect to the server

socket.on('connect', () => {
    console.log(`You are now connected to server with ID ${socket.id}`);
    console.log('Sending message to server every second for 10 seconds');

    const intervalId = setInterval(() => {
        socket.emit('send-message', 'Hello from client!'); // Emit a custom event with a message
    }, 1000); // send every 1 second

    setTimeout(() => {
        clearInterval(intervalId); // stop after 10 seconds
    }, 10000);

    socket.on('receive-message', (data) => { // Listen for messages from the server
        console.log('Received from server: ' + data);
    });
});

socket.on('disconnect', () => {
    console.log('Disconnected from server');
});

socket.on('error', (error) => {
    console.error('Socket.IO error: ' + error);
});
