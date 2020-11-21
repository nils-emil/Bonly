import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrizeRegistration } from 'app/shared/model/prize-registration.model';
import { PrizeRegistrationService } from './prize-registration.service';

@Component({
  templateUrl: './prize-registration-delete-dialog.component.html',
})
export class PrizeRegistrationDeleteDialogComponent {
  prizeRegistration?: IPrizeRegistration;

  constructor(
    protected prizeRegistrationService: PrizeRegistrationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.prizeRegistrationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('prizeRegistrationListModification');
      this.activeModal.close();
    });
  }
}
