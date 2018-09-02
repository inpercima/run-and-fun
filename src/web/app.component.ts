import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Routes } from '@angular/router';
import { Title } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppState } from './core/appState';
import { AuthService } from './core/auth.service';
import { ConfigService } from './core/config.service';
import { FeaturesRoutingModule } from './features/features-routing.module';
import { IndexActivitiesDialogComponent } from './app-index-activities-dialog.component';
import { LoginRoutingModule } from './login/login-routing.module';

@Component({
  selector: 'raf-root',
  templateUrl: './app.component.html',
  // WORKAROUND use style instead of styleUrls and
  // require('./app.component.css').toString() to avoid Error: Expected 'styles' to be an array of strings.
  styles: [require('./app.component.css').toString()],
})
export class AppComponent implements OnInit {

  private routes: Routes;

  private appname: string;

  private isAuthenticated: boolean;

  private appState: AppState = null;

  public constructor(private authService: AuthService, private configService: ConfigService, private titleService: Title, public dialog: MatDialog) { }

  ngOnInit() {
    this.appname = this.configService.getAppname();
    this.routes = AppRoutingModule.ROUTES;
    if (this.configService.isShowFeatures()) {
      this.routes = this.routes.concat(FeaturesRoutingModule.ROUTES);
    }
    // should a login will be used the login route could be added
    if (this.configService.isActivateLogin() && this.configService.isShowLogin()) {
      this.routes = this.routes.concat(LoginRoutingModule.ROUTES);
    }
    this.titleService.setTitle(this.appname);
    this.authService.isAuthenticated().subscribe(response => this.isAuthenticated = response);
    this.authService.appState().subscribe(response => this.appState = response);
  }

  public indexActivities() {
    const dialogRef = this.dialog.open(IndexActivitiesDialogComponent, {
      width: '250px',
    });
    dialogRef.updatePosition({ top: '200px' });
    dialogRef.afterClosed().subscribe(result => {
      // TODO: refresh page
    });
  }

  public logout() {
    window.location.href = '/logout';
  }

}
