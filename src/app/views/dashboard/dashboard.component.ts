import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { egretAnimations } from "../../shared/animations/egret-animations";
import PerfectScrollbar from 'perfect-scrollbar';

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  animations: egretAnimations
})
export class DashboardComponent implements OnInit, AfterViewInit {

  public computer: any[] = [
    {
      name: 'Laptops',
      photo: 'assets/images/category/computer.jpg',
      dest: 'shop',
      type: "computer/laptop"
    }, {
      name: 'Office Electronics',
      photo: 'assets/images/category/printer.jpg',
      dest: 'shop',
      type: "computer/office"
    },
    {
      name: 'Storage Devices',
      photo: 'assets/images/category/storage.jpg',
      dest: 'shop',
      type: "computer/storage"
    },
    {
      name: 'Networking',
      photo: 'assets/images/category/networking.jpg',
      dest: 'shop',
      type: "computer/networking"
    }, {
      name: 'Others',
      photo: 'assets/images/category/other.jpg',
      dest: 'shop',
      type: "computer/other"
    }
  ];

  public electronic: any[] = [
    {
      name: 'Accessories & Parts',
      photo: 'assets/images/category/parts.jpg',
      dest: 'shop',
      type: "electronic/accessory"
    }, {
      name: 'Camera & Photo',
      photo: 'assets/images/category/phone.jpg',
      dest: 'shop',
      type: "electronic/photo"
    },
    {
      name: 'Smart Electronics',
      photo: 'assets/images/category/smart.jpg',
      dest: 'shop',
      type: "electronic/smart"
    },
    {
      name: 'Home Audio & Video',
      photo: 'assets/images/category/home.png',
      dest: 'shop',
      type: "electronic/home"
    }, {
      name: 'Portable Audio & Video',
      photo: 'assets/images/category/speaker.jpg',
      dest: 'shop',
      type: "electronic/portable"
    }, {
      name: 'Video Games',
      photo: 'assets/images/category/game.jpg',
      dest: 'shop',
      type: "electronic/game"
    }
  ];

  public phone: any[] = [
    {
      name: 'Mobile Phones',
      photo: 'assets/images/category/phone.jpg',
      dest: 'shop',
      type: "phone/phone"
    }, {
      name: 'Mobile Phone Parts',
      photo: 'assets/images/category/phone_part.jpg',
      dest: 'shop',
      type: "phone/parts"
    },
    {
      name: 'Accessories',
      photo: 'assets/images/category/cover.jpg',
      dest: 'shop',
      type: "phone/accessory"
    }
  ];

  public other: any[] = [
    {
      name: 'Miscellaneous',
      photo: 'assets/images/category/other.jpg',
      dest: 'shop',
      type: "other/miscellaneous"
    }
  ];

  private homePS: PerfectScrollbar;
  public type: string;
  public category: any;

  constructor(private router: Router, private route: ActivatedRoute) { }
  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.type = params['type'];
      if(this.type == 'computer') {
        this.category = this.computer;
      }
      if(this.type == 'electronic') {
        this.category = this.electronic;
      }
      if(this.type == 'phone') {
        this.category = this.phone;
      }
      if(this.type == 'other') {
        this.category = this.other;
      }
    });
  }

  ngAfterViewInit() {
    setTimeout(() => {
      this.homePS = new PerfectScrollbar('.scrollable')
    });
  }

  goToDashboard(v) {
    this.router.navigateByUrl('/shop?type=' + v.type);
  }
}
