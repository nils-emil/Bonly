import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';

@Component({
  selector: 'jhi-user-advertisement-answers-detail',
  templateUrl: './user-advertisement-answers-detail.component.html',
})
export class UserAdvertisementAnswersDetailComponent implements OnInit {
  userAdvertisementAnswers: IUserAdvertisementAnswers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userAdvertisementAnswers }) => (this.userAdvertisementAnswers = userAdvertisementAnswers));
  }

  previousState(): void {
    window.history.back();
  }
}
