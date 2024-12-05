import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUplatnica } from '../uplatnica.model';
import { UplatnicaService } from '../service/uplatnica.service';

@Injectable({ providedIn: 'root' })
export class UplatnicaRoutingResolveService implements Resolve<IUplatnica | null> {
  constructor(protected service: UplatnicaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUplatnica | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((uplatnica: HttpResponse<IUplatnica>) => {
          if (uplatnica.body) {
            return of(uplatnica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
