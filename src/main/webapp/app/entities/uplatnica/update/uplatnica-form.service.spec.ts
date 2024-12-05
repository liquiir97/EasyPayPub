import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../uplatnica.test-samples';

import { UplatnicaFormService } from './uplatnica-form.service';

describe('Uplatnica Form Service', () => {
  let service: UplatnicaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UplatnicaFormService);
  });

  describe('Service methods', () => {
    describe('createUplatnicaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUplatnicaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uplatilac: expect.any(Object),
            svrhaUplate: expect.any(Object),
            primalac: expect.any(Object),
            sifraPlacanja: expect.any(Object),
            valuta: expect.any(Object),
            iznos: expect.any(Object),
            racunPrimaoca: expect.any(Object),
            model: expect.any(Object),
            pozivNaBroj: expect.any(Object),
            datumKreiranja: expect.any(Object),
          })
        );
      });

      it('passing IUplatnica should create a new form with FormGroup', () => {
        const formGroup = service.createUplatnicaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            uplatilac: expect.any(Object),
            svrhaUplate: expect.any(Object),
            primalac: expect.any(Object),
            sifraPlacanja: expect.any(Object),
            valuta: expect.any(Object),
            iznos: expect.any(Object),
            racunPrimaoca: expect.any(Object),
            model: expect.any(Object),
            pozivNaBroj: expect.any(Object),
            datumKreiranja: expect.any(Object),
          })
        );
      });
    });

    describe('getUplatnica', () => {
      it('should return NewUplatnica for default Uplatnica initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUplatnicaFormGroup(sampleWithNewData);

        const uplatnica = service.getUplatnica(formGroup) as any;

        expect(uplatnica).toMatchObject(sampleWithNewData);
      });

      it('should return NewUplatnica for empty Uplatnica initial value', () => {
        const formGroup = service.createUplatnicaFormGroup();

        const uplatnica = service.getUplatnica(formGroup) as any;

        expect(uplatnica).toMatchObject({});
      });

      it('should return IUplatnica', () => {
        const formGroup = service.createUplatnicaFormGroup(sampleWithRequiredData);

        const uplatnica = service.getUplatnica(formGroup) as any;

        expect(uplatnica).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUplatnica should not enable id FormControl', () => {
        const formGroup = service.createUplatnicaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUplatnica should disable id FormControl', () => {
        const formGroup = service.createUplatnicaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
