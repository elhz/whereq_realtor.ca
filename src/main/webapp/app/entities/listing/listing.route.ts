import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ListingComponent } from './listing.component';
import { ListingDetailComponent } from './listing-detail.component';
import { ListingPopupComponent } from './listing-dialog.component';
import { ListingDeletePopupComponent } from './listing-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ListingResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const listingRoute: Routes = [
  {
    path: 'listing',
    component: ListingComponent,
    resolve: {
      'pagingParams': ListingResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'whereQRealtorCaApp.listing.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'listing/:id',
    component: ListingDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'whereQRealtorCaApp.listing.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const listingPopupRoute: Routes = [
  {
    path: 'listing-new',
    component: ListingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'whereQRealtorCaApp.listing.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'listing/:id/edit',
    component: ListingPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'whereQRealtorCaApp.listing.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'listing/:id/delete',
    component: ListingDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'whereQRealtorCaApp.listing.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
