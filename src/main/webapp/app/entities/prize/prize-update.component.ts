import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrize, Prize } from 'app/shared/model/prize.model';
import { PrizeService } from './prize.service';
import { IPerson } from 'app/shared/model/person.model';
import { AlertError } from '../../shared/alert/alert-error.model';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';
import { BonlyImageCropperModalComponent } from '../advertisement/bonly-image-cropper/bonly-image-cropper-modal.component';
import { UserService } from '../../core/user/user.service';
import { PersonService } from '../person/person.service';
import { SERVER_API_URL } from '../../app.constants';

@Component({
  selector: 'jhi-prize-update',
  templateUrl: './prize-update.component.html',
})
export class PrizeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    registationStops: [],
    creditsRequired: [],
    image: [null, [Validators.required]],
    imageId: [null],
    type: [null, [Validators.required]],
    title: [null, [Validators.required]],
    imageContentType: [],
    winner: [],
  });
  public imageDownload = SERVER_API_URL + 'api/image/';

  constructor(
    protected prizeService: PrizeService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    protected eventManager: JhiEventManager,
    protected dataUtils: JhiDataUtils,
    protected userService: UserService,
    protected modalService: NgbModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prize }) => {
      if (!prize.id) {
        const today = moment().startOf('day');
        prize.registationStops = today;
        console.log(prize);
      }
      console.log(prize);
      this.updateForm(prize);
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('bonlyApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  updateForm(prize: IPrize): void {
    this.editForm.patchValue({
      id: prize.id,
      registationStops: prize.registationStops ? prize.registationStops.format(DATE_TIME_FORMAT) : null,
      creditsRequired: prize.creditsRequired,
      image: prize.image,
      imageId: prize.imageId,
      type: prize.type,
      imageContentType: prize.imageContentType,
      winner: prize.winner,
      title: prize.title,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prize = this.createFromForm();
    if (prize.id !== undefined) {
      this.subscribeToSaveResponse(this.prizeService.update(prize));
    } else {
      this.subscribeToSaveResponse(this.prizeService.create(prize));
    }
  }

  private createFromForm(): IPrize {
    return {
      ...new Prize(),
      id: this.editForm.get(['id'])!.value,
      registationStops: this.editForm.get(['registationStops'])!.value
        ? moment(this.editForm.get(['registationStops'])!.value, DATE_TIME_FORMAT)
        : undefined,
      image: this.editForm.get(['image'])!.value,
      imageId: this.editForm.get(['imageId'])!.value,
      type: this.editForm.get(['type'])!.value,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      creditsRequired: this.editForm.get(['creditsRequired'])!.value,
      winner: this.editForm.get(['winner'])!.value,
      title: this.editForm.get(['title'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrize>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPerson): any {
    return item.id;
  }

  openImageModal(): void {
    const modalRef = this.modalService.open(BonlyImageCropperModalComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.croppedImage = this.editForm.get(['image'])!.value;
    modalRef.componentInstance.widthRatio = 4;
    modalRef.componentInstance.heightRatio = 4;
    this.eventManager.subscribe('croppedImageSelected', () => {
      this.editForm.patchValue({ image: modalRef.componentInstance.croppedImage });
    });
  }

  getImageSource(): string {
    return this.editForm.get('image')!.value ? this.editForm.get('image')!.value : this.imageDownload + this.editForm.get('imageId')!.value;
  }
}
