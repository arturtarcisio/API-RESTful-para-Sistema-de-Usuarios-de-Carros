import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_CONFIG } from '../config/api.config';
import { Observable } from 'rxjs';
import { Car } from '../model/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) { }

  findAll(): Observable<Car[]>{
    return this.http.get<Car[]>(`${API_CONFIG.baseUrl}/cars`)
  }

  create(car: Car): Observable<Car> {
    return this.http.post<Car>(`${API_CONFIG.baseUrl}/cars`, car)
  }
}
