import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UplatnicaComponent } from './list/uplatnica.component';
import { UplatnicaDetailComponent } from './detail/uplatnica-detail.component';
import { UplatnicaUpdateComponent } from './update/uplatnica-update.component';
import { UplatnicaDeleteDialogComponent } from './delete/uplatnica-delete-dialog.component';
import { UplatnicaRoutingModule } from './route/uplatnica-routing.module';

@NgModule({
  imports: [SharedModule, UplatnicaRoutingModule],
  declarations: [UplatnicaComponent, UplatnicaDetailComponent, UplatnicaUpdateComponent, UplatnicaDeleteDialogComponent],
})
export class UplatnicaModule {}
