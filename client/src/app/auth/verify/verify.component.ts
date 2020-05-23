import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AuthService } from '../auth.service';

@Component({
  selector: 'raf-verify',
  templateUrl: './verify.component.html'
})
export class VerifyComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit() {
    const queryParamMap = this.activatedRoute.snapshot.queryParamMap;
    const error = queryParamMap.get('error');
    this.authService.verify(queryParamMap.get('code'), error ? error : '').subscribe(() => {
      const redirectUri = queryParamMap.get('state');
      this.router.navigate([redirectUri ? redirectUri : 'overview']);
    }, () => this.router.navigate(['login']));
  }
}
