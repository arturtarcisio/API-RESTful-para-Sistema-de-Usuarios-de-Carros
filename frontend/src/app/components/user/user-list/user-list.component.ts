import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { User } from '../../../model/user';
import { UserService } from '../../../services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent implements OnInit{

  ELEMENT_DATA: User[] = []

  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'birthday', 'login', 'phone', 'actions'];
  dataSource = new MatTableDataSource<User>(this.ELEMENT_DATA);
  
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
      private userService: UserService
  ){}  

  ngOnInit(): void {
    this.findAll()
  }

  findAll(){
    this.userService.findAll().subscribe(response => {
      console.log(response)
      this.ELEMENT_DATA = response
      console.log(this.ELEMENT_DATA)
      this.dataSource = new MatTableDataSource<User>(this.ELEMENT_DATA)
      this.dataSource.paginator = this.paginator
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}