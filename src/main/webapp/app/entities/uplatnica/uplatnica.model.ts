import dayjs from 'dayjs/esm';

export interface IUplatnica {
  id: number;
  uplatilac?: string | null;
  svrhaUplate?: string | null;
  primalac?: string | null;
  sifraPlacanja?: string | null;
  valuta?: string | null;
  iznos?: number | null;
  racunPrimaoca?: string | null;
  model?: string | null;
  pozivNaBroj?: string | null;
  datumKreiranja?: dayjs.Dayjs | null;
  userId?: number | null;
  generatePdf?: boolean | null;
}

export type NewUplatnica = Omit<IUplatnica, 'id'> & { id: null };
