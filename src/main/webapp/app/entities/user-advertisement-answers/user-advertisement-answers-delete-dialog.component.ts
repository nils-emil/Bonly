import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';
import { UserAdvertisementAnswersService } from './user-advertisement-answers.service';

@Component({
  templateUrl: './user-advertisement-answers-delete-dialog.component.html',
})
export class UserAdvertisementAnswersDeleteDialogComponent {
  userAdvertisementAnswers?: IUserAdvertisementAnswers;

  constructor(
    protected userAdvertisementAnswersService: UserAdvertisementAnswersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userAdvertisementAnswersService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userAdvertisementAnswersListModification');
      this.activeModal.close();
    });
  }
}
