import { Component, OnInit } from '@angular/core';
import { Car } from '../../../model/car';
import { FormControl, Validators } from '@angular/forms';
import { CarService } from '../../../services/car.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car-create',
  templateUrl: './car-create.component.html',
  styleUrl: './car-create.component.css'
})
export class CarCreateComponent implements OnInit{
  
  car: Car = {
    id:              '',
    carYear:         null,
    licensePlate:    '',
    model:           '',
    color:           ''
  }

  carYear: FormControl =  new FormControl(null, Validators.minLength(4));
  licensePlate: FormControl =       new FormControl(null, Validators.required);
  model: FormControl =        new FormControl(null, Validators.required);
  color: FormControl = new FormControl(null, Validators.minLength(3));
  
  constructor(
    private service:  CarService,
    private toast:    ToastrService,
    private router:   Router,
  ){}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

}
