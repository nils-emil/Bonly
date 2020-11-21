import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrizeRegistration } from 'app/shared/model/prize-registration.model';

@Component({
  selector: 'jhi-prize-registration-detail',
  templateUrl: './prize-registration-detail.component.html',
})
export class PrizeRegistrationDetailComponent implements OnInit {
  prizeRegistration: IPrizeRegistration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prizeRegistration }) => (this.prizeRegistration = prizeRegistration));
  }

  previousState(): void {
    window.history.back();
  }
}
