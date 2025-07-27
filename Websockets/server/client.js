var sock = new WebSocket('ws://localhost:5001');

sock.onopen = function () {
    console.log('Connected to server');

    setTimeout(function () {
        sock.send('Hello from client!');
    }, 1000);
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