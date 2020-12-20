import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'admin/advertisement',
        loadChildren: () => import('./advertisement/advertisement.module').then(m => m.BonlyAdvertisementModule),
      },
      {
        path: 'admin/advertisement-answers',
        loadChildren: () => import('./advertisement-answers/advertisement-answers.module').then(m => m.BonlyAdvertisementAnswersModule),
      },
      {
        path: 'admin/user-advertisement-answers',
        loadChildren: () =>
          import('./user-advertisement-answers/user-advertisement-answers.module').then(m => m.BonlyUserAdvertisementAnswersModule),
      },
      {
        path: 'admin/prize',
        loadChildren: () => import('./prize/prize.module').then(m => m.BonlyPrizeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
  declarations: [],
})
export class BonlyEntityModule {}
