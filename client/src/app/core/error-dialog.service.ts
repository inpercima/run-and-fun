import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ErrorDialogComponent } from '../shared/error-dialog/error-dialog.component';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ErrorDialogService {

  isDialogOpen = false;

  constructor(public dialog: MatDialog) { }

  openDialog(): void {
    if (!this.isDialogOpen) {
      this.isDialogOpen = true;
      const dialogRef = this.dialog.open(ErrorDialogComponent, {
        width: '300px',
        panelClass: `${environment.theme}-theme`
      });
      dialogRef.afterClosed().subscribe(() => this.isDialogOpen = false);
    }
  }
}
