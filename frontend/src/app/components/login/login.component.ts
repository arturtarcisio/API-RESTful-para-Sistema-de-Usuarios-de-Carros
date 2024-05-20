import { Component, OnInit } from '@angular/core';
import { LoginRequest } from '../../model/loginRequest';
import { FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

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
  
  constructor(
    private toast: ToastrService,
    private authService: AuthService,
    private router: Router){}
  
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  logar() {
    this.authService.authenticate(this.loginRequest).subscribe(response => {
      const responseBody = JSON.parse(response.body);
      const accessToken = responseBody.acessToken;
      if (accessToken) {
        this.authService.successFulLogin(accessToken);
        this.router.navigate(['home']);
      } else {
        this.toast.error('No access token received.');
      }
    }, () => {
      this.toast.error('Invalid user or password.');
    });
  }

  validaCampos(): boolean {
    return this.login.valid && this.password.valid

  }

}
