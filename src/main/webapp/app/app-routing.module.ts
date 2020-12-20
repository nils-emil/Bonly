import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute } from './layouts/error/error.route';
import { clientRoutes } from './layouts/navbar/clientRoutes';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';
import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { BonlySharedLibsModule } from 'app/shared/shared-libs.module';

const LAYOUT_ROUTES = [...clientRoutes, ...errorRoute];

@NgModule({
  imports: [
    BonlySharedLibsModule,
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          data: {
            authorities: [Authority.ADMIN],
          },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        ...LAYOUT_ROUTES,
        {
          path: '',
          redirectTo: 'login',
          pathMatch: 'full',
        },
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    ),
  ],
  exports: [RouterModule],
  declarations: [],
})
export class BonlyAppRoutingModule {}
