import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BonlySharedModule } from 'app/shared/shared.module';
import { UserAdvertisementAnswersComponent } from './user-advertisement-answers.component';
import { UserAdvertisementAnswersDetailComponent } from './user-advertisement-answers-detail.component';
import { UserAdvertisementAnswersUpdateComponent } from './user-advertisement-answers-update.component';
import { UserAdvertisementAnswersDeleteDialogComponent } from './user-advertisement-answers-delete-dialog.component';
import { userAdvertisementAnswersRoute } from './user-advertisement-answers.route';

@NgModule({
  imports: [BonlySharedModule, RouterModule.forChild(userAdvertisementAnswersRoute)],
  declarations: [
    UserAdvertisementAnswersComponent,
    UserAdvertisementAnswersDetailComponent,
    UserAdvertisementAnswersUpdateComponent,
    UserAdvertisementAnswersDeleteDialogComponent,
  ],
  entryComponents: [UserAdvertisementAnswersDeleteDialogComponent],
})
export class BonlyUserAdvertisementAnswersModule {}
