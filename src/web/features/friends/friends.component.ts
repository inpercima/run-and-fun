import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';

import { FriendsService } from './friends.service';

@Component({
  selector: 'raf-friends',
  templateUrl: './friends.component.html',
})
export class FriendsComponent implements OnInit {

  private displayedColumns: string[] = ['name', 'profile'];

  private dataSource = new MatTableDataSource();

  constructor(private friendsService: FriendsService) { }

  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.friendsService.list().subscribe(response => this.dataSource.data = response);
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
