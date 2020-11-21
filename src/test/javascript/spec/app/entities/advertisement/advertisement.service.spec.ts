import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AdvertisementService } from 'app/entities/advertisement/advertisement.service';
import { IAdvertisement, Advertisement } from 'app/shared/model/advertisement.model';

describe('Service Tests', () => {
  describe('Advertisement Service', () => {
    let injector: TestBed;
    let service: AdvertisementService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdvertisement;
    let expectedResult: IAdvertisement | IAdvertisement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdvertisementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Advertisement(0, currentDate, currentDate, 'image/png', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            activeFrom: currentDate.format(DATE_TIME_FORMAT),
            activeUntill: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Advertisement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            activeFrom: currentDate.format(DATE_TIME_FORMAT),
            activeUntill: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activeFrom: currentDate,
            activeUntill: currentDate,
          },
          returnedFromService
        );

        service.create(new Advertisement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Advertisement', () => {
        const returnedFromService = Object.assign(
          {
            activeFrom: currentDate.format(DATE_TIME_FORMAT),
            activeUntill: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB',
            question: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activeFrom: currentDate,
            activeUntill: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Advertisement', () => {
        const returnedFromService = Object.assign(
          {
            activeFrom: currentDate.format(DATE_TIME_FORMAT),
            activeUntill: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB',
            question: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            activeFrom: currentDate,
            activeUntill: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Advertisement', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
