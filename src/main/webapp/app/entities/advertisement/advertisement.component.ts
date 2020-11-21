import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdvertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from './advertisement.service';
import { AdvertisementDeleteDialogComponent } from './advertisement-delete-dialog.component';

@Component({
  selector: 'jhi-advertisement',
  templateUrl: './advertisement.component.html',
})
export class AdvertisementComponent implements OnInit, OnDestroy {
  advertisements?: IAdvertisement[];
  eventSubscriber?: Subscription;

  constructor(
    protected advertisementService: AdvertisementService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.advertisementService.query().subscribe((res: HttpResponse<IAdvertisement[]>) => (this.advertisements = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAdvertisements();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdvertisement): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAdvertisements(): void {
    this.eventSubscriber = this.eventManager.subscribe('advertisementListModification', () => this.loadAll());
  }

  delete(advertisement: IAdvertisement): void {
    const modalRef = this.modalService.open(AdvertisementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.advertisement = advertisement;
  }
}
