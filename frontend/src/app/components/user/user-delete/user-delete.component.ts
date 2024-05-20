import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { UserService } from '../../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user-delete',
  templateUrl: './user-delete.component.html',
  styleUrl: './user-delete.component.css'
})
export class UserDeleteComponent implements OnInit{
  
  constructor(private userService:  UserService,
    private toast:          ToastrService,
    private router:         Router,
    private activatedRoute: ActivatedRoute,){}
  
  ngOnInit(): void {
    this.user.id = this.activatedRoute.snapshot.paramMap.get('id')
    this.findById();
  }

  user: User = {
    id:                 '',
    firstName:          '',
    lastName:           '',
    email:              '',
    birthday:           '',
    login:              '',
    password:           '',
    phone:              ''
    //cars: Car[]
  }

  delete(){
    this.userService.deleteById(this.user.id).subscribe(response => {
      this.user = response;
      this.toast.success('User deleted successfully', 'Delete');
      this.router.navigate(['users']);
    })
  }

  findById(): void {
    this.userService.findById(this.user.id).subscribe(response => {
      this.user = response;
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
