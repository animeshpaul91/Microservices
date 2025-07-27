var sock = new WebSocket('ws://localhost:5001');

sock.onopen = function () {
    console.log('Connected to server');
    console.log('Sending message to server every second for 10 seconds');

    const intervalId = setInterval(function () {
        sock.send('Hello from client!');
    }, 1000); // send every 1 second

    setTimeout(function () {
        clearInterval(intervalId); // stop after 5 seconds
    }, 10000);
};

sock.onmessage = function (event) {
    console.log('Received from server: ' + event.data);
};

sock.onclose = function () {
    console.log('Disconnected from server');
};

sock.onerror = function (error) {
    console.error('WebSocket error: ' + error);
};