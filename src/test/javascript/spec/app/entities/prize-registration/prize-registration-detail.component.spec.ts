import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BonlyTestModule } from '../../../test.module';
import { PrizeRegistrationDetailComponent } from 'app/entities/prize-registration/prize-registration-detail.component';
import { PrizeRegistration } from 'app/shared/model/prize-registration.model';

describe('Component Tests', () => {
  describe('PrizeRegistration Management Detail Component', () => {
    let comp: PrizeRegistrationDetailComponent;
    let fixture: ComponentFixture<PrizeRegistrationDetailComponent>;
    const route = ({ data: of({ prizeRegistration: new PrizeRegistration(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [PrizeRegistrationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PrizeRegistrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PrizeRegistrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load prizeRegistration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.prizeRegistration).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
