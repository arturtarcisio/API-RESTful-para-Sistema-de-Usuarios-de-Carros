import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { RouteConfigLoadStart, Router } from '@angular/router';
import { User } from '../../model/user';

@Component({
  selector: 'app-create-acc',
  templateUrl: './create-acc.component.html',
  styleUrl: './create-acc.component.css'
})
export class CreateAccComponent {
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
    if (typeof this.user.birthday === 'string') {
      const parts = this.user.birthday.split('/');
      const birthdayDate = new Date(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0]));
      
      this.user.birthday = birthdayDate;
    }
    this.userService.create(this.user).subscribe(() => {
      this.toast.success('User registered successfully', 'Register');
      this.router.navigate(['login']);
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
