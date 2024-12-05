import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UplatnicaComponent } from '../list/uplatnica.component';
import { UplatnicaDetailComponent } from '../detail/uplatnica-detail.component';
import { UplatnicaUpdateComponent } from '../update/uplatnica-update.component';
import { UplatnicaRoutingResolveService } from './uplatnica-routing-resolve.service';
import { ASC, DESC } from 'app/config/navigation.constants';

const uplatnicaRoute: Routes = [
  {
    path: '',
    component: UplatnicaComponent,
    data: {
      defaultSort: 'id,' + DESC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UplatnicaDetailComponent,
    resolve: {
      uplatnica: UplatnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UplatnicaUpdateComponent,
    resolve: {
      uplatnica: UplatnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/template',
    component: UplatnicaUpdateComponent,
    resolve: {
      uplatnica: UplatnicaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(uplatnicaRoute)],
  exports: [RouterModule],
})
export class UplatnicaRoutingModule {}
