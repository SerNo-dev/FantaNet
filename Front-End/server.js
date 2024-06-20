const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 8081 });

wss.on('connection', ws => {
  console.log('Client connected');

  ws.on('message', message => {
    console.log(`Received message => ${message}`);
    
    let parsedMessage;
    try {
      parsedMessage = JSON.parse(message);
    } catch (e) {
      console.error('Invalid JSON:', e);
      return;
    }


    const broadcastMessage = JSON.stringify(parsedMessage);
    wss.clients.forEach(client => {
      if (client.readyState === WebSocket.OPEN) {
        client.send(broadcastMessage);
      }
    });
  });

  ws.on('close', () => {
    console.log('Client disconnected');
  });
});

console.log('WebSocket server started on port 8081');
