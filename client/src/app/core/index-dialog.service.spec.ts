import { TestBed } from '@angular/core/testing';

import { IndexDialogService } from './index-dialog.service';

describe('IndexDialogService', () => {
  let service: IndexDialogService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndexDialogService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
