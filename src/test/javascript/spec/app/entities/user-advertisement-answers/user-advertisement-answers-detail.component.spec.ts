import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { UserAdvertisementAnswersDetailComponent } from 'app/entities/user-advertisement-answers/user-advertisement-answers-detail.component';
import { UserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';

describe('Component Tests', () => {
  describe('UserAdvertisementAnswers Management Detail Component', () => {
    let comp: UserAdvertisementAnswersDetailComponent;
    let fixture: ComponentFixture<UserAdvertisementAnswersDetailComponent>;
    const route = ({ data: of({ userAdvertisementAnswers: new UserAdvertisementAnswers(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [UserAdvertisementAnswersDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserAdvertisementAnswersDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserAdvertisementAnswersDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userAdvertisementAnswers on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userAdvertisementAnswers).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
