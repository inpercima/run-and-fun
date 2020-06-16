import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';

import { ChartsModule } from 'ng2-charts';

import { MaterialModule } from '../shared/material/material.module';
import { ActivitiesComponent } from './activities/activities.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';
import { OverviewComponent } from './overview/overview.component';

@NgModule({
  declarations: [
    ActivitiesComponent,
    FriendsComponent,
    GraphsComponent,
    OverviewComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ChartsModule,
    FeaturesRoutingModule,
    FlexLayoutModule,
    MaterialModule,
  ]
})
export class FeaturesModule { }
