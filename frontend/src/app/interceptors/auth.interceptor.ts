import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private excludedRoutes: string[] = [
    `${API_CONFIG.baseUrl}/users/**`,
    `${API_CONFIG.baseUrl}/signin`
  ];

  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const isExcludedRoute = this.excludedRoutes.some(route => req.url.includes(route));

    if (isExcludedRoute) {
      return next.handle(req);
    } else {
      const token = localStorage.getItem('token');
      if (token) {
        const cloneReq = req.clone({
          headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
        return next.handle(cloneReq);
      } else {
        return next.handle(req);
      }
    }
  }
}

export const AuthInterceptorProvider = [
  {
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }
];
