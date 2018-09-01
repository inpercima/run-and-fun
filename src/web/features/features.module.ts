import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { NgModule } from '@angular/core';

import { ActivitiesComponent } from './activities/activities.component';
import { DashComponent } from './dash/dash.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { FriendsComponent } from './friends/friends.component';
import { FriendsService } from './friends/friends.service';
import { GraphsComponent } from './graphs/graphs.component';

@NgModule({
  declarations: [
    ActivitiesComponent,
    DashComponent,
    FriendsComponent,
    GraphsComponent,
  ],
  imports: [
    CommonModule,
    FeaturesRoutingModule,
    FlexLayoutModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSortModule,
  ],
  providers: [
    FriendsService,
  ]
})
export class FeaturesModule { }
