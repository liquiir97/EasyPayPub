import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUplatnica, NewUplatnica } from '../uplatnica.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUplatnica for edit and NewUplatnicaFormGroupInput for create.
 */
type UplatnicaFormGroupInput = IUplatnica | PartialWithRequiredKeyOf<NewUplatnica>;

type UplatnicaFormDefaults = Pick<NewUplatnica, 'id'>;

type UplatnicaFormGroupContent = {
  id: FormControl<IUplatnica['id'] | NewUplatnica['id']>;
  uplatilac: FormControl<IUplatnica['uplatilac']>;
  svrhaUplate: FormControl<IUplatnica['svrhaUplate']>;
  primalac: FormControl<IUplatnica['primalac']>;
  sifraPlacanja: FormControl<IUplatnica['sifraPlacanja']>;
  valuta: FormControl<IUplatnica['valuta']>;
  iznos: FormControl<IUplatnica['iznos']>;
  racunPrimaoca: FormControl<IUplatnica['racunPrimaoca']>;
  model: FormControl<IUplatnica['model']>;
  pozivNaBroj: FormControl<IUplatnica['pozivNaBroj']>;
  datumKreiranja: FormControl<IUplatnica['datumKreiranja']>;
};

export type UplatnicaFormGroup = FormGroup<UplatnicaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UplatnicaFormService {
  createUplatnicaFormGroup(uplatnica: UplatnicaFormGroupInput = { id: null }): UplatnicaFormGroup {
    const uplatnicaRawValue = {
      ...this.getFormDefaults(),
      ...uplatnica,
    };
    return new FormGroup<UplatnicaFormGroupContent>({
      id: new FormControl(
        { value: uplatnicaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      uplatilac: new FormControl(uplatnicaRawValue.uplatilac),
      svrhaUplate: new FormControl(uplatnicaRawValue.svrhaUplate),
      primalac: new FormControl(uplatnicaRawValue.primalac),
      sifraPlacanja: new FormControl(uplatnicaRawValue.sifraPlacanja),
      valuta: new FormControl(uplatnicaRawValue.valuta),
      iznos: new FormControl(uplatnicaRawValue.iznos),
      racunPrimaoca: new FormControl(uplatnicaRawValue.racunPrimaoca),
      model: new FormControl(uplatnicaRawValue.model),
      pozivNaBroj: new FormControl(uplatnicaRawValue.pozivNaBroj),
      datumKreiranja: new FormControl(uplatnicaRawValue.datumKreiranja),
    });
  }

  getUplatnica(form: UplatnicaFormGroup): IUplatnica | NewUplatnica {
    return form.getRawValue() as IUplatnica | NewUplatnica;
  }

  resetForm(form: UplatnicaFormGroup, uplatnica: UplatnicaFormGroupInput): void {
    const uplatnicaRawValue = { ...this.getFormDefaults(), ...uplatnica };
    form.reset(
      {
        ...uplatnicaRawValue,
        id: { value: uplatnicaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UplatnicaFormDefaults {
    return {
      id: null,
    };
  }
}
