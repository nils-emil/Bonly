import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BonlySharedModule } from 'app/shared/shared.module';
import { BonlyCoreModule } from 'app/core/core.module';
import { BonlyAppRoutingModule } from './app-routing.module';
import { BonlyHomeModule } from './home/home.module';
import { BonlyEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { LoginModalComponent } from 'app/shared/login/login.component';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'app/material.module';

@NgModule({
  imports: [
    MaterialModule,
    BrowserModule,
    CommonModule,
    BonlySharedModule,
    NoopAnimationsModule,
    BonlyCoreModule,
    BonlyHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BonlyEntityModule,
    BonlyAppRoutingModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    LoginModalComponent,
    ActiveMenuDirective,
    FooterComponent,
  ],
  bootstrap: [MainComponent],
  exports: [],
})
export class BonlyAppModule {}
