import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PartidoList } from './partido-list';

describe('PartidoList', () => {
  let component: PartidoList;
  let fixture: ComponentFixture<PartidoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PartidoList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PartidoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
