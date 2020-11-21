import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BonlySharedModule } from 'app/shared/shared.module';
import { AdvertisementAnswersComponent } from './advertisement-answers.component';
import { AdvertisementAnswersDetailComponent } from './advertisement-answers-detail.component';
import { AdvertisementAnswersUpdateComponent } from './advertisement-answers-update.component';
import { AdvertisementAnswersDeleteDialogComponent } from './advertisement-answers-delete-dialog.component';
import { advertisementAnswersRoute } from './advertisement-answers.route';

@NgModule({
  imports: [BonlySharedModule, RouterModule.forChild(advertisementAnswersRoute)],
  declarations: [
    AdvertisementAnswersComponent,
    AdvertisementAnswersDetailComponent,
    AdvertisementAnswersUpdateComponent,
    AdvertisementAnswersDeleteDialogComponent,
  ],
  entryComponents: [AdvertisementAnswersDeleteDialogComponent],
})
export class BonlyAdvertisementAnswersModule {}
