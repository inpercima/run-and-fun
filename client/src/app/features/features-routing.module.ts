import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '../auth/auth-guard.service';
import { ActivitiesComponent } from './activities/activities.component';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';
import { OverviewComponent } from './overview/overview.component';
import { environment } from 'src/environments/environment';

const routes: Routes = [{
  canActivate: [AuthGuard],
  component: OverviewComponent,
  path: environment.defaultRoute,
}, {
  canActivate: [AuthGuard],
  component: ActivitiesComponent,
  path: 'activities',
}, {
  canActivate: [AuthGuard],
  component: GraphsComponent,
  path: 'graphs',
}, {
  canActivate: [AuthGuard],
  component: FriendsComponent,
  path: 'friends',
}];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class FeaturesRoutingModule {

  static ROUTES = routes;
}
