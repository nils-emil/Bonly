import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BonlyTestModule } from '../../../test.module';
import { PrizeRegistrationComponent } from 'app/entities/prize-registration/prize-registration.component';
import { PrizeRegistrationService } from 'app/entities/prize-registration/prize-registration.service';
import { PrizeRegistration } from 'app/shared/model/prize-registration.model';

describe('Component Tests', () => {
  describe('PrizeRegistration Management Component', () => {
    let comp: PrizeRegistrationComponent;
    let fixture: ComponentFixture<PrizeRegistrationComponent>;
    let service: PrizeRegistrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [PrizeRegistrationComponent],
      })
        .overrideTemplate(PrizeRegistrationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrizeRegistrationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrizeRegistrationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PrizeRegistration(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.prizeRegistrations && comp.prizeRegistrations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
