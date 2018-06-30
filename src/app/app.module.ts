import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {BrowserModule, HAMMER_GESTURE_CONFIG} from '@angular/platform-browser';
import {HttpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {GestureConfig} from '@angular/material';

import {rootRouterConfig} from './app.routing';
import {SharedModule} from './shared/shared.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    HttpClientModule,
    SharedModule,
    RouterModule.forRoot(rootRouterConfig, { useHash: false }),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [AppComponent],
  providers: [
    // ANGULAR MATERIAL SLIDER FIX
    { provide: HAMMER_GESTURE_CONFIG, useClass: GestureConfig }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
