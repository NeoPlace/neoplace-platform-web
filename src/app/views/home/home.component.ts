import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppLoaderService } from '../../shared/services/app-loader/app-loader.service';
import PerfectScrollbar from 'perfect-scrollbar';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy, AfterViewInit {
  /****** Only for demo) **********/
  public versions: any[] = [
    {
      name: 'Computer, Laptop & Accessories',
      photo: 'assets/images/category/computer.jpg',
      dest: 'dashboard',
      type: 'computer'
    }, {
      name: 'Consumer Electronics',
      photo: 'assets/images/category/electronic.jpg',
      dest: 'dashboard',
      type: 'electronic'
    },
    {
      name: 'Phones & Accessories',
      photo: 'assets/images/category/phone.jpg',
      dest: 'dashboard',
      type: 'phone'
    },
    {
      name: 'Others',
      photo: 'assets/images/category/other.jpg',
      dest: 'dashboard',
      type: 'other'
    }
  ]

  // private homePS: PerfectScrollbar;
  constructor(
    private router: Router,
    private loader: AppLoaderService
  ) { }

  ngOnInit() {
  }

  ngOnDestroy() {
    // if (this.homePS) this.homePS.destroy();
    this.loader.close();
  }
  ngAfterViewInit() {
    // setTimeout(() => {
    //   this.homePS = new PerfectScrollbar('.scrollable')
    // });
  }

  /****** Remove this (Only for demo) **********/
  goToDashboard(v) {
    // let origin = window.location.origin;
    //
    // if(v.theme) {
    //   return window.location.href = `${origin}/${v.dest}/?type=${v.type}&theme=${v.theme}`;
    // }
    // window.location.href = `${origin}/${v.dest}/?type=${v.type}`;

    this.router.navigateByUrl("/" + v.dest + "?type=" + v.type);
  }
  goToMainDash() {
    this.loader.open();
    this.router.navigateByUrl('/dashboard')
  }
}
