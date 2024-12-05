export interface IOblikPlacanja {
  id: number;
  code?: number | null;
  name?: string | null;
  detail?: string | null;
}

export type NewUplatnica = Omit<IOblikPlacanja, 'id'> & { id: null };
