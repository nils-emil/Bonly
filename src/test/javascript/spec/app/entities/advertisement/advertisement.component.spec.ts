import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementComponent } from 'app/entities/advertisement/advertisement.component';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';
import { Advertisement } from 'app/shared/model/advertisement.model';

describe('Component Tests', () => {
  describe('Advertisement Management Component', () => {
    let comp: AdvertisementComponent;
    let fixture: ComponentFixture<AdvertisementComponent>;
    let service: AdvertisementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementComponent],
      })
        .overrideTemplate(AdvertisementComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvertisementComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertisementService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Advertisement(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.advertisements && comp.advertisements[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
