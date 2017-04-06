import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { WhereQRealtorCaSharedModule, UserRouteAccessService } from './shared';
import { WhereQRealtorCaHomeModule } from './home/home.module';
import { WhereQRealtorCaAdminModule } from './admin/admin.module';
import { WhereQRealtorCaAccountModule } from './account/account.module';
import { WhereQRealtorCaEntityModule } from './entities/entity.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';


@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        WhereQRealtorCaSharedModule,
        WhereQRealtorCaHomeModule,
        WhereQRealtorCaAdminModule,
        WhereQRealtorCaAccountModule,
        WhereQRealtorCaEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class WhereQRealtorCaAppModule {}
