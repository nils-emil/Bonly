import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PrizeService } from 'app/entities/prize/prize.service';
import { IPrize, Prize } from 'app/shared/model/prize.model';

describe('Service Tests', () => {
  describe('Prize Service', () => {
    let injector: TestBed;
    let service: PrizeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPrize;
    let expectedResult: IPrize | IPrize[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PrizeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Prize(0, currentDate, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            registationStops: currentDate.format(DATE_TIME_FORMAT),
            winnerChosenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Prize', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            registationStops: currentDate.format(DATE_TIME_FORMAT),
            winnerChosenAt: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registationStops: currentDate,
            winnerChosenAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Prize()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Prize', () => {
        const returnedFromService = Object.assign(
          {
            registationStops: currentDate.format(DATE_TIME_FORMAT),
            winnerChosenAt: currentDate.format(DATE_TIME_FORMAT),
            creditsRequired: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registationStops: currentDate,
            winnerChosenAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Prize', () => {
        const returnedFromService = Object.assign(
          {
            registationStops: currentDate.format(DATE_TIME_FORMAT),
            winnerChosenAt: currentDate.format(DATE_TIME_FORMAT),
            creditsRequired: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registationStops: currentDate,
            winnerChosenAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Prize', () => {
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
