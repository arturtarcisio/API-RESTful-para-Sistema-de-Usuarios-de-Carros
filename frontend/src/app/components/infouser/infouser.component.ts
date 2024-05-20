import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { UserInfo } from '../../model/userInfo';
import { AuthService } from '../../services/auth.service';
import { Car } from '../../model/car';

@Component({
  selector: 'app-infouser',
  templateUrl: './infouser.component.html',
  styleUrls: ['./infouser.component.css']
})
export class InfouserComponent implements OnInit {

  userInfo: UserInfo = {
    firstName: '',
    lastName: '',
    email: '',
    birthday: null,
    login: '',
    phone: '',
    cars: [],
    createAt: null,
    lastLogin: null
  };

  displayedUserColumns: string[] = ['firstName', 'lastName', 'email', 'birthday', 'login', 'phone', 'createdAt', 'lastLogin'];
  displayedCarColumns: string[] = ['id', 'carYear', 'licensePlate', 'model', 'color'];

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.infoAboutUser();
  }

  infoAboutUser(): void {
    this.authService.infoAboutUser().subscribe(response => {
      this.userInfo = response;
    });
  }
}
