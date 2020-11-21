import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BonlyTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { AdvertisementAnswersDeleteDialogComponent } from 'app/entities/advertisement-answers/advertisement-answers-delete-dialog.component';
import { AdvertisementAnswersService } from 'app/entities/advertisement-answers/advertisement-answers.service';

describe('Component Tests', () => {
  describe('AdvertisementAnswers Management Delete Component', () => {
    let comp: AdvertisementAnswersDeleteDialogComponent;
    let fixture: ComponentFixture<AdvertisementAnswersDeleteDialogComponent>;
    let service: AdvertisementAnswersService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementAnswersDeleteDialogComponent],
      })
        .overrideTemplate(AdvertisementAnswersDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdvertisementAnswersDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdvertisementAnswersService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
