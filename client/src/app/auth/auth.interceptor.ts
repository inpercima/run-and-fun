import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { ErrorDialogService } from '../core/error-dialog.service';
import { StorageService } from '../core/storage.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private errorService: ErrorDialogService, private storageService: StorageService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (request.urlWithParams.indexOf('localhost:8080') > -1) {
      const accessToken = this.storageService.read();
      request = request.clone({
        headers: accessToken ? request.headers.set('accessToken', accessToken) : request.headers,
        // to allow getting header 'set-Coookie' and setting header 'Cookie'
        withCredentials: true
      });
    }
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        this.errorService.openDialog();
        return throwError(error);
      }));
  }
}
