import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementUpdateComponent } from 'app/entities/advertisement/advertisement-update.component';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';
import { Advertisement } from 'app/shared/model/advertisement.model';

describe('Component Tests', () => {
  describe('Advertisement Management Update Component', () => {
    let comp: AdvertisementUpdateComponent;
    let fixture: ComponentFixture<AdvertisementUpdateComponent>;
    let service: AdvertisementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdvertisementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvertisementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertisementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Advertisement(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Advertisement();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
