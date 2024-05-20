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
    private carService:  CarService,
    private toast:    ToastrService,
    private router:   Router,
  ){}

  ngOnInit(): void {  }

  validaCampos(): boolean {
    return this.carYear.valid && this.licensePlate.valid
     && this.model.valid && this.color.valid
  }

  create(): void {
    this.carService.create(this.car).subscribe(() => {
      this.toast.success('Car registered successfully', 'Register');
      this.router.navigate(['cars']);
    }, ex => {
      if (ex.error.errors) {
        ex.error.errors.forEach(element => {
          this.toast.error(this.extractMessage(element), 'Error');
        });
      } else {
        this.toast.error(ex.error.message, 'Error');
      }
    });
  }
  
  extractMessage(element: any): string {
    const match = element.match(/Message: (.*) errorCode/);
    return match ? match[1] : 'An unknown error occurred';
  }
  

}
