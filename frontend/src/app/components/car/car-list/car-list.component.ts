import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Car } from '../../../model/car';
import { CarService } from '../../../services/car.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrl: './car-list.component.css'
})
export class CarListComponent implements OnInit{

  ELEMENT_DATA: Car[] = []

  displayedColumns: string[] = ['id', 'carYear', 'licensePlate', 'model', 'color', 'actions'];
  dataSource = new MatTableDataSource<Car>(this.ELEMENT_DATA);
  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
      private carService: CarService
  ){}  

  ngOnInit(): void {
    this.findAll()
  }

  findAll(){
    this.carService.findAll().subscribe(response => {
      this.ELEMENT_DATA = response
      this.dataSource = new MatTableDataSource<Car>(this.ELEMENT_DATA)
      this.dataSource.paginator = this.paginator
      console.log(this.ELEMENT_DATA)
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}