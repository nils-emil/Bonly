import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPrizeRegistration, PrizeRegistration } from 'app/shared/model/prize-registration.model';
import { PrizeRegistrationService } from './prize-registration.service';
import { IPrize } from 'app/shared/model/prize.model';
import { PrizeService } from 'app/entities/prize/prize.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

type SelectableEntity = IPrize | IPerson;

@Component({
  selector: 'jhi-prize-registration-update',
  templateUrl: './prize-registration-update.component.html',
})
export class PrizeRegistrationUpdateComponent implements OnInit {
  isSaving = false;
  prizes: IPrize[] = [];
  users: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    registation: [],
    prizeId: [],
    userId: [],
  });

  constructor(
    protected prizeRegistrationService: PrizeRegistrationService,
    protected prizeService: PrizeService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prizeRegistration }) => {
      if (!prizeRegistration.id) {
        const today = moment().startOf('day');
        prizeRegistration.registation = today;
      }

      this.updateForm(prizeRegistration);

      this.prizeService
        .query({ filter: 'prizeregistration-is-null' })
        .pipe(
          map((res: HttpResponse<IPrize[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPrize[]) => {
          if (!prizeRegistration.prizeId) {
            this.prizes = resBody;
          } else {
            this.prizeService
              .find(prizeRegistration.prizeId)
              .pipe(
                map((subRes: HttpResponse<IPrize>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPrize[]) => (this.prizes = concatRes));
          }
        });

      this.personService
        .query({ filter: 'prizeregistration-is-null' })
        .pipe(
          map((res: HttpResponse<IPerson[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPerson[]) => {
          if (!prizeRegistration.userId) {
            this.users = resBody;
          } else {
            this.personService
              .find(prizeRegistration.userId)
              .pipe(
                map((subRes: HttpResponse<IPerson>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPerson[]) => (this.users = concatRes));
          }
        });
    });
  }

  updateForm(prizeRegistration: IPrizeRegistration): void {
    this.editForm.patchValue({
      id: prizeRegistration.id,
      registation: prizeRegistration.registation ? prizeRegistration.registation.format(DATE_TIME_FORMAT) : null,
      prizeId: prizeRegistration.prizeId,
      userId: prizeRegistration.userId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const prizeRegistration = this.createFromForm();
    if (prizeRegistration.id !== undefined) {
      this.subscribeToSaveResponse(this.prizeRegistrationService.update(prizeRegistration));
    } else {
      this.subscribeToSaveResponse(this.prizeRegistrationService.create(prizeRegistration));
    }
  }

  private createFromForm(): IPrizeRegistration {
    return {
      ...new PrizeRegistration(),
      id: this.editForm.get(['id'])!.value,
      registation: this.editForm.get(['registation'])!.value
        ? moment(this.editForm.get(['registation'])!.value, DATE_TIME_FORMAT)
        : undefined,
      prizeId: this.editForm.get(['prizeId'])!.value,
      userId: this.editForm.get(['userId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrizeRegistration>>): void {
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
