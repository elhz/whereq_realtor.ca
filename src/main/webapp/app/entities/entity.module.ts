import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WhereQRealtorCaListingModule } from './listing/listing.module';
import { WhereQRealtorCaLocationModule } from './location/location.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WhereQRealtorCaListingModule,
        WhereQRealtorCaLocationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WhereQRealtorCaEntityModule {}
