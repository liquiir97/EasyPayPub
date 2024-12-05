import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UplatnicaDetailComponent } from './uplatnica-detail.component';

describe('Uplatnica Management Detail Component', () => {
  let comp: UplatnicaDetailComponent;
  let fixture: ComponentFixture<UplatnicaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UplatnicaDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ uplatnica: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(UplatnicaDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(UplatnicaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load uplatnica on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.uplatnica).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
