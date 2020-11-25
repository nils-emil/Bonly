import { Route } from '@angular/router';

import { NavbarComponent } from './navbar.component';
import { LoginModalComponent } from 'app/shared/login/login.component';
import { ContactComponent } from 'app/client/contact/contact/contact.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Authority } from 'app/shared/constants/authority.constants';
import { AdvertisementChoosingComponent } from 'app/client/advertisement-choosing/advertisement-choosing/advertisement-choosing.component';
import { PrizesComponent } from 'app/client/prizes/prizes.component';
import { MoreAccountQuestionsComponent } from 'app/client/more-account-questions/more-account-questions.component';

export const clientRoutes: Route[] = [
  {
    path: '',
    component: AdvertisementChoosingComponent,
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'prizes',
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
    },
    component: PrizesComponent,
  },
  {
    path: 'more-questions',
    data: {
      authorities: [Authority.USER, Authority.ADMIN],
    },
    component: MoreAccountQuestionsComponent,
  },
  {
    path: 'contact',
    component: ContactComponent,
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
