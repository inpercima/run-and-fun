import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NotFoundComponent } from './not-found.component';
import { environment } from '../../environments/environment';

const routes: Routes = [environment.redirectNotFound ? {
  path: '**',
  redirectTo: environment.defaultRoute
} : {
  component: NotFoundComponent,
  path: '**',
}];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class NotFoundRoutingModule {

  public static ROUTES = routes;

}
