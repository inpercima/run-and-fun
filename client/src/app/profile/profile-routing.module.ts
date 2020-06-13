import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '../auth/auth-guard.service';
import { ProfileComponent } from './profile.component';

const routes: Routes = [{
  canActivate: [ AuthGuard ],
  component: ProfileComponent,
  path: 'profile',
}];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class ProfileRoutingModule {

  static ROUTES = routes;
}
