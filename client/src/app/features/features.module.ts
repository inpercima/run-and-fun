import { NgModule } from '@angular/core';

import { FeaturesRoutingModule } from './features-routing.module';
import { OverviewComponent } from './overview/overview.component';

@NgModule({
  declarations: [
    OverviewComponent,
  ],
  imports: [
    FeaturesRoutingModule,
  ],
})
export class FeaturesModule { }
