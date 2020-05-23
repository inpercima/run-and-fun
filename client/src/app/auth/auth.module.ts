import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';

import { MaterialModule } from '../shared/material/material.module';
import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { VerifyComponent } from './verify/verify.component';

@NgModule({
  declarations: [
    LoginComponent,
    VerifyComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    AuthRoutingModule,
    MaterialModule
  ]
})
export class AuthModule { }
