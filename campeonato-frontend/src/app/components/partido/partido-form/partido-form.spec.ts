import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartidoForm } from './partido-form';

describe('PartidoForm', () => {
  let component: PartidoForm;
  let fixture: ComponentFixture<PartidoForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartidoForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartidoForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
