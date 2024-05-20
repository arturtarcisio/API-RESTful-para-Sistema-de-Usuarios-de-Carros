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
        const dateISO = response.birthday;

        const date = new Date(dateISO);

        const day = ('0' + date.getDate()).slice(-2);
        const month = ('0' + (date.getMonth() + 1)).slice(-2); 
        const year = date.getFullYear();

        const formattedDate = `${day}/${month}/${year}`;

        this.user = response;
        this.user.birthday = formattedDate;
    });
}

update(): void {
  if (typeof this.user.birthday === 'string') {
    const parts = this.user.birthday.split('/');
    const birthdayDate = new Date(parseInt(parts[2]), parseInt(parts[1]) - 1, parseInt(parts[0]));
    
    const formattedBirthday = `${birthdayDate.getFullYear()}-${('0' + (birthdayDate.getMonth() + 1)).slice(-2)}-${('0' + birthdayDate.getDate()).slice(-2)}`;

    this.user.birthday = formattedBirthday;
  }

  this.userService.update(this.user).subscribe(() => {
    this.toast.success('User updated successfully', 'Update');
    this.router.navigate(['users']);
  }, ex => {
    if (ex.error.errors) {
      ex.error.errors.forEach((element: any) => {
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
