import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(private authSrv: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const token = this.authSrv.getToken();
    if (token) {
      const clonedRequest = request.clone({
        setHeaders: { Authorization: `Bearer ${token}` }
      });
      console.log('Request with token:', clonedRequest);
      return next.handle(clonedRequest);
    } else {
      return next.handle(request);
    }
  }
}
