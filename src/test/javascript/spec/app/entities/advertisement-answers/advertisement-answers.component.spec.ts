import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementAnswersComponent } from 'app/entities/advertisement-answers/advertisement-answers.component';
import { AdvertisementAnswersService } from 'app/entities/advertisement-answers/advertisement-answers.service';
import { AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

describe('Component Tests', () => {
  describe('AdvertisementAnswers Management Component', () => {
    let comp: AdvertisementAnswersComponent;
    let fixture: ComponentFixture<AdvertisementAnswersComponent>;
    let service: AdvertisementAnswersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementAnswersComponent],
      })
        .overrideTemplate(AdvertisementAnswersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvertisementAnswersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertisementAnswersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AdvertisementAnswers(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.advertisementAnswers && comp.advertisementAnswers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
