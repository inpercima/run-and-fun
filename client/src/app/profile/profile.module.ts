import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ReactiveFormsModule } from '@angular/forms';

import { MaterialModule } from '../shared/material/material.module';
import { ProfileComponent } from './profile.component';
import { ProfileRoutingModule } from './profile-routing.module';

@NgModule({
  declarations: [ ProfileComponent ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    MaterialModule,
    ProfileRoutingModule
  ]
})
export class ProfileModule { }
