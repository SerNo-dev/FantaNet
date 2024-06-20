import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket$: WebSocketSubject<any>;

  constructor() {
    this.socket$ = webSocket({
      url: environment.wsUrl,
      deserializer: ({ data }) => JSON.parse(data)
    });
  }

  connect(): void {
    this.socket$.subscribe(
      message => this.handleMessage(message),
      error => console.error(error),
      () => console.log('WebSocket connection closed')
    );
  }

  disconnect(): void {
    this.socket$.complete();
  }

  sendMessage(message: any): void {
    this.socket$.next(message);
  }

  private handleMessage(message: any): void {
    console.log('Received message: ', message);
    // Handle the message
  }

  get messages() {
    return this.socket$.asObservable();
  }
}
