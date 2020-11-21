import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrizeRegistration } from 'app/shared/model/prize-registration.model';
import { PrizeRegistrationService } from './prize-registration.service';
import { PrizeRegistrationDeleteDialogComponent } from './prize-registration-delete-dialog.component';

@Component({
  selector: 'jhi-prize-registration',
  templateUrl: './prize-registration.component.html',
})
export class PrizeRegistrationComponent implements OnInit, OnDestroy {
  prizeRegistrations?: IPrizeRegistration[];
  eventSubscriber?: Subscription;

  constructor(
    protected prizeRegistrationService: PrizeRegistrationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.prizeRegistrationService
      .query()
      .subscribe((res: HttpResponse<IPrizeRegistration[]>) => (this.prizeRegistrations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrizeRegistrations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrizeRegistration): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPrizeRegistrations(): void {
    this.eventSubscriber = this.eventManager.subscribe('prizeRegistrationListModification', () => this.loadAll());
  }

  delete(prizeRegistration: IPrizeRegistration): void {
    const modalRef = this.modalService.open(PrizeRegistrationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prizeRegistration = prizeRegistration;
  }
}
