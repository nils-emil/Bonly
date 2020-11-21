import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

@Component({
  selector: 'jhi-advertisement-answers-detail',
  templateUrl: './advertisement-answers-detail.component.html',
})
export class AdvertisementAnswersDetailComponent implements OnInit {
  advertisementAnswers: IAdvertisementAnswers | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ advertisementAnswers }) => (this.advertisementAnswers = advertisementAnswers));
  }

  previousState(): void {
    window.history.back();
  }
}
