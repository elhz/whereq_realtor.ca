import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Listing } from './listing.model';
import { ListingService } from './listing.service';
@Injectable()
export class ListingPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private listingService: ListingService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.listingService.find(id).subscribe(listing => {
                if (listing.inputDate) {
                    listing.inputDate = {
                        year: listing.inputDate.getFullYear(),
                        month: listing.inputDate.getMonth() + 1,
                        day: listing.inputDate.getDate()
                    };
                }
                if (listing.modifiedDate) {
                    listing.modifiedDate = {
                        year: listing.modifiedDate.getFullYear(),
                        month: listing.modifiedDate.getMonth() + 1,
                        day: listing.modifiedDate.getDate()
                    };
                }
                this.listingModalRef(component, listing);
            });
        } else {
            return this.listingModalRef(component, new Listing());
        }
    }

    listingModalRef(component: Component, listing: Listing): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.listing = listing;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
