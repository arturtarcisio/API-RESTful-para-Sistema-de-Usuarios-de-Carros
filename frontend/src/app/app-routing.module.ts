import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { combineLatest } from 'rxjs';
import { HomeComponent } from './components/home/home.component';
import { CarListComponent } from './components/car/car-list/car-list.component';
import { LoginComponent } from './components/login/login.component';
import { AboutComponent } from './components/about/about.component';

const routes: Routes = [
  { path:'login', component: LoginComponent},
  {
    path:'', component: NavComponent, children:[
      { path:'home', component: HomeComponent },
      { path:'cars', component: CarListComponent },
      {path: 'about', component: AboutComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
