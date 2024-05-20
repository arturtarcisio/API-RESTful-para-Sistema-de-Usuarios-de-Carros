import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../config/api.config';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<User[]>{
    return this.http.get<User[]>(`${API_CONFIG.baseUrl}/users`)
  }

  findById(id: any): Observable<User>{
    return this.http.get<User>(`${API_CONFIG.baseUrl}/users/${id}`)
  }

  create(car: User): Observable<User> {
    return this.http.post<User>(`${API_CONFIG.baseUrl}/users`, car)
  }

  update(car: User): Observable<User> {
    return this.http.put<User>(`${API_CONFIG.baseUrl}/users/${car.id}`, car)
  }

  deleteById(id: any): Observable<User>{
    return this.http.delete<User>(`${API_CONFIG.baseUrl}/users/${id}`)
  }
}
