import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit{
  
  constructor(private route: Router){}
  
  ngOnInit(): void {
    this.route.navigate(['cars'])
  }

}

export interface PeriodicElement {
  name: string
  position: number
  weight: number
  symbol: string
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position:1, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:2, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:3, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:4, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:5, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:6, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:7, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:8, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:9, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:10, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:11, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:12, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:13, name:"loremipsum", weight:56348.98, symbol:'H'},
  {position:14, name:"loremipsum", weight:56348.98, symbol:'H'},
];
