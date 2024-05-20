import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { FormControl, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-create',
  templateUrl: './user-create.component.html',
  styleUrl: './user-create.component.css'
})
export class UserCreateComponent implements OnInit{
  
  user: User = {
    id:             '',
    firstName:      '',
    lastName:       '',
    email:          '',
    birthday:       '',
    login:          '',
    password:       '',
    phone:          ''
  }

  firstName: FormControl =      new FormControl(null, Validators.minLength(3));
  email: FormControl =          new FormControl(null, Validators.email);
  login: FormControl =          new FormControl(null, Validators.minLength(4));
  password: FormControl =       new FormControl(null, Validators.minLength(8));
  phone: FormControl =          new FormControl(null, Validators.minLength(11));
  
  constructor(
    private userService:  UserService,
    private toast:    ToastrService,
    private router:   Router,
  ){}

  ngOnInit(): void {  }

  validaCampos(): boolean {
    return this.firstName.valid && this.email.valid
     && this.login.valid && this.password.valid && this.phone.valid
  }

  create(): void {
    this.userService.create(this.user).subscribe(() => {
      this.toast.success('User registered successfully', 'Register');
      this.router.navigate(['users']);
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
