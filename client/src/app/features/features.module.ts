import { NgModule } from '@angular/core';

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
    OverviewComponent,
  ],
  imports: [
    FeaturesRoutingModule,
  ],
})
export class FeaturesModule { }
