import { Route } from '@angular/router';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: 'admin',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
};
