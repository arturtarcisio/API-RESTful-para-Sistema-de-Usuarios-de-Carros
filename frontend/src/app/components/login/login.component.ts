import { Component, OnInit } from '@angular/core';
import { LoginRequest } from '../../model/loginRequest';
import { FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  
  loginRequest: LoginRequest = {
    login: '',
    password: ''
  }

  login = new FormControl(null, Validators.required)
  password = new FormControl(null, Validators.minLength(3))
  
  constructor(private toast: ToastrService){}
  
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  logar() {
    this.toast.error('Invalid user or password', 'Login')
    this.loginRequest.password = ''
  }

  validaCampos(): boolean {
    if(this.login.valid && this.password.valid){
      return true
    } else {
      return false
    }

  }

}
