import { Component, OnInit, OnDestroy, ChangeDetectorRef, Inject } from '@angular/core';
import { Subject } from 'rxjs';
import { MatCalendar } from '@angular/material/datepicker';
import { MatDateFormats, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'raf-datepicker-header',
  templateUrl: './datepicker-header.component.html',
  styleUrls: ['./datepicker-header.component.css'],
})
export class DatepickerHeaderComponent<D> implements OnDestroy {
  private destroyed = new Subject<void>();

  constructor(private calendar: MatCalendar<D>, private dateAdapter: DateAdapter<D>,
              @Inject(MAT_DATE_FORMATS) private dateFormats: MatDateFormats, cdr: ChangeDetectorRef) {
    calendar.stateChanges.pipe(takeUntil(this.destroyed)).subscribe(() => cdr.markForCheck());
  }

  ngOnDestroy(): void {
    this.destroyed.next();
    this.destroyed.complete();
  }

  get periodLabel(): string {
    return this.dateAdapter
      .format(this.calendar.activeDate, this.dateFormats.display.monthYearLabel)
      .toLocaleUpperCase();
  }

  previousClicked(mode: any): void {
    this.calendar.activeDate = this.dateAdapter.addCalendarMonths(this.calendar.activeDate, -1);
  }

  nextClicked(mode: any): void {
    this.calendar.activeDate = this.dateAdapter.addCalendarMonths(this.calendar.activeDate, 1);
  }
}
