import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';
import { IAdvertisement, Advertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from './advertisement.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';
import { AdvertisementAnswersService } from 'app/entities/advertisement-answers/advertisement-answers.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { BonlyImageCropperModalComponent } from './bonly-image-cropper/bonly-image-cropper-modal.component';

@Component({
  selector: 'jhi-advertisement-update',
  templateUrl: './advertisement-update.component.html',
})
export class AdvertisementUpdateComponent implements OnInit {
  isSaving = false;
  correctanswers: IAdvertisementAnswers[] = [];

  editForm = this.fb.group({
    id: [],
    activeFrom: [null, [Validators.required]],
    activeUntill: [null, [Validators.required]],
    image: [null, [Validators.required]],
    imageContentType: [],
    creditCount: [null, [Validators.required]],
    question: [null, [Validators.required]],
    correctAnswerId: [],
  });
  croppedImage: any = '';

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected advertisementService: AdvertisementService,
    protected modalService: NgbModal,
    protected advertisementAnswersService: AdvertisementAnswersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisement }) => {
      if (!advertisement.id) {
        const today = moment().startOf('day');
        advertisement.activeFrom = today;
        advertisement.activeUntill = today;
      }

      this.updateForm(advertisement);

      this.advertisementAnswersService
        .query({ filter: 'advertisement-is-null' })
        .pipe(
          map((res: HttpResponse<IAdvertisementAnswers[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdvertisementAnswers[]) => {
          if (!advertisement.correctAnswerId) {
            this.correctanswers = resBody;
          } else {
            this.advertisementAnswersService
              .find(advertisement.correctAnswerId)
              .pipe(
                map((subRes: HttpResponse<IAdvertisementAnswers>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAdvertisementAnswers[]) => (this.correctanswers = concatRes));
          }
        });
    });
  }

  updateForm(advertisement: IAdvertisement): void {
    this.editForm.patchValue({
      id: advertisement.id,
      activeFrom: advertisement.activeFrom ? advertisement.activeFrom.format(DATE_TIME_FORMAT) : null,
      activeUntill: advertisement.activeUntill ? advertisement.activeUntill.format(DATE_TIME_FORMAT) : null,
      image: advertisement.image,
      imageContentType: advertisement.imageContentType,
      creditCount: advertisement.creditCount,
      question: advertisement.question,
      correctAnswerId: advertisement.correctAnswerId,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('bonlyApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const advertisement = this.createFromForm();
    if (advertisement.id !== undefined) {
      this.subscribeToSaveResponse(this.advertisementService.update(advertisement));
    } else {
      this.subscribeToSaveResponse(this.advertisementService.create(advertisement));
    }
  }

  private createFromForm(): IAdvertisement {
    return {
      ...new Advertisement(),
      id: this.editForm.get(['id'])!.value,
      activeFrom: this.editForm.get(['activeFrom'])!.value ? moment(this.editForm.get(['activeFrom'])!.value, DATE_TIME_FORMAT) : undefined,
      activeUntill: this.editForm.get(['activeUntill'])!.value
        ? moment(this.editForm.get(['activeUntill'])!.value, DATE_TIME_FORMAT)
        : undefined,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      creditCount: this.editForm.get(['creditCount'])!.value,
      question: this.editForm.get(['question'])!.value,
      correctAnswerId: this.editForm.get(['correctAnswerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvertisement>>): void {
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

  trackById(index: number, item: IAdvertisementAnswers): any {
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
}
