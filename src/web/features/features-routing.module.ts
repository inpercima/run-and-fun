import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import config from '../../config.json';
import { AuthGuard } from '../core/auth-guard.service';
import { DashComponent } from './dash/dash.component';

const routes: Routes = [{
  canActivate: [AuthGuard],
  component: DashComponent,
  path: (<any>config).routes.default,
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
