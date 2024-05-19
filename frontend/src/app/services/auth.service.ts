import { Injectable } from '@angular/core';
import { LoginRequest } from '../model/loginRequest';
import { HttpClient } from '@angular/common/http';
import { API_CONFIG } from '../config/api.config';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtService: JwtHelperService = new JwtHelperService()

  constructor(private http: HttpClient) { }

  authenticate(loginRequest: LoginRequest) {
    return this.http.post(`${API_CONFIG.baseUrl}/signin`, loginRequest, {
      observe: 'response',
      responseType: 'text'
    })
  }

  successFulLogin(acessToken: string) {
    localStorage.setItem('token', acessToken)
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        const decodedToken = this.jwtService.decodeToken(token);
        return !this.jwtService.isTokenExpired(token);
      } catch (e) {
        console.error('Token is not valid', e);
        return false;
      }
    }
    return false;
  }
}
