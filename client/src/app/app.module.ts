import { OverlayModule } from '@angular/cdk/overlay';
import { NgModule } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { JwtModule } from '@auth0/angular-jwt';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppRoutingPipe } from './app-routing.pipe';
import { FeaturesModule } from './features/features.module';
import { LoginModule } from './login/login.module';
import { NotFoundModule } from './not-found/not-found.module';

export function getToken() {
  return localStorage.getItem('access_token');
}

@NgModule({
  declarations: [
    AppComponent,
    AppRoutingPipe
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    MatTabsModule,
    MatToolbarModule,
    OverlayModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: getToken,
        whitelistedDomains: ['localhost'],
      }
    }),
    AppRoutingModule,
    FeaturesModule,
    LoginModule,
    NotFoundModule
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
