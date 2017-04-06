import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Listing } from './listing.model';
import { ListingPopupService } from './listing-popup.service';
import { ListingService } from './listing.service';

@Component({
    selector: 'jhi-listing-delete-dialog',
    templateUrl: './listing-delete-dialog.component.html'
})
export class ListingDeleteDialogComponent {

    listing: Listing;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private listingService: ListingService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['listing', 'listingType', 'listingStatus', 'style']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.listingService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'listingListModification',
                content: 'Deleted an listing'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-listing-delete-popup',
    template: ''
})
export class ListingDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private listingPopupService: ListingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.listingPopupService
                .open(ListingDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
