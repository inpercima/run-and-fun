import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';

import { ProfileService } from './profile.service';

@Component({
  selector: 'raf-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profileForm: FormGroup;

  profileImage: string;

  constructor(private formBuilder: FormBuilder, private domSanitizer: DomSanitizer, private profileService: ProfileService) { }

  ngOnInit(): void {
    this.profileService.get().subscribe(profile => {
      this.profileImage = profile.small_picture;
      this.profileForm = this.formBuilder.group({
        // to avoid 'changed after checked' errors, it is recommend using disabled here and not in html
        username: [{ value: profile.username, disabled: true }],
        name: [{ value: profile.name, disabled: true }],
        location: [{ value: profile.location, disabled: true }],
        gender: [{ value: profile.gender, disabled: true }],
        elite: [{ value: profile.elite ? 'yes' : 'no', disabled: true }],
      });
    });
  }

  headerImage() {
    // DomSanitizer bypassSecurityTrustStyle must used to get picture from different url
    return this.domSanitizer.bypassSecurityTrustStyle(`url('${this.profileImage}')`);
  }
}
