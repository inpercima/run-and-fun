import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { ActivitiesComponent } from './activities/activities.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';
import { OverviewComponent } from './overview/overview.component';

@NgModule({
  declarations: [
    OverviewComponent,
    FriendsComponent,
    ActivitiesComponent,
    GraphsComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FeaturesRoutingModule
  ]
})
export class FeaturesModule { }
