import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUplatnica } from '../uplatnica.model';
import { UplatnicaService } from '../service/uplatnica.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './uplatnica-delete-dialog.component.html',
})
export class UplatnicaDeleteDialogComponent {
  uplatnica?: IUplatnica;

  constructor(protected uplatnicaService: UplatnicaService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uplatnicaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
