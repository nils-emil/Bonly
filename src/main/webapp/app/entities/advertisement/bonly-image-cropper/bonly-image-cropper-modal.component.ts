import { Component } from '@angular/core';
import { AdvertisementService } from '../advertisement.service';
import { ImageCroppedEvent } from 'ngx-image-cropper';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

@Component({
  templateUrl: './bonly-image-cropper-modal.component.html',
  styleUrls: ['./bonly-image-cropper-modal.component.scss'],
})
export class BonlyImageCropperModalComponent {
  imageChangedEvent: any = '';
  croppedImage: any = '';
  widthRatio = 1;
  heightRatio = 1;
  constructor(
    protected advertisementService: AdvertisementService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent): void {
    this.croppedImage = event.base64;
  }
  imageLoaded(): void {}

  cropperReady(): void {
    // cropper ready
  }
  loadImageFailed(): void {
    // show message
  }

  public save(): void {
    this.imageChangedEvent = this.croppedImage;
    this.eventManager.broadcast('croppedImageSelected');
    this.activeModal.dismiss();
  }
}
