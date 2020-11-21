import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IAdvertisementAnswers, AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';
import { AdvertisementAnswersService } from './advertisement-answers.service';
import { IAdvertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';

@Component({
  selector: 'jhi-advertisement-answers-update',
  templateUrl: './advertisement-answers-update.component.html',
})
export class AdvertisementAnswersUpdateComponent implements OnInit {
  isSaving = false;
  advertisements: IAdvertisement[] = [];

  editForm = this.fb.group({
    id: [],
    answer: [],
    city: [],
    stateProvince: [],
    advertisementId: [],
  });

  constructor(
    protected advertisementAnswersService: AdvertisementAnswersService,
    protected advertisementService: AdvertisementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisementAnswers }) => {
      this.updateForm(advertisementAnswers);

      this.advertisementService
        .query({ filter: 'advertisementanswers-is-null' })
        .pipe(
          map((res: HttpResponse<IAdvertisement[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdvertisement[]) => {
          if (!advertisementAnswers.advertisementId) {
            this.advertisements = resBody;
          } else {
            this.advertisementService
              .find(advertisementAnswers.advertisementId)
              .pipe(
                map((subRes: HttpResponse<IAdvertisement>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAdvertisement[]) => (this.advertisements = concatRes));
          }
        });
    });
  }

  updateForm(advertisementAnswers: IAdvertisementAnswers): void {
    this.editForm.patchValue({
      id: advertisementAnswers.id,
      answer: advertisementAnswers.answer,
      city: advertisementAnswers.city,
      stateProvince: advertisementAnswers.stateProvince,
      advertisementId: advertisementAnswers.advertisementId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const advertisementAnswers = this.createFromForm();
    if (advertisementAnswers.id !== undefined) {
      this.subscribeToSaveResponse(this.advertisementAnswersService.update(advertisementAnswers));
    } else {
      this.subscribeToSaveResponse(this.advertisementAnswersService.create(advertisementAnswers));
    }
  }

  private createFromForm(): IAdvertisementAnswers {
    return {
      ...new AdvertisementAnswers(),
      id: this.editForm.get(['id'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      advertisementId: this.editForm.get(['advertisementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdvertisementAnswers>>): void {
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

  trackById(index: number, item: IAdvertisement): any {
    return item.id;
  }
}
