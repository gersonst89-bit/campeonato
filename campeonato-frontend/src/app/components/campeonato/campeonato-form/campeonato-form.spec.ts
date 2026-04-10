import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampeonatoForm } from './campeonato-form';

describe('CampeonatoForm', () => {
  let component: CampeonatoForm;
  let fixture: ComponentFixture<CampeonatoForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CampeonatoForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampeonatoForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
