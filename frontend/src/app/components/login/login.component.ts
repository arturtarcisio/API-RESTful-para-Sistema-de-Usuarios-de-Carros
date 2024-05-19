import { Component, OnInit } from '@angular/core';
import { LoginRequest } from '../../model/loginRequest';
import { FormControl, Validators } from '@angular/forms';

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
  
  constructor(){}
  
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  validaCampos(): boolean {
    if(this.login.valid && this.password.valid){
      return true
    } else {
      return false
    }

  }

}
