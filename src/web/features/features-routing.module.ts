import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import config from '../../config.json';
import { ActivitiesComponent } from './activities/activities.component';
import { AuthGuard } from '../core/auth-guard.service';
import { DashComponent } from './dash/dash.component';
import { FriendsComponent } from './friends/friends.component';
import { GraphsComponent } from './graphs/graphs.component';

const routes: Routes = [{
  canActivate: [AuthGuard],
  component: DashComponent,
  path: (<any>config).routes.default,
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
    RouterModule
  ],
})
export class FeaturesRoutingModule {

  public static ROUTES = routes;

}
