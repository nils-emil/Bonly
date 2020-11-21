import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdvertisementAnswers, AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';
import { AdvertisementAnswersService } from './advertisement-answers.service';
import { AdvertisementAnswersComponent } from './advertisement-answers.component';
import { AdvertisementAnswersDetailComponent } from './advertisement-answers-detail.component';
import { AdvertisementAnswersUpdateComponent } from './advertisement-answers-update.component';

@Injectable({ providedIn: 'root' })
export class AdvertisementAnswersResolve implements Resolve<IAdvertisementAnswers> {
  constructor(private service: AdvertisementAnswersService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvertisementAnswers> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((advertisementAnswers: HttpResponse<AdvertisementAnswers>) => {
          if (advertisementAnswers.body) {
            return of(advertisementAnswers.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdvertisementAnswers());
  }
}

export const advertisementAnswersRoute: Routes = [
  {
    path: '',
    component: AdvertisementAnswersComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisementAnswers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdvertisementAnswersDetailComponent,
    resolve: {
      advertisementAnswers: AdvertisementAnswersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisementAnswers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdvertisementAnswersUpdateComponent,
    resolve: {
      advertisementAnswers: AdvertisementAnswersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisementAnswers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdvertisementAnswersUpdateComponent,
    resolve: {
      advertisementAnswers: AdvertisementAnswersResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisementAnswers.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
