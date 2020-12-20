import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import { LoginModalComponent } from 'app/shared/login/login.component';

export const clientRoutes: Route[] = [
  {
    path: 'login',
    component: LoginModalComponent,
  },
  {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar',
  },
];
