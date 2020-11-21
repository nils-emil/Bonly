import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';
import { AdvertisementAnswersService } from './advertisement-answers.service';
import { AdvertisementAnswersDeleteDialogComponent } from './advertisement-answers-delete-dialog.component';

@Component({
  selector: 'jhi-advertisement-answers',
  templateUrl: './advertisement-answers.component.html',
})
export class AdvertisementAnswersComponent implements OnInit, OnDestroy {
  advertisementAnswers?: IAdvertisementAnswers[];
  eventSubscriber?: Subscription;

  constructor(
    protected advertisementAnswersService: AdvertisementAnswersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.advertisementAnswersService
      .query()
      .subscribe((res: HttpResponse<IAdvertisementAnswers[]>) => (this.advertisementAnswers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAdvertisementAnswers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdvertisementAnswers): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdvertisementAnswers(): void {
    this.eventSubscriber = this.eventManager.subscribe('advertisementAnswersListModification', () => this.loadAll());
  }

  delete(advertisementAnswers: IAdvertisementAnswers): void {
    const modalRef = this.modalService.open(AdvertisementAnswersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.advertisementAnswers = advertisementAnswers;
  }
}
