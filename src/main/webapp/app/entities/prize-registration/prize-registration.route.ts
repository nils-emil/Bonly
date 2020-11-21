import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrizeRegistration, PrizeRegistration } from 'app/shared/model/prize-registration.model';
import { PrizeRegistrationService } from './prize-registration.service';
import { PrizeRegistrationComponent } from './prize-registration.component';
import { PrizeRegistrationDetailComponent } from './prize-registration-detail.component';
import { PrizeRegistrationUpdateComponent } from './prize-registration-update.component';

@Injectable({ providedIn: 'root' })
export class PrizeRegistrationResolve implements Resolve<IPrizeRegistration> {
  constructor(private service: PrizeRegistrationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrizeRegistration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((prizeRegistration: HttpResponse<PrizeRegistration>) => {
          if (prizeRegistration.body) {
            return of(prizeRegistration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PrizeRegistration());
  }
}

export const prizeRegistrationRoute: Routes = [
  {
    path: '',
    component: PrizeRegistrationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.prizeRegistration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrizeRegistrationDetailComponent,
    resolve: {
      prizeRegistration: PrizeRegistrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.prizeRegistration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrizeRegistrationUpdateComponent,
    resolve: {
      prizeRegistration: PrizeRegistrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.prizeRegistration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrizeRegistrationUpdateComponent,
    resolve: {
      prizeRegistration: PrizeRegistrationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.prizeRegistration.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
