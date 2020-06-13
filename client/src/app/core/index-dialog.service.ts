import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IndexDialogComponent } from '../shared/index-dialog/index-dialog.component';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IndexDialogService {

  isDialogOpen = false;

  constructor(private dialog: MatDialog) { }

  openDialog(): void {
    if (!this.isDialogOpen) {
      this.isDialogOpen = true;
      const dialogRef = this.dialog.open(IndexDialogComponent, {
        width: '300px',
        panelClass: `${environment.theme}-theme`
      });
      dialogRef.afterClosed().subscribe(() => this.isDialogOpen = false);
    }
  }
}
