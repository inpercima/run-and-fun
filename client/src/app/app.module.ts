import { OverlayModule } from '@angular/cdk/overlay';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
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
import { IndexDialogComponent } from './shared/index-dialog/index-dialog.component';
import { MaterialModule } from './shared/material/material.module';

@NgModule({
  declarations: [
    AppComponent,
    AppRoutingPipe,
    ErrorDialogComponent,
    IndexDialogComponent
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FlexLayoutModule,
    OverlayModule,
    AppRoutingModule,
    AuthModule,
    FeaturesModule,
    MaterialModule,
    // NotFoundModule contains the route configuration for path: '**' and this must be at the end
    NotFoundModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
