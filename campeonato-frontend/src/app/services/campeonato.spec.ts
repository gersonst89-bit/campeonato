import { TestBed } from '@angular/core/testing';

import { Campeonato } from './campeonato';

describe('Campeonato', () => {
  let service: Campeonato;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Campeonato);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
