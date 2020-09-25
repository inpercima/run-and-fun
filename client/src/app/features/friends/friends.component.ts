import { Component, OnInit } from '@angular/core';

import { FriendsService } from './friends.service';

@Component({
  selector: 'raf-friends',
  templateUrl: './friends.component.html',
})
export class FriendsComponent implements OnInit {

  friends: any;

  constructor(private friendsService: FriendsService) { }

  ngOnInit(): void {
    this.friendsService.list().subscribe(friends => this.friends = friends);
  }
}
