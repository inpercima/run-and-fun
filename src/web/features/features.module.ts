import { NgModule } from '@angular/core';

import { ActivitiesComponent } from './activities/activities.component';
import { DashComponent } from './dash/dash.component';
import { FeaturesRoutingModule } from './features-routing.module';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';

@NgModule({
  declarations: [
    ActivitiesComponent,
    DashComponent,
    FriendsComponent,
    GraphsComponent,
  ],
  imports: [
    FeaturesRoutingModule,
  ],
})
export class FeaturesModule { }
