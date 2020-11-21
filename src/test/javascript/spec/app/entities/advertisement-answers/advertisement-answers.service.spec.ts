import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdvertisementAnswersService } from 'app/entities/advertisement-answers/advertisement-answers.service';
import { IAdvertisementAnswers, AdvertisementAnswers } from 'app/shared/model/advertisement-answers.model';

describe('Service Tests', () => {
  describe('AdvertisementAnswers Service', () => {
    let injector: TestBed;
    let service: AdvertisementAnswersService;
    let httpMock: HttpTestingController;
    let elemDefault: IAdvertisementAnswers;
    let expectedResult: IAdvertisementAnswers | IAdvertisementAnswers[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AdvertisementAnswersService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AdvertisementAnswers(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AdvertisementAnswers', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AdvertisementAnswers()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AdvertisementAnswers', () => {
        const returnedFromService = Object.assign(
          {
            answer: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AdvertisementAnswers', () => {
        const returnedFromService = Object.assign(
          {
            answer: 'BBBBBB',
            city: 'BBBBBB',
            stateProvince: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AdvertisementAnswers', () => {
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
