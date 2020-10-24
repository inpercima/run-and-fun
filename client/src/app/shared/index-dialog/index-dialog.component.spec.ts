import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexDialogComponent } from './index-dialog.component';

describe('IndexDialogComponent', () => {
  let component: IndexDialogComponent;
  let fixture: ComponentFixture<IndexDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndexDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndexDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
