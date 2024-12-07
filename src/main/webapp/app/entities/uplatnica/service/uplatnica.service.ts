import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUplatnica, NewUplatnica } from '../uplatnica.model';
import { IOblikPlacanja } from '../oblik-placanja.model';
import { IOsnovPlacanja } from '../osnov-placanja.model';
import { environment as enviromentProd } from 'app/environment/environment-prod';
import { environment as enviromentDev } from 'app/environment/environment-dev';

export type PartialUpdateUplatnica = Partial<IUplatnica> & Pick<IUplatnica, 'id'>;

type RestOf<T extends IUplatnica | NewUplatnica> = Omit<T, 'datumKreiranja'> & {
  datumKreiranja?: string | null;
};

export type RestUplatnica = RestOf<IUplatnica>;

export type NewRestUplatnica = RestOf<NewUplatnica>;

export type PartialUpdateRestUplatnica = RestOf<PartialUpdateUplatnica>;

export type EntityResponseType = HttpResponse<IUplatnica>;
export type EntityArrayResponseType = HttpResponse<IUplatnica[]>;
export type EntityArrayResponseTypeOblik = HttpResponse<IOblikPlacanja[]>;

@Injectable({ providedIn: 'root' })
export class UplatnicaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/uplatnicas');

  private baseUrl = enviromentDev.baseUrl;
  private UrlData = enviromentDev.UrlData;;
  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(uplatnica: NewUplatnica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uplatnica);
    return this.http
      .post<RestUplatnica>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(uplatnica: IUplatnica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uplatnica);
    return this.http
      .put<RestUplatnica>(`${this.resourceUrl}/${this.getUplatnicaIdentifier(uplatnica)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(uplatnica: PartialUpdateUplatnica): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(uplatnica);
    return this.http
      .patch<RestUplatnica>(`${this.resourceUrl}/${this.getUplatnicaIdentifier(uplatnica)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUplatnica>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUplatnica[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUplatnicaIdentifier(uplatnica: Pick<IUplatnica, 'id'>): number {
    return uplatnica.id;
  }

  compareUplatnica(o1: Pick<IUplatnica, 'id'> | null, o2: Pick<IUplatnica, 'id'> | null): boolean {
    return o1 && o2 ? this.getUplatnicaIdentifier(o1) === this.getUplatnicaIdentifier(o2) : o1 === o2;
  }

  addUplatnicaToCollectionIfMissing<Type extends Pick<IUplatnica, 'id'>>(
    uplatnicaCollection: Type[],
    ...uplatnicasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const uplatnicas: Type[] = uplatnicasToCheck.filter(isPresent);
    if (uplatnicas.length > 0) {
      const uplatnicaCollectionIdentifiers = uplatnicaCollection.map(uplatnicaItem => this.getUplatnicaIdentifier(uplatnicaItem)!);
      const uplatnicasToAdd = uplatnicas.filter(uplatnicaItem => {
        const uplatnicaIdentifier = this.getUplatnicaIdentifier(uplatnicaItem);
        if (uplatnicaCollectionIdentifiers.includes(uplatnicaIdentifier)) {
          return false;
        }
        uplatnicaCollectionIdentifiers.push(uplatnicaIdentifier);
        return true;
      });
      return [...uplatnicasToAdd, ...uplatnicaCollection];
    }
    return uplatnicaCollection;
  }

  findAllOblikPlacanja(): Observable<EntityArrayResponseTypeOblik> {
    const header = new HttpHeaders().set('Activity-System', 'Activity-System');
    header.set('Access-Control-Allow-Origin', '*');
    return this.http.get<IOblikPlacanja[]>(this.baseUrl + '/find-all-oblik-placanja', { observe: 'response' });
  }

  findAllOsnovPlacanja(): Observable<IOsnovPlacanja[]> {
    return this.http.get<IOsnovPlacanja[]>(this.baseUrl + '/find-all-osnov-placanja');
  }

  findAllPaymentsByUser(user: string): Observable<IUplatnica[]> {
    const urlAdd = this.UrlData + '/find-all-payments-by-user';
    return this.http.get<IUplatnica[]>(`${urlAdd}/${user}`);
  }

  generatePdf(id: number): void {
    //  Observable<boolean> {

    const urlPdf = this.baseUrl + '/generate-pdf';
    var pdfDownloadUrl = `${urlPdf}/${id}`;
    var iframe = document.createElement('iframe');
    iframe.style.display = 'none';
    iframe.src = pdfDownloadUrl;
    document.body.appendChild(iframe);

    //  return this.http.get<boolean>(`${urlPdf}/${id}`);
  }

  createFromTemplate(uplatnica: IUplatnica): Observable<EntityResponseType> {
    return this.http
      .post<RestUplatnica>(this.UrlData + '/create-from-template', uplatnica, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  protected convertDateFromClient<T extends IUplatnica | NewUplatnica | PartialUpdateUplatnica>(uplatnica: T): RestOf<T> {
    return {
      ...uplatnica,
      datumKreiranja: uplatnica.datumKreiranja?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restUplatnica: RestUplatnica): IUplatnica {
    return {
      ...restUplatnica,
      datumKreiranja: restUplatnica.datumKreiranja ? dayjs(restUplatnica.datumKreiranja) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUplatnica>): HttpResponse<IUplatnica> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUplatnica[]>): HttpResponse<IUplatnica[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
