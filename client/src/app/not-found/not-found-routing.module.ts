import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { NotFoundComponent } from './not-found.component';

const routes: Routes = [{
  component: NotFoundComponent,
  path: '**',
}];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [
    RouterModule,
  ],
})
export class NotFoundRoutingModule {

  static ROUTES = routes;
}
