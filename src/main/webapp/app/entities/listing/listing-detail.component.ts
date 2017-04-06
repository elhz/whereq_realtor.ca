import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Listing } from './listing.model';
import { ListingService } from './listing.service';

@Component({
    selector: 'jhi-listing-detail',
    templateUrl: './listing-detail.component.html'
})
export class ListingDetailComponent implements OnInit, OnDestroy {

    listing: Listing;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private listingService: ListingService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['listing', 'listingType', 'listingStatus', 'style']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
        this.registerChangeInListings();
    }

    load (id) {
        this.listingService.find(id).subscribe(listing => {
            this.listing = listing;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInListings() {
        this.eventSubscriber = this.eventManager.subscribe('listingListModification', response => this.load(this.listing.id));
    }

}
