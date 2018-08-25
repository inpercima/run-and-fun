import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { Title } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { ConfigService } from './core/config.service';
import { FeaturesRoutingModule } from './features/features-routing.module';
import { LoginRoutingModule } from './login/login-routing.module';

@Component({
  selector: 'raf-root',
  templateUrl: './app.component.html',
  // WORKAROUND use style instead of styleUrls and
  // require('./app.component.css').toString() to avoid Error: Expected 'styles' to be an array of strings.
  styles: [require('./app.component.css').toString()],
})
export class AppComponent {

  public routes: Routes;

  public appname: string;

  public constructor(private configService: ConfigService, private titleService: Title) {
    this.appname = configService.getAppname();
    this.routes = AppRoutingModule.ROUTES;
    if (configService.isShowFeatures()) {
      this.routes = this.routes.concat(FeaturesRoutingModule.ROUTES);
    }
    // should a login will be used the login route could be added
    if (configService.isActivateLogin() && configService.isShowLogin()) {
      this.routes = this.routes.concat(LoginRoutingModule.ROUTES);
    }
    this.titleService.setTitle(this.appname);
  }

}
