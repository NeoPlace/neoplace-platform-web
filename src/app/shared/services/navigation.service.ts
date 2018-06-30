import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

interface IMenuItem {
  type: string,       // Possible values: link/dropDown/icon/separator/extLink
  name?: string,      // Used as display text for item and title for separator type
  state?: string,     // Router state
  icon?: string,      // Material icon name
  tooltip?: string,   // Tooltip text
  disabled?: boolean, // If true, item will not be appeared in sidenav.
  sub?: IChildItem[], // Dropdown items
  badges?: IBadge[],
  category?: string
}
interface IChildItem {
  type?: string,
  name: string,       // Display text
  state?: string,     // Router state
  icon?: string,
  category?: string,
  sub?: IChildItem[]
}

interface IBadge {
  color: string;      // primary/accent/warn/hex color codes(#fff000)
  value: string;      // Display text
}

@Injectable()
export class NavigationService {
  constructor() { }

  iconMenu: IMenuItem[] = [
    {
      name: 'HOME',
      type: 'link',
      tooltip: 'Home',
      icon: 'home',
      state: 'home'
    },
    {
      name: 'ALL',
      type: 'dropDown',
      tooltip: 'All',
      icon: 'dashboard',
      state: 'shop',
      category: '',
      sub: [
        { name: 'Computer', state: 'shop', category: 'computer' },
        { name: 'Electronic', state: 'shop', category: 'electronic' },
        { name: 'Phone', state: 'shop', category: 'phone' }
      ]
    },
    {
      name: 'COMPUTER',
      type: 'dropDown',
      tooltip: 'Computer, Laptop & Accessories',
      icon: 'computer',
      state: 'shop',
      category: 'computer',
      sub: [
        { name: 'Laptops', state: 'shop', category: 'computer/laptop' },
        { name: 'Office Electronics', state: 'shop', category: 'computer/office' },
        { name: 'Storage Devices', state: 'shop', category: 'computer/storage' },
        { name: 'Networking', state: 'shop', category: 'computer/networking' },
        { name: 'Others', state: 'shop', category: 'computer/other' }
      ]
    },
    {
      name: 'ELECTRONIC',
      type: 'dropDown',
      tooltip: 'Consumer electronic',
      icon: 'settings_input_component',
      state: 'shop',
      category: 'electronic',
      sub: [
        { name: 'Accessories & Parts', state: 'shop', category: 'electronic/accessory' },
        { name: 'Camera & Photo', state: 'shop', category: 'electronic/photo' },
        { name: 'Smart Electronics', state: 'shop', category: 'electronic/smart' },
        { name: 'Home Audio & Video', state: 'shop', category: 'electronic/home' },
        { name: 'Portable Audio & Video', state: 'shop', category: 'electronic/portable' },
        { name: 'Video Games', state: 'shop', category: 'electronic/game' }
      ]
    },
    {
      name: 'PHONE',
      type: 'dropDown',
      tooltip: 'Phones & Accessories',
      icon: 'phone_iphone',
      state: 'shop',
      category: 'phone',
      sub: [
        { name: 'Mobile Phones', state: 'shop', category: 'phone/phone' },
        { name: 'Mobile Phone Parts', state: 'shop', category: 'phone/part' },
        { name: 'Accessories Devices', state: 'shop', category: 'phone/accessory' }
      ]
    },
    {
      name: 'OTHER',
      type: 'dropDown',
      tooltip: 'Others',
      icon: 'view_carousel',
      state: 'shop',
      category: 'other',
      sub: [
        { name: 'Miscellaneous', state: 'shop', category: 'other/miscellaneous' }
      ]
    }
  ]

  // Icon menu TITLE at the very top of navigation.
  // This title will appear if any icon type item is present in menu.
  iconTypeMenuTitle: string = 'Frequently Accessed';
  // sets iconMenu as default;
  menuItems = new BehaviorSubject<IMenuItem[]>(this.iconMenu);
  // navigation component has subscribed to this Observable
  menuItems$ = this.menuItems.asObservable();

  // Customizer component uses this method to change menu.
  // You can remove this method and customizer component.
  // Or you can customize this method to supply different menu for
  // different user type.
  publishNavigationChange(menuType: string) {
    switch (menuType) {
      // case 'separator-menu':
      //   this.menuItems.next(this.separatorMenu);
      //   break;
      case 'icon-menu':
        this.menuItems.next(this.iconMenu);
        break;
      // default:
      //   this.menuItems.next(this.defaultMenu);
    }
  }
}
