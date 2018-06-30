import {Component, Input, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {NavigationService} from "../../../shared/services/navigation.service";
import {Subscription} from 'rxjs/Subscription';
import {ThemeService} from '../../../shared/services/theme.service';
import {LayoutService} from '../../services/layout.service';
import {AppConfirmService} from "../../services/app-confirm/app-confirm.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header-top',
  templateUrl: './header-top.component.html'
})
export class HeaderTopComponent implements OnInit, OnDestroy {

  title = 'You must be logged';
  text = "You are going to log in with Metamask";


  public cartData: any;
  layoutConf: any;
  menuItems:any;
  menuItemSub: Subscription;
  themes: any[] = [];

  @Input() notificPanel;
  constructor(
    private layout: LayoutService,
    private navService: NavigationService,
    public themeService: ThemeService,
    private renderer: Renderer2,
    private confirmService: AppConfirmService,
    private router: Router
  ) { }

  ngOnInit() {
    this.layoutConf = this.layout.layoutConf;
    this.themes = this.themeService.themes;
    this.menuItemSub = this.navService.menuItems$
    .subscribe(res => {
      res = res.filter(item => item.type !== 'icon' && item.type !== 'separator');
      let limit = 6;
      let mainItems:any[] = res.slice(0, limit)
      if(res.length <= limit) {
        return this.menuItems = mainItems
      }
      let subItems:any[] = res.slice(limit, res.length - 1)
      mainItems.push({
        name: 'More',
        type: 'dropDown',
        tooltip: 'More',
        icon: 'more_horiz',
        sub: subItems
      })
      this.menuItems = mainItems;
    });
  }
  ngOnDestroy() {
    this.menuItemSub.unsubscribe()
  }

  changeTheme(theme) {
    this.themeService.changeTheme(this.renderer, theme);
  }
  toggleNotific() {
    this.notificPanel.toggle();
  }
  toggleSidenav() {
    if(this.layoutConf.sidebarStyle === 'closed') {
      return this.layout.publishLayoutChange({
        sidebarStyle: 'full'
      })
    }
    this.layout.publishLayoutChange({
      sidebarStyle: 'closed'
    })
  }

  connect(url: string) {

  }

  getStaticBasket() {
    return 0;
  }
}
