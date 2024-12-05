import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUplatnica } from '../uplatnica.model';

@Component({
  selector: 'jhi-uplatnica-detail',
  templateUrl: './uplatnica-detail.component.html',
})
export class UplatnicaDetailComponent implements OnInit {
  uplatnica: IUplatnica | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uplatnica }) => {
      this.uplatnica = uplatnica;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
