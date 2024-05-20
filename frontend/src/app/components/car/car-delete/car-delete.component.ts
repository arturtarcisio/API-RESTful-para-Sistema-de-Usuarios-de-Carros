import { Component, OnInit } from '@angular/core';
import { Car } from '../../../model/car';
import { CarService } from '../../../services/car.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-car-delete',
  templateUrl: './car-delete.component.html',
  styleUrl: './car-delete.component.css'
})
export class CarDeleteComponent implements OnInit{
  
  constructor(private carService:  CarService,
    private toast:          ToastrService,
    private router:         Router,
    private activatedRoute: ActivatedRoute,){}
  
  ngOnInit(): void {
    this.car.id = this.activatedRoute.snapshot.paramMap.get('id')
    this.findById();
  }

  car: Car = {
    id:              '',
    carYear:         null,
    licensePlate:    '',
    model:           '',
    color:           ''
  }

  delete(){
    this.carService.deleteById(this.car.id).subscribe(response => {
      this.car = response;
      this.toast.success('Car deleted successfully', 'Delete');
      this.router.navigate(['cars']);
    })
  }

  findById(): void {
    this.carService.findById(this.car.id).subscribe(response => {
      this.car = response;
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
