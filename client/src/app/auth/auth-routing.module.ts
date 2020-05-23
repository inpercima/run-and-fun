import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { VerifyComponent } from './verify/verify.component';

const routes: Routes = [{
  component: LoginComponent,
  path: 'login',
}, {
  component: VerifyComponent,
  path: 'verify',
}];

@NgModule({
  imports: [ RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class AuthRoutingModule {

  static ROUTES = routes;
}
