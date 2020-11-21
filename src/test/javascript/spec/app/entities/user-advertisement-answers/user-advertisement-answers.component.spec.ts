import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BonlyTestModule } from '../../../test.module';
import { UserAdvertisementAnswersComponent } from 'app/entities/user-advertisement-answers/user-advertisement-answers.component';
import { UserAdvertisementAnswersService } from 'app/entities/user-advertisement-answers/user-advertisement-answers.service';
import { UserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';

describe('Component Tests', () => {
  describe('UserAdvertisementAnswers Management Component', () => {
    let comp: UserAdvertisementAnswersComponent;
    let fixture: ComponentFixture<UserAdvertisementAnswersComponent>;
    let service: UserAdvertisementAnswersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [UserAdvertisementAnswersComponent],
      })
        .overrideTemplate(UserAdvertisementAnswersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAdvertisementAnswersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAdvertisementAnswersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserAdvertisementAnswers(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userAdvertisementAnswers && comp.userAdvertisementAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
