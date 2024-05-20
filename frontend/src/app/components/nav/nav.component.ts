import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit{
  
  constructor(
    private route: Router,
    private authService: AuthService,
    private toast: ToastrService){}
  
  ngOnInit(): void {
    this.route.navigate(['cars/create'])
  }

  logout() {
    this.route.navigate(['login'])
    this.authService.logout()
    this.toast.info('Logout successful', 'Logout', {timeOut: 7000})
  }

}
