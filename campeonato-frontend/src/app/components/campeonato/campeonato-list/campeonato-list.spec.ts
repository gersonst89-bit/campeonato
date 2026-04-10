import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CampeonatoList } from './campeonato-list';

describe('CampeonatoList', () => {
  let component: CampeonatoList;
  let fixture: ComponentFixture<CampeonatoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CampeonatoList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CampeonatoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
