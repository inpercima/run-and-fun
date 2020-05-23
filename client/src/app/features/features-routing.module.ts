import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '../core/auth-guard.service';
import { OverviewComponent } from './overview/overview.component';
import { environment } from '../../environments/environment';
import { GraphsComponent } from './graphs/graphs.component';
import { ActivitiesComponent } from './activities/activities.component';
import { FriendsComponent } from './friends/friends.component';

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
