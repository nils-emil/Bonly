import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrize } from 'app/shared/model/prize.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { PrizeService } from './prize.service';
import { PrizeDeleteDialogComponent } from './prize-delete-dialog.component';

@Component({
  selector: 'jhi-prize',
  templateUrl: './prize.component.html',
})
export class PrizeComponent implements OnInit, OnDestroy {
  prizes: IPrize[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected prizeService: PrizeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.prizes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.prizeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IPrize[]>) => this.paginatePrizes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.prizes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrizes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrize): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPrizes(): void {
    this.eventSubscriber = this.eventManager.subscribe('prizeListModification', () => this.reset());
  }

  delete(prize: IPrize): void {
    const modalRef = this.modalService.open(PrizeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prize = prize;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePrizes(data: IPrize[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.prizes.push(data[i]);
      }
    }
  }
}
