import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private storageService: StorageService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (request.urlWithParams.indexOf('localhost:8080') > -1) {
      const accessToken = this.storageService.read();
      request = request.clone({
        headers: accessToken ? request.headers.set('accessToken', accessToken) : request.headers,
        // to allow getting header 'set-Coookie' and setting header 'Cookie'
        withCredentials: true
      });
    }
    return next.handle(request);
  }
}
