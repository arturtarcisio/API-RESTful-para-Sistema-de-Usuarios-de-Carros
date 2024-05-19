import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Car } from '../../../model/car';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrl: './car-list.component.css'
})
export class CarListComponent implements OnInit{

  ELEMENT_DATA: Car[] = [
    {
      id: 1,
      year: '19/05/2024',
      licensePlate: 'AAA-A3AA',
      model: 'GOL',
      color: 'BLUE'
    }
  ]

  displayedColumns: string[] = ['id', 'year', 'licensePlate', 'model', 'color', 'actions'];
  dataSource = new MatTableDataSource<Car>(this.ELEMENT_DATA);
  
  constructor(){}
  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

}