import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.BonlyPersonModule),
      },
      {
        path: 'advertisement',
        loadChildren: () => import('./advertisement/advertisement.module').then(m => m.BonlyAdvertisementModule),
      },
      {
        path: 'advertisement-answers',
        loadChildren: () => import('./advertisement-answers/advertisement-answers.module').then(m => m.BonlyAdvertisementAnswersModule),
      },
      {
        path: 'user-advertisement-answers',
        loadChildren: () =>
          import('./user-advertisement-answers/user-advertisement-answers.module').then(m => m.BonlyUserAdvertisementAnswersModule),
      },
      {
        path: 'prize',
        loadChildren: () => import('./prize/prize.module').then(m => m.BonlyPrizeModule),
      },
      {
        path: 'prize-registration',
        loadChildren: () => import('./prize-registration/prize-registration.module').then(m => m.BonlyPrizeRegistrationModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BonlyEntityModule {}
