import { NgModule } from '@angular/core';

import { FeaturesRoutingModule } from './features-routing.module';
import { DashComponent } from './dash/dash.component';

@NgModule({
  declarations: [
    DashComponent,
  ],
  imports: [
    FeaturesRoutingModule,
  ],
})
export class FeaturesModule { }
