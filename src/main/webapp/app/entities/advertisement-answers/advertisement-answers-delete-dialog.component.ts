import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';
import { AdvertisementAnswersService } from './advertisement-answers.service';

@Component({
  templateUrl: './advertisement-answers-delete-dialog.component.html',
})
export class AdvertisementAnswersDeleteDialogComponent {
  advertisementAnswers?: IAdvertisementAnswers;

  constructor(
    protected advertisementAnswersService: AdvertisementAnswersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.advertisementAnswersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('advertisementAnswersListModification');
      this.activeModal.close();
    });
  }
}
