import { Component, OnInit } from '@angular/core';
import { PrizeService } from '../../entities/prize/prize.service';
import { IPrize } from 'app/shared/model/prize.model';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { HttpResponse } from '@angular/common/http';
import { JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PrizeRegistrationModalComponent } from 'app/client/prize-registration-modal/prize-registration-modal.component';
import { AccountService } from 'app/core/auth/account.service';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  selector: 'jhi-prizes',
  templateUrl: './prizes.component.html',
  styleUrls: ['./prizes.component.scss'],
})
export class PrizesComponent implements OnInit {
  prizes: IPrize[];
  prizesExist = false;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  availableCreditCount = 0;

  constructor(
    protected prizeService: PrizeService,
    protected parseLinks: JhiParseLinks,
    private eventManager: JhiEventManager,
    protected accountSercice: AccountService,
    protected modalService: NgbModal
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

  ngOnInit(): void {
    this.loadAll();
    // TODO remove memory leak
    this.eventManager.subscribe('userListModification', () => this.loadAll());

    this.accountSercice.getAccount().subscribe(e => {
      this.availableCreditCount = e.creditCount;
    });
  }

  loadAll(): void {
    this.prizeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IPrize[]>) => {
        this.prizesExist = !!res.body;
        this.prizes = res.body || [];
      });
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  openPrizeRegistration(prize: any): void {
    const modalRef = this.modalService.open(PrizeRegistrationModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prize = prize;
  }
}
