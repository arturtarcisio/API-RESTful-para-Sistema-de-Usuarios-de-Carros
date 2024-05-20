import { Component, OnInit } from '@angular/core';
import { User } from '../../../model/user';
import { FormControl, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrl: './user-update.component.css'
})
export class UserUpdateComponent implements OnInit{  

  user: User = {
    id:             '',
    firstName:      '',
    lastName:       '',
    email:          '',
    birthday:       null,
    login:          '',
    password:       '',
    phone:          ''
  }

  firstName: FormControl =      new FormControl(null, Validators.minLength(3));
  email: FormControl =          new FormControl(null, Validators.email);
  login: FormControl =          new FormControl(null, Validators.minLength(4));
  password: FormControl =       new FormControl(null, Validators.minLength(8));
  phone: FormControl =          new FormControl(null, Validators.minLength(11));

  constructor(private userService:  UserService,
    private toast:          ToastrService,
    private router:         Router,
    private activatedRoute: ActivatedRoute,){}
  
  ngOnInit(): void {
    this.user.id = this.activatedRoute.snapshot.paramMap.get('id')
    this.findById();
  }

  validaCampos(): boolean {
    return this.firstName.valid && this.email.valid
     && this.login.valid && this.password.valid && this.phone.valid
  }

  findById(): void {
    this.userService.findById(this.user.id).subscribe(response => {
      this.user = response;
    })
  }

  update(): void {
    this.userService.update(this.user).subscribe(() => {
      this.toast.success('User updated successfully', 'Update');
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
