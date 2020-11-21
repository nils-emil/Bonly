import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementAnswersDetailComponent } from 'app/entities/advertisement-answers/advertisement-answers-detail.component';
import { AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

describe('Component Tests', () => {
  describe('AdvertisementAnswers Management Detail Component', () => {
    let comp: AdvertisementAnswersDetailComponent;
    let fixture: ComponentFixture<AdvertisementAnswersDetailComponent>;
    const route = ({ data: of({ advertisementAnswers: new AdvertisementAnswers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementAnswersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AdvertisementAnswersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdvertisementAnswersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load advertisementAnswers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.advertisementAnswers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
