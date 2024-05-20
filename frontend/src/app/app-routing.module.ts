import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { CarListComponent } from './components/car/car-list/car-list.component';
import { LoginComponent } from './components/login/login.component';
import { AboutComponent } from './components/about/about.component';
import { AuthGuard } from './auth/auth.guard';
import { CarCreateComponent } from './components/car/car-create/car-create.component';
import { CarUpdateComponent } from './components/car/car-update/car-update.component';

const routes: Routes = [
  { path:'login', component: LoginComponent},
  {
    path:'', component: NavComponent, canActivate:[AuthGuard], children:[
      { path:'home', component: HomeComponent },
      
      { path:'cars', component: CarListComponent },
      { path:'cars/create', component: CarCreateComponent },
      { path:'cars/update/:id', component: CarUpdateComponent },    
      
      {path: 'about', component: AboutComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
