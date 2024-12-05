import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'uplatnica',
        data: { pageTitle: 'easyPayApp.uplatnica.home.title' },
        loadChildren: () => import('./uplatnica/uplatnica.module').then(m => m.UplatnicaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
