import { OverlayContainer } from '@angular/cdk/overlay';
import { Component, HostBinding, OnInit } from '@angular/core';
import { Routes, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';

import { Observable } from 'rxjs';

import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from './auth/auth.service';
import { AppState } from './auth/app-state.model';
import { ActivitiesService } from './features/activities/activities.service';
import { FeaturesRoutingModule } from './features/features-routing.module';

@Component({
  selector: 'raf-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

  routes: Routes;

  appname: string;

  appState$: Observable<AppState>;

  isAuthenticated$: Observable<boolean>;

  /**
   * Adds the custom theme to the app root.
   * For overlays the OverlayContainer like in the constructor is used.
   * For dialogs the panelClass of the configuration must added manually like
   *
   * const dialogConfig = new MatDialogConfig();
   * dialogConfig.panelClass = `${environment.theme}-theme`;
   */
  @HostBinding('class') class = `${environment.theme}-theme`;

  public constructor(private router: Router, private titleService: Title, public overlayContainer: OverlayContainer,
                     private activitiesService: ActivitiesService, private authService: AuthService) {
    this.appname = environment.appname;
    this.routes = AppRoutingModule.ROUTES.concat(FeaturesRoutingModule.ROUTES);
    this.titleService.setTitle(this.appname);
    this.overlayContainer.getContainerElement().classList.add(`${environment.theme}-theme`);
  }

  ngOnInit() {
    this.authService.verifyAuthentication().subscribe();
    this.isAuthenticated$ = this.authService.isAuthenticated;
    this.appState$ = this.authService.state();
  }

  profile() {
    this.router.navigate(['profile']);
  }

  indexActivities() {
    this.activitiesService.openDialog();
  }

  logout() {
    this.authService.logout();
  }
}
