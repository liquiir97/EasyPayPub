import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UplatnicaFormService, UplatnicaFormGroup } from './uplatnica-form.service';
import { IUplatnica } from '../uplatnica.model';
import { UplatnicaService } from '../service/uplatnica.service';
import { AccountService } from 'app/core/auth/account.service';
import { IOblikPlacanja } from '../oblik-placanja.model';
import { IOsnovPlacanja } from '../osnov-placanja.model';
import dayjs from 'dayjs/esm';

@Component({
  selector: 'jhi-uplatnica-update',
  templateUrl: './uplatnica-update.component.html',
  styleUrls: ['./uplatnica-update.component.scss'],
})
export class UplatnicaUpdateComponent implements OnInit {
  isSaving = false;
  uplatnica: IUplatnica | null = null;
  firstName = '';
  lastName = '';
  valuta = 'RSD';
  osnovPlacanjaId = '';
  oblikPlacanjaId = '';
  svrhaPlacanjaElement = '';
  primalacElement = '';
  status = '2';
  user = '';
  osnovPlValue: string | null | undefined;
  primalacValue: string | null | undefined;
  svrhaUplateValue: string | null | undefined;
  isDisabled = true;
  todayDate = dayjs();
  todayDateForRestriction = { year: new Date().getFullYear(), month: new Date().getMonth() + 1, day: new Date().getDate() };
  userId: number | null | undefined;
  errorTekuci = false;
  errorIznos = false;
  errorSvrhaPlacanja = false;
  errorModelPozNaBr = false;
  errorModel = false;
  errorPozivNaBroj = false;
  geneatePdf = false;

  oblikPlacanja: IOblikPlacanja[] = [];
  osnovPlacanja: IOsnovPlacanja[] = [];
  svrhaPlacanjaPrevious: string[] = [];
  primalacPrevious: string[] = [];

  editForm: UplatnicaFormGroup = this.uplatnicaFormService.createUplatnicaFormGroup();

  constructor(
    protected uplatnicaService: UplatnicaService,
    protected uplatnicaFormService: UplatnicaFormService,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.userId = account?.id;
      this.user = account!.login;
    });
    this.activatedRoute.data.subscribe(({ uplatnica }) => {
      this.uplatnica = uplatnica;
      if (uplatnica) {
        this.updateForm(uplatnica);
      }
      if (this.uplatnica?.id) {
        this.primalacValue = this.uplatnica.primalac;
        this.svrhaUplateValue = this.uplatnica.svrhaUplate;
        this.osnovPlValue = this.uplatnica.sifraPlacanja?.substring(1);
      }
      if (!uplatnica) {
        this.accountService.identity().subscribe(account => {
          if (account) {
            const fullName = account.firstName! + ' ' + account.lastName!;
            this.editForm.setValue({
              uplatilac: fullName,
              id: null,
              svrhaUplate: null,
              primalac: null,
              sifraPlacanja: null,
              valuta: this.valuta,
              iznos: null,
              racunPrimaoca: null,
              model: null,
              pozivNaBroj: null,
              datumKreiranja: this.todayDate,
            });
          }
        });
      }
      this.uplatnicaService.findAllOblikPlacanja().subscribe(res => {
        this.oblikPlacanja = res.body!;
        if (uplatnica) {
          const osnovStart = this.uplatnica?.sifraPlacanja?.substring(0, 1);
          const osnovPl = this.oblikPlacanja.filter(ele => ele.code?.toString() === osnovStart);
          const id = osnovPl[0].code?.toString();
          this.oblikPlacanjaId = `${String(id)}` + ' ' + `${String(osnovPl[0].name)}`;
          this.status = this.oblikPlacanjaId.substring(0, 1);
        }
      });
    });

    this.uplatnicaService.findAllOsnovPlacanja().subscribe(res => {
      this.osnovPlacanja = res;
      for (const os of this.osnovPlacanja) {
        const id = os.code!.toString();
        os.name = `${String(id)}` + ' ' + `${String(os.name)}`;
      }
    });

    this.uplatnicaService.findAllPaymentsByUser(this.user).subscribe(res => {
      const listUplatnicaDto: IUplatnica[] = res;
      this.svrhaPlacanjaPrevious = res.map(value => value.svrhaUplate as string);
      this.primalacPrevious = res.map(value => value.primalac as string);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    const uplatnica = this.uplatnicaFormService.getUplatnica(this.editForm);
    uplatnica.svrhaUplate = this.svrhaPlacanjaElement;
    uplatnica.primalac = this.primalacElement;
    uplatnica.sifraPlacanja = `${String(this.status)}` + `${String(this.osnovPlValue)}`;
    uplatnica.datumKreiranja = this.todayDate;
    uplatnica.userId = this.userId;
    uplatnica.svrhaUplate = this.svrhaUplateValue;
    uplatnica.primalac = this.primalacValue;
    this.checkModel();
    this.checkPozivNaBroj();
    if (this.errorIznos || this.errorModel || this.errorPozivNaBroj || this.errorSvrhaPlacanja || this.errorTekuci) {
      alert('Morate ispraviti greske');
    } else if (
      uplatnica.iznos === null ||
      uplatnica.primalac === undefined ||
      uplatnica.primalac! === '' ||
      uplatnica.racunPrimaoca === null ||
      uplatnica.racunPrimaoca! === '' ||
      uplatnica.sifraPlacanja === '2undefined'
    ) {
      alert('Potrebno je popuniti polja pre kreiranja!');
    } else {
      this.isSaving = true;
      this.geneatePdf = confirm('Da li zelite da generisete PDF nakon kreiranja uplatnice!');
      if (uplatnica.id !== null) {
        this.subscribeToSaveResponse(this.uplatnicaService.createFromTemplate(uplatnica));
      } else {
        this.subscribeToSaveResponse(this.uplatnicaService.create(uplatnica));
      }
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUplatnica>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: (response: HttpResponse<IUplatnica>) => {
        this.onSaveSuccess(response.body?.id);
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(id: number | undefined): void {
    if (this.geneatePdf && id != undefined) {
      this.uplatnicaService.generatePdf(id);
    }
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(uplatnica: IUplatnica): void {
    this.uplatnica = uplatnica;
    this.uplatnicaFormService.resetForm(this.editForm, uplatnica);
  }

  protected onSelected(a: string): void {
    this.oblikPlacanjaId = a;
  }

  protected ssChange(a: any): void {
    const tmp: string = a;
    this.osnovPlValue = tmp.substring(0, 2);
    this.osnovPlacanjaId = tmp.substring(0, 2);
  }

  protected svrhaPlacanjaChange(svrhaPlacanjaElement: string): void {
    if (svrhaPlacanjaElement.length > 35) {
      this.errorSvrhaPlacanja = true;
    } else {
      this.svrhaPlacanjaElement = svrhaPlacanjaElement;
      this.errorSvrhaPlacanja = false;
    }
  }

  protected primalacChange(primalacElement: any): void {
    this.primalacElement = primalacElement;
  }

  protected checkTekuci(): void {
    let racunPrimaoca = this.editForm.get('racunPrimaoca')?.value;
    racunPrimaoca = !racunPrimaoca!.trim() ? '' : racunPrimaoca!.trim();
    if (
      racunPrimaoca !== '' &&
      (racunPrimaoca.substring(0, 3).includes('-') || racunPrimaoca.substring(racunPrimaoca.length - 2, racunPrimaoca.length).includes('-'))
    ) {
      // racunPrimaoca?.match(/^[0-9-]+$/) == null
      this.errorTekuci = true;
    } else {
      this.errorTekuci = false;
    }
  }

  protected checkIznos(): void {
    const iznos = this.editForm.get('iznos')?.value as number;
    if (iznos < 0) {
      this.errorIznos = true;
    } else {
      this.errorIznos = false;
    }
  }

  protected checkModel(): void {
    let model = this.editForm.get('model')?.value;
    if (model !== null) {
      model = !model!.trim() ? '' : model!.trim();

      if (model !== '') {
        // && model?.match(/^[0-9]+$/) === null
        this.errorModel = true;
      } else if (model.length > 2) {
        this.errorModel = true;
      } else {
        this.errorModel = false;
      }
    }
  }

  protected checkPozivNaBroj(): void {
    let pozivNaBroj = this.editForm.get('pozivNaBroj')?.value;
    const model = this.editForm.get('model')?.value;
    if (pozivNaBroj !== null) {
      pozivNaBroj = !pozivNaBroj!.trim() ? '' : pozivNaBroj!.trim();

      if (pozivNaBroj.substring(0, 1) === '-' || pozivNaBroj.substring(pozivNaBroj.length - 1, pozivNaBroj.length) === '-') {
        this.errorPozivNaBroj = true;
      } else if (pozivNaBroj !== '') {
        //  && pozivNaBroj?.match(/^[0-9-]+$/) === null
        this.errorPozivNaBroj = true;
      } else if (pozivNaBroj.includes('-') && (model === '97' || model === '11')) {
        this.errorPozivNaBroj = true;
      } else if (pozivNaBroj.length > 33) {
        this.errorPozivNaBroj = true;
      } else {
        this.errorPozivNaBroj = false;
      }
    }
  }
}
