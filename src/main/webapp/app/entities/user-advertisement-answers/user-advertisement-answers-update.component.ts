import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IUserAdvertisementAnswers, UserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';
import { UserAdvertisementAnswersService } from './user-advertisement-answers.service';
import { IPerson } from 'app/shared/model/person.model';
import { IAdvertisement } from 'app/shared/model/advertisement.model';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';

type SelectableEntity = IPerson | IAdvertisement;

@Component({
  selector: 'jhi-user-advertisement-answers-update',
  templateUrl: './user-advertisement-answers-update.component.html',
})
export class UserAdvertisementAnswersUpdateComponent implements OnInit {
  isSaving = false;
  users: IPerson[] = [];
  advertisements: IAdvertisement[] = [];

  editForm = this.fb.group({
    id: [],
    stateProvince: [],
    userId: [],
    advertisementId: [],
  });

  constructor(
    protected userAdvertisementAnswersService: UserAdvertisementAnswersService,
    protected advertisementService: AdvertisementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAdvertisementAnswers }) => {
      this.updateForm(userAdvertisementAnswers);

      this.advertisementService
        .query({ filter: 'useradvertisementanswers-is-null' })
        .pipe(
          map((res: HttpResponse<IAdvertisement[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAdvertisement[]) => {
          if (!userAdvertisementAnswers.advertisementId) {
            this.advertisements = resBody;
          } else {
            this.advertisementService
              .find(userAdvertisementAnswers.advertisementId)
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

  updateForm(userAdvertisementAnswers: IUserAdvertisementAnswers): void {
    this.editForm.patchValue({
      id: userAdvertisementAnswers.id,
      stateProvince: userAdvertisementAnswers.stateProvince,
      userId: userAdvertisementAnswers.userId,
      advertisementId: userAdvertisementAnswers.advertisementId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userAdvertisementAnswers = this.createFromForm();
    if (userAdvertisementAnswers.id !== undefined) {
      this.subscribeToSaveResponse(this.userAdvertisementAnswersService.update(userAdvertisementAnswers));
    } else {
      this.subscribeToSaveResponse(this.userAdvertisementAnswersService.create(userAdvertisementAnswers));
    }
  }

  private createFromForm(): IUserAdvertisementAnswers {
    return {
      ...new UserAdvertisementAnswers(),
      id: this.editForm.get(['id'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      advertisementId: this.editForm.get(['advertisementId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAdvertisementAnswers>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
