import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BonlySharedModule } from 'app/shared/shared.module';
import { PrizeRegistrationComponent } from './prize-registration.component';
import { PrizeRegistrationDetailComponent } from './prize-registration-detail.component';
import { PrizeRegistrationUpdateComponent } from './prize-registration-update.component';
import { PrizeRegistrationDeleteDialogComponent } from './prize-registration-delete-dialog.component';
import { prizeRegistrationRoute } from './prize-registration.route';

@NgModule({
  imports: [BonlySharedModule, RouterModule.forChild(prizeRegistrationRoute)],
  declarations: [
    PrizeRegistrationComponent,
    PrizeRegistrationDetailComponent,
    PrizeRegistrationUpdateComponent,
    PrizeRegistrationDeleteDialogComponent,
  ],
  entryComponents: [PrizeRegistrationDeleteDialogComponent],
})
export class BonlyPrizeRegistrationModule {}
