import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';
import { UserAdvertisementAnswersService } from './user-advertisement-answers.service';
import { UserAdvertisementAnswersDeleteDialogComponent } from './user-advertisement-answers-delete-dialog.component';

@Component({
  selector: 'jhi-user-advertisement-answers',
  templateUrl: './user-advertisement-answers.component.html',
})
export class UserAdvertisementAnswersComponent implements OnInit, OnDestroy {
  userAdvertisementAnswers?: IUserAdvertisementAnswers[];
  eventSubscriber?: Subscription;

  constructor(
    protected userAdvertisementAnswersService: UserAdvertisementAnswersService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userAdvertisementAnswersService
      .query()
      .subscribe((res: HttpResponse<IUserAdvertisementAnswers[]>) => (this.userAdvertisementAnswers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserAdvertisementAnswers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserAdvertisementAnswers): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserAdvertisementAnswers(): void {
    this.eventSubscriber = this.eventManager.subscribe('userAdvertisementAnswersListModification', () => this.loadAll());
  }

  delete(userAdvertisementAnswers: IUserAdvertisementAnswers): void {
    const modalRef = this.modalService.open(UserAdvertisementAnswersDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userAdvertisementAnswers = userAdvertisementAnswers;
  }
}
