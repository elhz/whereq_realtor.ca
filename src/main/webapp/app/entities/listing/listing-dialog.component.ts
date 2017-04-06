import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Listing } from './listing.model';
import { ListingPopupService } from './listing-popup.service';
import { ListingService } from './listing.service';
import { Location, LocationService } from '../location';

@Component({
    selector: 'jhi-listing-dialog',
    templateUrl: './listing-dialog.component.html'
})
export class ListingDialogComponent implements OnInit {

    listing: Listing;
    authorities: any[];
    isSaving: boolean;

    locations: Location[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private listingService: ListingService,
        private locationService: LocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['listing', 'listingType', 'listingStatus', 'style']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.locationService.query({filter: 'listing-is-null'}).subscribe((res: Response) => {
            if (!this.listing.location || !this.listing.location.id) {
                this.locations = res.json();
            } else {
                this.locationService.find(this.listing.location.id).subscribe((subRes: Location) => {
                    this.locations = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.listing.id !== undefined) {
            this.listingService.update(this.listing)
                .subscribe((res: Listing) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.listingService.create(this.listing)
                .subscribe((res: Listing) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess (result: Listing) {
        this.eventManager.broadcast({ name: 'listingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-listing-popup',
    template: ''
})
export class ListingPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private listingPopupService: ListingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.listingPopupService
                    .open(ListingDialogComponent, params['id']);
            } else {
                this.modalRef = this.listingPopupService
                    .open(ListingDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
