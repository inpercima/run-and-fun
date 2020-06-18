import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '../auth/auth-guard.service';
import { environment } from '../../environments/environment';
import { ActivitiesComponent } from './activities/activities.component';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';
import { OverviewComponent } from './overview/overview.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [{
  canActivate: [AuthGuard],
  component: OverviewComponent,
  path: environment.defaultRoute,
  data: {
    description: 'overview of all your stuff',
  },
}, {
  canActivate: [AuthGuard],
  component: ActivitiesComponent,
  path: 'activities',
  data: {
    description: 'check and search your activities',
  },
}, {
  canActivate: [AuthGuard],
  component: GraphsComponent,
  path: 'graphs',
  data: {
    description: 'control your activities with graphs',
  },
}, {
  canActivate: [AuthGuard],
  component: FriendsComponent,
  path: 'friends',
  data: {
    description: 'see what friends do',
  },
}, {
    canActivate: [ AuthGuard ],
    component: ProfileComponent,
    path: 'profile',
    data: {
      description: 'your account information',
    },
}];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class FeaturesRoutingModule {

  static ROUTES = routes;
}
