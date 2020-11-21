import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { PrizeRegistrationUpdateComponent } from 'app/entities/prize-registration/prize-registration-update.component';
import { PrizeRegistrationService } from 'app/entities/prize-registration/prize-registration.service';
import { PrizeRegistration } from 'app/shared/model/prize-registration.model';

describe('Component Tests', () => {
  describe('PrizeRegistration Management Update Component', () => {
    let comp: PrizeRegistrationUpdateComponent;
    let fixture: ComponentFixture<PrizeRegistrationUpdateComponent>;
    let service: PrizeRegistrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [PrizeRegistrationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PrizeRegistrationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PrizeRegistrationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PrizeRegistrationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PrizeRegistration(123);
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
        const entity = new PrizeRegistration();
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
