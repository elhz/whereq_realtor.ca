import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WhereQRealtorCaSharedModule } from '../../shared';

import {
    ListingService,
    ListingPopupService,
    ListingComponent,
    ListingDetailComponent,
    ListingDialogComponent,
    ListingPopupComponent,
    ListingDeletePopupComponent,
    ListingDeleteDialogComponent,
    listingRoute,
    listingPopupRoute,
    ListingResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...listingRoute,
    ...listingPopupRoute,
];

@NgModule({
    imports: [
        WhereQRealtorCaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ListingComponent,
        ListingDetailComponent,
        ListingDialogComponent,
        ListingDeleteDialogComponent,
        ListingPopupComponent,
        ListingDeletePopupComponent,
    ],
    entryComponents: [
        ListingComponent,
        ListingDialogComponent,
        ListingPopupComponent,
        ListingDeleteDialogComponent,
        ListingDeletePopupComponent,
    ],
    providers: [
        ListingService,
        ListingPopupService,
        ListingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhereQRealtorCaListingModule {}
