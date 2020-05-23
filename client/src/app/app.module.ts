import { OverlayModule } from '@angular/cdk/overlay';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppRoutingPipe } from './app-routing.pipe';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthModule } from './auth/auth.module';
import { FeaturesModule } from './features/features.module';
import { NotFoundModule } from './not-found/not-found.module';
import { ErrorDialogComponent } from './shared/error-dialog/error-dialog.component';
import { MaterialModule } from './shared/material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    AppRoutingPipe,
    ErrorDialogComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    OverlayModule,
    AppRoutingModule,
    AuthModule,
    FeaturesModule,
    MaterialModule,
    NotFoundModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
