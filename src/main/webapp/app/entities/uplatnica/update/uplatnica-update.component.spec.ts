import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { UplatnicaFormService } from './uplatnica-form.service';
import { UplatnicaService } from '../service/uplatnica.service';
import { IUplatnica } from '../uplatnica.model';

import { UplatnicaUpdateComponent } from './uplatnica-update.component';

describe('Uplatnica Management Update Component', () => {
  let comp: UplatnicaUpdateComponent;
  let fixture: ComponentFixture<UplatnicaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let uplatnicaFormService: UplatnicaFormService;
  let uplatnicaService: UplatnicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [UplatnicaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(UplatnicaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UplatnicaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    uplatnicaFormService = TestBed.inject(UplatnicaFormService);
    uplatnicaService = TestBed.inject(UplatnicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const uplatnica: IUplatnica = { id: 456 };

      activatedRoute.data = of({ uplatnica });
      comp.ngOnInit();

      expect(comp.uplatnica).toEqual(uplatnica);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUplatnica>>();
      const uplatnica = { id: 123 };
      jest.spyOn(uplatnicaFormService, 'getUplatnica').mockReturnValue(uplatnica);
      jest.spyOn(uplatnicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uplatnica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uplatnica }));
      saveSubject.complete();

      // THEN
      expect(uplatnicaFormService.getUplatnica).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(uplatnicaService.update).toHaveBeenCalledWith(expect.objectContaining(uplatnica));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUplatnica>>();
      const uplatnica = { id: 123 };
      jest.spyOn(uplatnicaFormService, 'getUplatnica').mockReturnValue({ id: null });
      jest.spyOn(uplatnicaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uplatnica: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: uplatnica }));
      saveSubject.complete();

      // THEN
      expect(uplatnicaFormService.getUplatnica).toHaveBeenCalled();
      expect(uplatnicaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUplatnica>>();
      const uplatnica = { id: 123 };
      jest.spyOn(uplatnicaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ uplatnica });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(uplatnicaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
