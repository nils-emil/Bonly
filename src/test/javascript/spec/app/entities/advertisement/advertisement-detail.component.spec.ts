import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BonlyTestModule } from '../../../test.module';
import { AdvertisementDetailComponent } from 'app/entities/advertisement/advertisement-detail.component';
import { Advertisement } from 'app/shared/model/advertisement.model';

describe('Component Tests', () => {
  describe('Advertisement Management Detail Component', () => {
    let comp: AdvertisementDetailComponent;
    let fixture: ComponentFixture<AdvertisementDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ advertisement: new Advertisement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BonlyTestModule],
        declarations: [AdvertisementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AdvertisementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdvertisementDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load advertisement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.advertisement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
