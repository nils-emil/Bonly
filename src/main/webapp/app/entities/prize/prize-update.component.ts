import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPrize, Prize } from 'app/shared/model/prize.model';
import { PrizeService } from './prize.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-prize-update',
  templateUrl: './prize-update.component.html',
})
export class PrizeUpdateComponent implements OnInit {
  isSaving = false;
  winners: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    registationStops: [],
    winnerChosenAt: [],
    creditsRequired: [],
    winnerId: [],
  });

  constructor(
    protected prizeService: PrizeService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ prize }) => {
      if (!prize.id) {
        const today = moment().startOf('day');
        prize.registationStops = today;
        prize.winnerChosenAt = today;
      }

      this.updateForm(prize);

      this.personService
        .query({ filter: 'prize-is-null' })
        .pipe(
          map((res: HttpResponse<IPerson[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IPerson[]) => {
          if (!prize.winnerId) {
            this.winners = resBody;
          } else {
            this.personService
              .find(prize.winnerId)
              .pipe(
                map((subRes: HttpResponse<IPerson>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IPerson[]) => (this.winners = concatRes));
          }
        });
    });
  }

  updateForm(prize: IPrize): void {
    this.editForm.patchValue({
      id: prize.id,
      registationStops: prize.registationStops ? prize.registationStops.format(DATE_TIME_FORMAT) : null,
      winnerChosenAt: prize.winnerChosenAt ? prize.winnerChosenAt.format(DATE_TIME_FORMAT) : null,
      creditsRequired: prize.creditsRequired,
      winnerId: prize.winnerId,
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
      winnerChosenAt: this.editForm.get(['winnerChosenAt'])!.value
        ? moment(this.editForm.get(['winnerChosenAt'])!.value, DATE_TIME_FORMAT)
        : undefined,
      creditsRequired: this.editForm.get(['creditsRequired'])!.value,
      winnerId: this.editForm.get(['winnerId'])!.value,
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
}
