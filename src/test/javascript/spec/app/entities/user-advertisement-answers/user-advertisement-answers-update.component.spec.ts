import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { UserAdvertisementAnswersUpdateComponent } from 'app/entities/user-advertisement-answers/user-advertisement-answers-update.component';
import { UserAdvertisementAnswersService } from 'app/entities/user-advertisement-answers/user-advertisement-answers.service';
import { UserAdvertisementAnswers } from 'app/shared/model/user-advertisement-answers.model';

describe('Component Tests', () => {
  describe('UserAdvertisementAnswers Management Update Component', () => {
    let comp: UserAdvertisementAnswersUpdateComponent;
    let fixture: ComponentFixture<UserAdvertisementAnswersUpdateComponent>;
    let service: UserAdvertisementAnswersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [UserAdvertisementAnswersUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAdvertisementAnswersUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAdvertisementAnswersUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAdvertisementAnswersService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAdvertisementAnswers(123);
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
        const entity = new UserAdvertisementAnswers();
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
