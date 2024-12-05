import dayjs from 'dayjs/esm';

import { IUplatnica, NewUplatnica } from './uplatnica.model';

export const sampleWithRequiredData: IUplatnica = {
  id: 96319,
};

export const sampleWithPartialData: IUplatnica = {
  id: 3634,
  uplatilac: 'payment',
  svrhaUplate: 'Borders',
  valuta: 'copy Assistant monitoring',
  iznos: 50426,
  racunPrimaoca: 'panel',
};

export const sampleWithFullData: IUplatnica = {
  id: 42687,
  uplatilac: 'grow',
  svrhaUplate: 'Loan Polarised neural',
  primalac: 'Underpass Account functionalities',
  sifraPlacanja: 'database Car Savings',
  valuta: 'silver Hampshire',
  iznos: 30884,
  racunPrimaoca: 'Bacon world-class Shirt',
  model: 'implementation navigating Buckinghamshire',
  pozivNaBroj: 'Web Shoes',
  datumKreiranja: dayjs('2023-11-08'),
};

export const sampleWithNewData: NewUplatnica = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
