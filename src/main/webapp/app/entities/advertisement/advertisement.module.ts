import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BonlySharedModule } from 'app/shared/shared.module';
import { AdvertisementComponent } from './advertisement.component';
import { AdvertisementDetailComponent } from './advertisement-detail.component';
import { AdvertisementUpdateComponent } from './advertisement-update.component';
import { AdvertisementDeleteDialogComponent } from './advertisement-delete-dialog.component';
import { advertisementRoute } from './advertisement.route';

@NgModule({
  imports: [BonlySharedModule, RouterModule.forChild(advertisementRoute)],
  declarations: [AdvertisementComponent, AdvertisementDetailComponent, AdvertisementUpdateComponent, AdvertisementDeleteDialogComponent],
  entryComponents: [AdvertisementDeleteDialogComponent],
})
export class BonlyAdvertisementModule {}
