export interface IOsnovPlacanja {
  id: number;
  code?: number | null;
  name?: string | null;
  detail?: string | null;
}

export type NewUplatnica = Omit<IOsnovPlacanja, 'id'> & { id: null };
