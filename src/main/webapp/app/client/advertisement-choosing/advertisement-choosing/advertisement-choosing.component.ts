import { Component, OnInit } from '@angular/core';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';
import { IAdvertisement } from 'app/shared/model/advertisement.model';

@Component({
  selector: 'jhi-advertisement-choosing',
  templateUrl: './advertisement-choosing.component.html',
  styleUrls: ['./advertisement-choosing.component.scss'],
})
export class AdvertisementChoosingComponent implements OnInit {
  public advertisement: IAdvertisement | null = null;
  public adExist = false;

  constructor(private adService: AdvertisementService) {}

  ngOnInit(): void {
    this.findAnAddToShow();
  }

  private findAnAddToShow(): void {
    this.adService.findOneAdToShow().subscribe(e => {
      if (e && e.body) {
        this.adExist = true;
        this.advertisement = e.body;
      } else {
        this.adExist = false;
      }
    });
  }

  chooseAnswer(questionId: any, answerId: any): void {
    this.adService.chooseAnswer(questionId, answerId).subscribe(
      () => {
        this.findAnAddToShow();
      },
      () => {
        this.findAnAddToShow();
      }
    );
  }
}
