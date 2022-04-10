const http = require('http');

const hostname = '127.0.0.1';
const port = 8080;

function onRequest(request, response) {
    console.log("Request Recieved");

    response.setHeader('content-type', 'application/json');
    response.statusCode = 200;

    if (typeof request.header['reqtype'] == 'undefined') {
        response.statusCode = 400;
    }

    switch (request.header['reqtype']) {
        case 'company-search':
            response.statusCode = 200;
            break;
        case 'upc-search':
            response.statusCode = 200;
            break;
        default:
            response.statusCode = 400;
            response.write(JSON.stringify({ err: 'unrecognized reqtype' }));
    }

    response.end
}

const server = http.createServer(onRequest).listen(port);
console.log("Server Online");