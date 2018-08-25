import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import config from '../config.json';

const routes: Routes = [{
  path: '',
  pathMatch: 'full',
  redirectTo: (<any>config).routes.default,
}];

@NgModule({
  exports: [
    RouterModule,
  ],
  imports: [
    RouterModule.forRoot(routes, { useHash: true }),
  ],
})
export class AppRoutingModule {

  public static ROUTES: Routes = routes;

}
