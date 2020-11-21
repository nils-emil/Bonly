import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdvertisement, Advertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from './advertisement.service';
import { AdvertisementComponent } from './advertisement.component';
import { AdvertisementDetailComponent } from './advertisement-detail.component';
import { AdvertisementUpdateComponent } from './advertisement-update.component';

@Injectable({ providedIn: 'root' })
export class AdvertisementResolve implements Resolve<IAdvertisement> {
  constructor(private service: AdvertisementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdvertisement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((advertisement: HttpResponse<Advertisement>) => {
          if (advertisement.body) {
            return of(advertisement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Advertisement());
  }
}

export const advertisementRoute: Routes = [
  {
    path: '',
    component: AdvertisementComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdvertisementDetailComponent,
    resolve: {
      advertisement: AdvertisementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdvertisementUpdateComponent,
    resolve: {
      advertisement: AdvertisementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdvertisementUpdateComponent,
    resolve: {
      advertisement: AdvertisementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bonlyApp.advertisement.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
