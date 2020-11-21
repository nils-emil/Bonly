import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdvertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from './advertisement.service';

@Component({
  templateUrl: './advertisement-delete-dialog.component.html',
})
export class AdvertisementDeleteDialogComponent {
  advertisement?: IAdvertisement;

  constructor(
    protected advertisementService: AdvertisementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.advertisementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('advertisementListModification');
      this.activeModal.close();
    });
  }
}
