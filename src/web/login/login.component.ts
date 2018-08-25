import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../core/auth.service';
import { ConfigService } from '../core/config.service';

@Component({
  selector: 'raf-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {

  public loginForm: FormGroup;

  public hide = true;

  public wrongLogin = false;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private configService: ConfigService,
    private router: Router) {
    this.createForm();
  }

  createForm() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    this.authService.login(this.loginForm).subscribe(next => {
      if (this.authService.isAuthenticated) {
        this.wrongLogin = false;
        // get the redirect URL from auth service
        // if no redirect has been set, use default
        this.router.navigate([this.authService.redirectUrl ? this.authService.redirectUrl : this.configService.getDefaultRoute()]);
      }
    }, error => {
      this.wrongLogin = true;
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

}
