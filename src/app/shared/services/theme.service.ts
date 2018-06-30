import { Injectable, Inject, Renderer2 } from '@angular/core';
import { DOCUMENT } from '@angular/common';

interface ITheme {
  name: string,
  baseColor?: string,
  isActive?: boolean
}

@Injectable()
export class ThemeService {
  public themes :ITheme[]  = [{
    "name": "indigo",
    "baseColor": "#3f51b5",
    "isActive": true
  }];
  public activatedTheme: ITheme;

  constructor(
    @Inject(DOCUMENT) private document: Document
  ) {}

  // Invoked in AppComponent and apply 'activatedTheme' on startup
  applyMatTheme(r: Renderer2) {
    this.activatedTheme = this.themes[0];


    this.changeTheme(r, this.activatedTheme)
  }

  changeTheme(r: Renderer2, theme:ITheme) {
    r.removeClass(this.document.body, this.activatedTheme.name);
    r.addClass(this.document.body, theme.name);
  }

}
