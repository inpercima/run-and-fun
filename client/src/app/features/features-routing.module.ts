import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../core/auth-guard.service';
import { OverviewComponent } from './overview/overview.component';
import { environment } from '../../environments/environment';
import { ActivitiesComponent } from './activities/activities.component';
import { GraphsComponent } from './graphs/graphs.component';
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
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class FeaturesRoutingModule {

  public static ROUTES = routes;

}
