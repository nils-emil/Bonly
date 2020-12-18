import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import { LoginModalComponent } from 'app/shared/login/login.component';
import { Authority } from 'app/shared/constants/authority.constants';
import { MoreAccountQuestionsComponent } from 'app/client/more-account-questions/more-account-questions.component';

export const clientRoutes: Route[] = [
  {
    path: 'more-questions',
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
    },
    component: MoreAccountQuestionsComponent,
  },
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
