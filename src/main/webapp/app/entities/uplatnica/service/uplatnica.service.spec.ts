import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IUplatnica } from '../uplatnica.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../uplatnica.test-samples';

import { UplatnicaService, RestUplatnica } from './uplatnica.service';

const requireRestSample: RestUplatnica = {
  ...sampleWithRequiredData,
  datumKreiranja: sampleWithRequiredData.datumKreiranja?.format(DATE_FORMAT),
};

describe('Uplatnica Service', () => {
  let service: UplatnicaService;
  let httpMock: HttpTestingController;
  let expectedResult: IUplatnica | IUplatnica[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UplatnicaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Uplatnica', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const uplatnica = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(uplatnica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Uplatnica', () => {
      const uplatnica = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(uplatnica).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Uplatnica', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Uplatnica', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Uplatnica', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUplatnicaToCollectionIfMissing', () => {
      it('should add a Uplatnica to an empty array', () => {
        const uplatnica: IUplatnica = sampleWithRequiredData;
        expectedResult = service.addUplatnicaToCollectionIfMissing([], uplatnica);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uplatnica);
      });

      it('should not add a Uplatnica to an array that contains it', () => {
        const uplatnica: IUplatnica = sampleWithRequiredData;
        const uplatnicaCollection: IUplatnica[] = [
          {
            ...uplatnica,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUplatnicaToCollectionIfMissing(uplatnicaCollection, uplatnica);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Uplatnica to an array that doesn't contain it", () => {
        const uplatnica: IUplatnica = sampleWithRequiredData;
        const uplatnicaCollection: IUplatnica[] = [sampleWithPartialData];
        expectedResult = service.addUplatnicaToCollectionIfMissing(uplatnicaCollection, uplatnica);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uplatnica);
      });

      it('should add only unique Uplatnica to an array', () => {
        const uplatnicaArray: IUplatnica[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const uplatnicaCollection: IUplatnica[] = [sampleWithRequiredData];
        expectedResult = service.addUplatnicaToCollectionIfMissing(uplatnicaCollection, ...uplatnicaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const uplatnica: IUplatnica = sampleWithRequiredData;
        const uplatnica2: IUplatnica = sampleWithPartialData;
        expectedResult = service.addUplatnicaToCollectionIfMissing([], uplatnica, uplatnica2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(uplatnica);
        expect(expectedResult).toContain(uplatnica2);
      });

      it('should accept null and undefined values', () => {
        const uplatnica: IUplatnica = sampleWithRequiredData;
        expectedResult = service.addUplatnicaToCollectionIfMissing([], null, uplatnica, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(uplatnica);
      });

      it('should return initial array if no Uplatnica is added', () => {
        const uplatnicaCollection: IUplatnica[] = [sampleWithRequiredData];
        expectedResult = service.addUplatnicaToCollectionIfMissing(uplatnicaCollection, undefined, null);
        expect(expectedResult).toEqual(uplatnicaCollection);
      });
    });

    describe('compareUplatnica', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUplatnica(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUplatnica(entity1, entity2);
        const compareResult2 = service.compareUplatnica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUplatnica(entity1, entity2);
        const compareResult2 = service.compareUplatnica(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUplatnica(entity1, entity2);
        const compareResult2 = service.compareUplatnica(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
