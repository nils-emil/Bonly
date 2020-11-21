import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementAnswersUpdateComponent } from 'app/entities/advertisement-answers/advertisement-answers-update.component';
import { AdvertisementAnswersService } from 'app/entities/advertisement-answers/advertisement-answers.service';
import { AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

describe('Component Tests', () => {
  describe('AdvertisementAnswers Management Update Component', () => {
    let comp: AdvertisementAnswersUpdateComponent;
    let fixture: ComponentFixture<AdvertisementAnswersUpdateComponent>;
    let service: AdvertisementAnswersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementAnswersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdvertisementAnswersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdvertisementAnswersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertisementAnswersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdvertisementAnswers(123);
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
        const entity = new AdvertisementAnswers();
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
